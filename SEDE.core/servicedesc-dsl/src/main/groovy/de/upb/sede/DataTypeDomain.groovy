package de.upb.sede


import de.upb.sede.types.MutableDataTypeDesc
import de.upb.sede.types.auxiliary.MutableJavaTypeAux

class DataTypeDomain
    extends DomainAware<MutableDataTypeDesc, ServiceCollectionDomain>
    implements
//        Shared.AuxAware<MutableJavaTypeAux>,
        Shared.CommentAware,
        Shared.AuxDomAware {

    @Override
    def String getBindingName() {
        "type"
    }

//    @Override
//    MutableJavaTypeAux setJavaAux(MutableJavaTypeAux javaAux) {
//        model.javaTypeAux = javaAux
//        return javaAux
//    }
//
//    @Override
//    MutableJavaTypeAux getJavaAux() {
//        if(model.typeAux == null) {
//            model.javaTypeAux = MutableJavaTypeAux.create()
//        }
//        return model.typeAux
//    }

}
