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

import de.upb.sede.util.Uncheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;

public class ProcessStreamRunner implements Runnable {
    private final static Logger LOGGER = LoggerFactory.getLogger(ProcessStreamRunner.class);

    private final String displayName;
    private final InputStream inputStream;
    private final OutputStream outputStream;
    private final int bufferSize;
    private final CountDownLatch completed;
    private volatile boolean closed;

    public ProcessStreamRunner(String displayName, InputStream inputStream, OutputStream outputStream, CountDownLatch completed) {
        this(displayName, inputStream, outputStream, 2048, completed);
    }

    ProcessStreamRunner(String displayName, InputStream inputStream, OutputStream outputStream, int bufferSize, CountDownLatch completed) {
        this.displayName = displayName;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.bufferSize = bufferSize;
        this.completed = completed;
    }

    public void run() {
        try {
            forwardContent();
        } finally {
            completed.countDown();
        }
    }

    private void forwardContent() {
        byte[] buffer = new byte[bufferSize];
        try {
            while (!closed) {
                int nread = inputStream.read(buffer);
                if (nread < 0) {
                    break;
                }
                outputStream.write(buffer, 0, nread);
                outputStream.flush();
            }
            Uncheck.callEach(
                () -> {  inputStream.close(); return null;},
                () -> { outputStream.close(); return null;});
        } catch (Throwable t) {
            if (!closed && !wasInterrupted(t)) {
                LOGGER.error(String.format("Could not %s.", displayName), t);
            }
        }
    }

    /**
     * This can happen e.g. on IBM JDK when a remote process was terminated. Instead of
     * returning -1 on the next read() call, it will interrupt the current read call.
     */
    private boolean wasInterrupted(Throwable t) {
        return t instanceof IOException && "Interrupted system call".equals(t.getMessage());
    }

    public void closeInput() throws IOException {
        disconnect();
        inputStream.close();
    }

    @Override
    public String toString() {
        return displayName;
    }

    public void disconnect() {
        closed = true;
    }
}
