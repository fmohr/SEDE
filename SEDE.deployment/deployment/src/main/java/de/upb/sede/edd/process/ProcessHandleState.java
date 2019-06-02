package de.upb.sede.edd.process;

enum ProcessHandleState {
    INIT(false),
    STARTING(false),
    STARTED(false),
    ABORTED(true),
    FAILED(true),
    DETACHED(true),
    SUCCEEDED(true);

    private final boolean terminal;

    ProcessHandleState(boolean terminal) {
        this.terminal = terminal;
    }

    public boolean isTerminal() {
        return terminal;
    }
}
