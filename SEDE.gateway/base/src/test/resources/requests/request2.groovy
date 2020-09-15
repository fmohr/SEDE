import ai.services.composition.RRGen
import ai.services.requests.resolve.beta.IResolvePolicy
import ai.services.requests.resolve.beta.MutableResolvePolicy
import ai.services.requests.resolve.beta.MutableResolveRequest
import groovy.transform.BaseScript

@BaseScript RRGen gen

MutableResolveRequest.create().tap {
    composition = """
        a = s1::m1();
        b = s1::m2();
        c = s1::m3();
        """

    resolvePolicy = MutableResolvePolicy.create().tap {
        setReturnPolicy(IResolvePolicy.FieldSelection.LISTED)
        addReturnFieldnames "a", "b", "c"
    }

}

