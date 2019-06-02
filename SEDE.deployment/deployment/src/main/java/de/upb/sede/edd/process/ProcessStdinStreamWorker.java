/*
 * Copyright 2017 the original author or authors.
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

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * Forwards the contents of an {@link InputStream} to the process' stdin
 */
public class ProcessStdinStreamWorker implements StreamWorker {

    private final InputStream input;

    private final CountDownLatch completed = new CountDownLatch(1);

    private Executor executor;

    private ProcessStreamRunner standardInputWriter;

    public ProcessStdinStreamWorker(InputStream input) {
        this.input = input;
    }

    @Override
    public void connectStreams(Process process, String processName, Executor executor) {
        this.executor = executor;

        /*
            There's a potential problem here in that DisconnectableInputStream reads from input in the background.
            This won't automatically stop when the process is over. Therefore, if input is not closed then this thread
            will run forever. It would be better to ensure that this thread stops when the process does.
         */
        InputStream instr = new Streams.GraciousCloseInputStream(input);
        standardInputWriter = new ProcessStreamRunner("write standard input to " + processName, instr, process.getOutputStream(), completed);
    }

    public void start() {
        executor.execute(standardInputWriter);
    }

    public void stop() {
        disconnect();
        try {
            completed.await();
        } catch (InterruptedException e) {
            throw Uncheck.throwAsUncheckedException(e);
        }
    }

    @Override
    public void disconnect() {
        try {
            standardInputWriter.closeInput();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
