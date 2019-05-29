package de.upb.sede.edd;

enum ExecHandleState {
    INIT(false),
    STARTING(false),
    STARTED(false),
    ABORTED(true),
    FAILED(true),
    DETACHED(true),
    SUCCEEDED(true);

    private final boolean terminal;

    ExecHandleState(boolean terminal) {
        this.terminal = terminal;
    }

    public boolean isTerminal() {
        return terminal;
    }
}
