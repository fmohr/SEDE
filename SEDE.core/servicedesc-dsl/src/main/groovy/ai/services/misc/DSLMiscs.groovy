package ai.services.misc

import ai.services.composition.IFieldType

class DSLMiscs {


    public static List<IFieldType> newContext(@DelegatesTo(FieldContextCreator) Closure contextCreatorClosure) {
        FieldContextCreator creator = new FieldContextCreator()
        contextCreatorClosure.delegate = creator
        contextCreatorClosure.resolveStrategy = Closure.DELEGATE_FIRST
        contextCreatorClosure.run()
        return creator.resultingContext
    }

}
