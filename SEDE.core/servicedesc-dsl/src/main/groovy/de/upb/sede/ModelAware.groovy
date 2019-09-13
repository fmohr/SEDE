package de.upb.sede

trait ModelAware {

    Object model

//    void attr(Closure describer) {
//        if(!(model instanceof IQualifiable) &&
//            !(model instanceof ICommented)) {
//            throw new IllegalStateException(model.class.simpleName + " does not support description")
//        }
//        describer.delegate = model
//        describer.resolveStrategy = Closure.DELEGATE_ONLY
//        describer.run()
//    }

    void run(Closure describer) {
        def code = describer.rehydrate(model, this, this)
        code.resolveStrategy = Closure.DELEGATE_FIRST
        code.run()
    }

}
