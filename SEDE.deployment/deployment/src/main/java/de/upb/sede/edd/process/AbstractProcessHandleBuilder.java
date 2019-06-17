package de.upb.sede.edd.process;

import de.upb.sede.util.Streams;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public abstract class AbstractProcessHandleBuilder extends DefaultProcessForkOptions implements ProcessHandleOptions {

    private static final EmptyStdInStreamsWorker DEFAULT_STDIN = new EmptyStdInStreamsWorker();


    private OutputStream standardOutput;
    private OutputStream errorOutput;
    private InputStream input;
    private String displayName;
    private boolean ignoreExitValue;
    private boolean redirectErrorStream;

    private StreamWorker outputWorker;
    private StreamWorker inputWorker = DEFAULT_STDIN;

    private int timeoutMillis = 0;
    protected boolean daemon;
    private Executor executor;



    AbstractProcessHandleBuilder(Executor executor) {
        super();
        this.executor = executor;
        standardOutput = Streams.safeSystemOut();
        errorOutput = Streams.safeSystemErr();
        input = Streams.EmptyInStream();
    }

    public abstract List<String> getAllArguments();

    public List<String> getCommandLine() {
        List<String> commandLine = new ArrayList<String>();
        commandLine.add(getExecutable());
        commandLine.addAll(getAllArguments());
        return commandLine;
    }

    public AbstractProcessHandleBuilder setStandardInput(InputStream inputStream) {
        this.input = inputStream;
        this.inputWorker = new ProcessStdinStreamWorker(inputStream);
        return this;
    }

    public InputStream getStandardInput() {
        return input;
    }

    public AbstractProcessHandleBuilder setStandardOutput(OutputStream outputStream) {
        if (outputStream == null) {
            throw new IllegalArgumentException("outputStream == null!");
        }
        this.standardOutput = outputStream;
        return this;
    }

    public OutputStream getStandardOutput() {
        return standardOutput;
    }

    public AbstractProcessHandleBuilder setErrorOutput(OutputStream outputStream) {
        if (outputStream == null) {
            throw new IllegalArgumentException("outputStream == null!");
        }
        this.errorOutput = outputStream;
        return this;
    }

    public OutputStream getErrorOutput() {
        return errorOutput;
    }

    public boolean isIgnoreExitValue() {
        return ignoreExitValue;
    }

    public AbstractProcessHandleBuilder setIgnoreExitValue(boolean ignoreExitValue) {
        this.ignoreExitValue = ignoreExitValue;
        return this;
    }

    public String getDisplayName() {
        return displayName == null ? String.format("command '%s'", getExecutable()) : displayName;
    }

    public AbstractProcessHandleBuilder setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public DefaultProcessHandle build() {
        String executable = getExecutable();
        if (executable == null || executable.isEmpty()) {
            throw new IllegalStateException("execCommand == null!");
        }

        StreamWorker outputWorker = getEffectiveStreamWorker();
        return new DefaultProcessHandle(
            getDisplayName(), getWorkingDir(), executable,
            getAllArguments(), getActualEnvironment(),
            outputWorker, inputWorker,  redirectErrorStream,
            timeoutMillis, daemon, executor);
    }


    private StreamWorker getEffectiveStreamWorker() {
        StreamWorker effectiveHandler;
        if (this.outputWorker != null) {
            effectiveHandler = this.outputWorker;
        } else {
            boolean shouldReadErrorStream = !redirectErrorStream;
            effectiveHandler = new ProcessOutputStreamWorker(standardOutput, errorOutput, shouldReadErrorStream);
        }
        return effectiveHandler;
    }

    /**
     * Merge the process' error stream into its output stream
     */
    public AbstractProcessHandleBuilder redirectErrorStream() {
        this.redirectErrorStream = true;
        return this;
    }

    public AbstractProcessHandleBuilder setTimeout(int timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
        return this;
    }
}
