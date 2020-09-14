package de.upb.sede


import de.upb.sede.types.MutableDataTypeDesc
import de.upb.sede.util.DynRecord

class DataTypeDomain {

    // model MutableDataTypeDesc

    // topDom MutableServiceCollectionDesc

    static MutableDataTypeDesc aux(MutableDataTypeDesc model, @DelegatesTo(DynRecord) Closure desc) {
        Shared.aux(model, desc)
        return model
    }

    static MutableDataTypeDesc comment(MutableDataTypeDesc model, String ... comments) {
        Shared.comment(model, comments)
        return model
    }

    static MutableDataTypeDesc setInfo(MutableDataTypeDesc model, String commentBlock) {
        Shared.setInfo(model, commentBlock)
        return model
    }


}
