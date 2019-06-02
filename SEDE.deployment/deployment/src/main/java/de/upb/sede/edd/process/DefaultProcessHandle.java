/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.upb.sede.edd.process;

import de.upb.sede.util.Streams;
import de.upb.sede.util.Uncheck;
import de.upb.sede.util.OptionalField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.*;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * Default implementation for the ExecHandle interface.
 *
 * <h3>State flows</h3>
 *
 * <ul>
 *   <li>INIT -> STARTED -> [SUCCEEDED|FAILED|ABORTED|DETACHED]</li>
 *   <li>INIT -> FAILED</li>
 *   <li>INIT -> STARTED -> DETACHED -> ABORTED</li>
 * </ul>
 *
 * State is controlled on all control methods:
 * <ul>
 * <li>{@link #start()} allowed when state is INIT</li>
 * <li>{@link #abort()} allowed when state is STARTED or DETACHED</li>
 * </ul>
 */
public class DefaultProcessHandle implements ProcessHandle {

    private final static Logger LOGGER = LoggerFactory.getLogger(DefaultProcessHandle.class);

    private final String displayName;

    /**
     * The working directory of the process.
     */
    private final File directory;

    /**
     * The executable to run.
     */
    private final String command;

    /**
     * Arguments to pass to the executable.
     */
    private final List<String> arguments;

    /**
     * The variables to set in the environment the executable is run in.
     */
    private final Map<String, String> environment;
    private final StreamWorker streamWorker;
    private final boolean redirectErrorStream;

    private OptionalField<Integer> timeoutMillis;
    private boolean daemon;

    /**
     * Lock to guard all mutable state
     */
    private final Lock lock;
    private final Condition stateChanged;

    private final Executor executor;
    /**
     * State of this ExecHandle.
     */
    private ProcessHandleState state;

    /**
     * When not null, the runnable that is waiting
     */
    private ProcessHandleRunner processHandleRunner;

    private ProcessExecResultImpl execResult;

    DefaultProcessHandle(String displayName, File directory, String command, List<String> arguments,
                         Map<String, String> environment, StreamWorker outputHandler, StreamWorker inputHandler,
                         boolean redirectErrorStream, int timeoutMillis, boolean daemon, Executor executor) {
        this.displayName = displayName;
        this.directory = directory;
        this.command = command;
        this.arguments = arguments;
        this.environment = environment;
        this.streamWorker = new StreamWorker.Composite(outputHandler, inputHandler);
        this.redirectErrorStream = redirectErrorStream;
        this.timeoutMillis = timeoutMillis <= 0 ? OptionalField.empty() : OptionalField.of(timeoutMillis);
        this.daemon = daemon;
        this.executor = executor;
        this.lock = new ReentrantLock();
        this.stateChanged = lock.newCondition();
        this.state = ProcessHandleState.INIT;
    }

    public File getDirectory() {
        return directory;
    }

    public String getCommand() {
        return command;
    }

    public boolean isDaemon() {
        return daemon;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public List<String> getArguments() {
        return Collections.unmodifiableList(arguments);
    }

    public Map<String, String> getEnvironment() {
        return Collections.unmodifiableMap(environment);
    }

    public ProcessHandleState getState() {
        lock.lock();
        try {
            return state;
        } finally {
            lock.unlock();
        }
    }

    private void setState(ProcessHandleState state) {
        lock.lock();
        try {
            LOGGER.debug("Changing state to: {}", state);
            this.state = state;
            this.stateChanged.signalAll();
        } finally {
            lock.unlock();
        }
    }

    private boolean stateIn(ProcessHandleState... states) {
        lock.lock();
        try {
            return Arrays.asList(states).contains(this.state);
        } finally {
            lock.unlock();
        }
    }

    private void setEndStateInfo(ProcessHandleState newState, int exitValue, Throwable failureCause) {
        ProcessHandleState currentState;
        lock.lock();
        try {
            currentState = this.state;
        } finally {
            lock.unlock();
        }

        ProcessExecResultImpl newResult = new ProcessExecResultImpl(exitValue, execExceptionFor(failureCause, currentState), displayName);

        lock.lock();
        try {
            setState(newState);
            this.execResult = newResult;
        } finally {
            lock.unlock();
        }

        LOGGER.debug("Process '{}' finished with exit value {} (state: {})", displayName, exitValue, newState);
    }

    @Nullable
    private RuntimeException execExceptionFor(Throwable failureCause, ProcessHandleState currentState) {
        return failureCause != null
            ? new RuntimeException(failureMessageFor(currentState), failureCause)
            : null;
    }

    private String failureMessageFor(ProcessHandleState currentState) {
        return currentState == ProcessHandleState.STARTING
            ? format("A problem occurred starting process '%s'", displayName)
            : format("A problem occurred waiting for process '%s' to complete.", displayName);
    }

    public DefaultProcessHandle start() {
        LOGGER.info("Starting process '{}'. Working directory: {} Command: {}",
                displayName, directory, command + ' ' + arguments .stream().map(s -> s == null? "null" : s).collect(Collectors.joining(" ")));
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Environment for process '{}': {}", displayName, environment);
        }
        lock.lock();
        try {
            if (!stateIn(ProcessHandleState.INIT)) {
                throw new IllegalStateException(format("Cannot start process '%s' because it has already been started", displayName));
            }
            setState(ProcessHandleState.STARTING);

            processHandleRunner = new ProcessHandleRunner();
            executor.execute(processHandleRunner);
            while (stateIn(ProcessHandleState.STARTING)) {
                LOGGER.debug("Waiting until process started: {}.", displayName);
                try {
                    stateChanged.await();
                } catch (InterruptedException e) {
                    processHandleRunner.abortProcess();
                    throw Uncheck.throwAsUncheckedException(e);
                }
            }

            if (execResult != null) {
                execResult.rethrowFailure();
            }

            LOGGER.info("Successfully started process '{}'", displayName);
        } finally {
            lock.unlock();
        }
        return this;
    }

    public void abort() {
        lock.lock();
        try {
            if (  stateIn(ProcessHandleState.SUCCEEDED, ProcessHandleState.FAILED, ProcessHandleState.ABORTED)) {
                return;
            }
            if (! stateIn(ProcessHandleState.STARTED, ProcessHandleState.DETACHED)) {
                throw new IllegalStateException(
                    format("Cannot abort process '%s' because it is not in started or detached state", displayName));
            }
            this.processHandleRunner.abortProcess();
            this.waitForFinish();
        } finally {
            lock.unlock();
        }
    }

    public ProcessResult waitForFinish() {
        lock.lock();
        try {
            while (!state.isTerminal()) {
                try {
                    stateChanged.await();
                } catch (InterruptedException e) {
                    processHandleRunner.abortProcess();
                    throw Uncheck.throwAsUncheckedException(e);
                }
            }
        } finally {
            lock.unlock();
        }

        // At this point:
        // If in daemon mode, the process has started successfully and all streams to the process have been closed
        // If in fork mode, the process has completed and all cleanup has been done
        // In both cases, all asynchronous work for the process has completed and we're done

        return result();
    }

    private ProcessResult result() {
        lock.lock();
        try {
            return execResult.rethrowFailure();
        } finally {
            lock.unlock();
        }
    }

    void detached() {
        setEndStateInfo(ProcessHandleState.DETACHED, 0, null);
    }

    void started() {
        setState(ProcessHandleState.STARTED);
    }

    void finished(int exitCode) {
        if (exitCode != 0) {
            setEndStateInfo(ProcessHandleState.FAILED, exitCode, null);
        } else {
            setEndStateInfo(ProcessHandleState.SUCCEEDED, 0, null);
        }
    }

    void aborted(int exitCode) {
        if (exitCode == 0) {
            // This can happen on Windows
            exitCode = -1;
        }
        setEndStateInfo(ProcessHandleState.ABORTED, exitCode, null);
    }

    void failed(Throwable failureCause) {
        setEndStateInfo(ProcessHandleState.FAILED, -1, failureCause);
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean getRedirectErrorStream() {
        return redirectErrorStream;
    }

    public Optional<Integer> getTimeout() {
        return timeoutMillis.opt();
    }

    private static class ProcessExecResultImpl implements ProcessResult {

        private final int exitValue;
        private final RuntimeException failure;
        private final String displayName;

        ProcessExecResultImpl(int exitValue, RuntimeException failure, String displayName) {
            this.exitValue = exitValue;
            this.failure = failure;
            this.displayName = displayName;
        }

        public int getExitValue() {
            return exitValue;
        }

        public ProcessResult assertNormalExitValue() throws RuntimeException {
            if (exitValue != 0) {
                throw new RuntimeException(format("Process '%s' finished with non-zero exit value %d", displayName, exitValue));
            }
            return this;
        }

        public ProcessResult rethrowFailure() throws RuntimeException {
            if (failure != null) {
                throw failure;
            }
            return this;
        }

        @Override
        public String toString() {
            return "{exitValue=" + exitValue + ", failure=" + failure + "}";
        }
    }

    public ProcessBuilder createProcessBuilder() {
        List<String> commandWithArguments = new ArrayList<String>();
        commandWithArguments.add(this.getCommand());
        commandWithArguments.addAll(this.getArguments());

        ProcessBuilder processBuilder = new ProcessBuilder(commandWithArguments);
        processBuilder.directory(this.getDirectory());
        processBuilder.redirectErrorStream(this.getRedirectErrorStream());

        Map<String, String> environment = processBuilder.environment();
        environment.clear();
        environment.putAll(this.getEnvironment());

        return processBuilder;
    }


    class ProcessHandleRunner implements Runnable {

        private final Lock lock = new ReentrantLock();

        private Process process;
        private boolean aborted;


        public void abortProcess() {
            lock.lock();
            try {
                if (aborted) {
                    return;
                }
                aborted = true;
                if (process != null) {
                    streamWorker.disconnect();
                    LOGGER.debug("Abort requested. Destroying process: {}.", getDisplayName());
                    process.destroy();
                }
            } finally {
                lock.unlock();
            }
        }

        public void run() {
            try {
                startProcess();

                started();


                LOGGER.debug("waiting until streams are handled...");
                streamWorker.start();

                if (isDaemon()) {
                    streamWorker.stop();
                    detached();
                } else {
                    int exitValue = process.waitFor();
                    streamWorker.stop();
                    completed(exitValue);
                }
            } catch (Throwable t) {
                failed(t);
            }
        }


        private void startProcess() {
            lock.lock();
            try {
                if (aborted) {
                    throw new IllegalStateException("Process has already been aborted");
                }
                ProcessBuilder processBuilder = createProcessBuilder();
                this.process = processBuilder.start();
                streamWorker.connectStreams(process, getDisplayName(), executor);
            } catch (IOException e) {
                LOGGER.error("error starting process:", e);
            } finally {
                lock.unlock();
            }
        }

        private void completed(int exitValue) {
            if (aborted) {
                aborted(exitValue);
            } else {
                finished(exitValue);
            }
        }

        private void detached() {
            DefaultProcessHandle.this.detached();
        }
    }

}
