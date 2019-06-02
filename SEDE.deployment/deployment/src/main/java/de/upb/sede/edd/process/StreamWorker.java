package de.upb.sede.edd.process;

import java.util.Objects;
import java.util.concurrent.Executor;

public interface StreamWorker {
    /**
     * Collects whatever state is required the given process. Should not start work.
     */
    void connectStreams(Process process, String processName, Executor executor);

    /**
     * Starts reading/writing/whatever the process' streams. May block until the streams reach some particular state, e.g. indicate that the process has started successfully.
     */
    void start();

    /**
     * Disconnects from the process without waiting for further work.
     */
    void disconnect();

    /**
     * Stops doing work with the process's streams. Should block until no further asynchronous work is happening on the streams.
     */
    void stop();

    class Composite implements StreamWorker {

        private final StreamWorker outputHandler;
        private final StreamWorker inputHandler;

        public Composite(StreamWorker outputHandler, StreamWorker inputHandler) {
            this.outputHandler = Objects.requireNonNull(outputHandler);
            this.inputHandler = Objects.requireNonNull(inputHandler);
        }

        @Override
        public void connectStreams(Process process, String processName, Executor executor) {
            inputHandler.connectStreams(process, processName, executor);
            outputHandler.connectStreams(process, processName, executor);
        }

        @Override
        public void start() {
            inputHandler.start();
            outputHandler.start();
        }

        @Override
        public void stop() {
            outputHandler.stop();
            inputHandler.stop();
        }

        @Override
        public void disconnect() {
            outputHandler.disconnect();
            inputHandler.disconnect();
        }
    }
}
