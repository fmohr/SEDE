package de.upb.sede.composition

import de.upb.sede.ISDLAssembly
import de.upb.sede.SDLReader
import de.upb.sede.composition.typing.TypeCheckException
import de.upb.sede.gateway.SimpleGatewayImpl
import de.upb.sede.misc.DSLMiscs
import de.upb.sede.util.MappedList
import spock.lang.Specification

class SingleBlockCCImplTest extends Specification {

    static ISDLAssembly assembly

    static SimpleGatewayImpl simpleGateway

    def setupSpec() {
        assembly = SDLReader.assemble {
            collection "c", {
                service "a.S1", {
                    constructor inputs: ["Number", "Number"]
                    method name: "m1", input: "T1", output: 'T2'
                    method name: "m1", inputs: ["T1", "T2"], output: 'T3'
                    method name: "m2", input: "T1", output: 'T2', {
                        isPure = true
                    }
                }
                type 'T1', {
                    semanticType = "s1"
                }
                type 'T2', {
                    semanticType = "s2"
                }
                type 'T3', {
                    semanticType = "s1"
                }
            }
        }

        simpleGateway = new SimpleGatewayImpl(assembly)

    }

    def "test positive scenario"() {
        when:
        def composition = """
        s1 = a.S1::__construct({1,2});
        s2 = a.S1::__construct({12031,1295});
        s1::m1({a});
        c = s2::m1({a, b});
        b = s1::m2({c});
        c = s2::m1({c, b});
        """
        def initialContext = DSLMiscs.newContext {
            data('a', 'T3')
            data('b', 'T2')
        }
        def ccRequest = CCRequest.builder().composition(composition).initialContext(initialContext).build()
        def compiledComp = simpleGateway.compileComposition(ccRequest)

        then:
        compiledComp.programOrder == [0L,1L,2L,3L,4L,5L]

        when:
        /*
         * s1 = a.S1::__construct({1,2});
         *
         * Assigns to s1 service of type a.S1
         *
         * is a constructor
         *
         * takes 1, 2 primitve value with no type coercion
         *
         */
        def staticAnalysisMap = new MappedList<>(compiledComp.staticInstAnalysis,
            { IStaticInstAnalysis it ->  it.instruction.index});
        def accesses = staticAnalysisMap[0L].instFieldAccesses
        def types = staticAnalysisMap[0L].typeContext
        def mr = staticAnalysisMap[0L].methodResolution
        then:
        accesses.count { IFieldAccess access ->
            access.index == 0
            access.field == "s1"
            access.accessType == IFieldAccess.AccessType.ASSIGN
        } == 1
        accesses.every {it.index == 0}
        types.count {
            it.fieldname == "a"
            it.type.getTypeQualifier() == "T3"
        } == 1
        types.count {
            it.fieldname == "b"
            it.type.getTypeQualifier() == "T2"
        } == 1
        types.count {
            it.fieldname == "s1"
            it.type.getTypeQualifier() == "a.S1"
        } == 1
        mr.methodRef.ref.qualifier == "__construct"
        mr.methodRef.serviceRef.ref.qualifier == "a.S1"
        mr.methodRef.serviceRef.serviceCollectionRef.ref.qualifier == "c"
    }

    def "test undeclared type usage"() {
        /*
         * The type `c#a.S2` is not defined. Try to create a service of this type:
         */
        when:
        def composition = """
        s1 = a.S1::__construct({0,0});
        s2 = a.S2::__construct({12031,1295});
        c = s2::m1();
        """
        def initialContext = []
        def ccRequest = CCRequest.builder().composition(composition).initialContext(initialContext).build()
        def compiledComp = simpleGateway.compileComposition(ccRequest)

        then:
        thrown(TypeCheckException)
    }

    def "test undeclared field access"() {
        /*
         * The field `b` is not in the initial context.
         * Try to access it in an instruction
         */
        /*
         * The type `c#a.S2` is not defined. Try to create a service of this type:
         */
        when:
        def composition = """
        c = s1::m1({a, b});
        """
        def initialContext = DSLMiscs.newContext {
            serviceInstance("s1", 'a.S1')
            data('a', 'T1')
            // data('b', 'T2') is missing
        }
        def ccRequest = CCRequest.builder().composition(composition).initialContext(initialContext).build()
        def compiledComp = simpleGateway.compileComposition(ccRequest)
        then:
        thrown(TypeCheckException)
    }

}
