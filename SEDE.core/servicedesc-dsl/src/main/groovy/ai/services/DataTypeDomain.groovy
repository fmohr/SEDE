package ai.services


import ai.services.util.DynRecord

class DataTypeDomain {

    // model MutableDataTypeDesc

    // topDom MutableServiceCollectionDesc

    static ai.services.types.MutableDataTypeDesc aux(ai.services.types.MutableDataTypeDesc model, @DelegatesTo(DynRecord) Closure desc) {
        Shared.aux(model, desc)
        return model
    }

    static ai.services.types.MutableDataTypeDesc comment(ai.services.types.MutableDataTypeDesc model, String ... comments) {
        Shared.comment(model, comments)
        return model
    }

    static ai.services.types.MutableDataTypeDesc setInfo(ai.services.types.MutableDataTypeDesc model, String commentBlock) {
        Shared.setInfo(model, commentBlock)
        return model
    }


}
