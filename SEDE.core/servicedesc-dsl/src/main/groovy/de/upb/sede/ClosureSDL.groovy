package de.upb.sede

class ClosureSDL extends SDL{

    private Closure script;

    public ClosureSDL(Closure script) {
        this.script = script;
    }

    @Override
    Object run() {
        script.delegate = this;
        script.resolveStrategy = Closure.DELEGATE_FIRST;
        script.run();
        return super.cols;
    }
}
