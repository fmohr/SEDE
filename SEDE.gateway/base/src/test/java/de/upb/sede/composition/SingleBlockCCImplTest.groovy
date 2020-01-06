package de.upb.sede.composition

import de.upb.sede.ISDLAssembly
import de.upb.sede.SDLReader
import de.upb.sede.composition.graphs.types.DataValueType
import de.upb.sede.gateway.SimpleGatewayImpl
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

    def positive() {
        when:
        def composition = """
        s1 = a.S1::__construct({1,2});
        s2 = a.S1::__construct({12031,1295});
        s1::m1({a});
        c = s2::m1({a, b});
        b = s1::m2({c});
        c = s2::m1({c, b});
        """.stripMargin()
        def initialContext = [
            FieldType.builder()
                .fieldname('a')
                .type(DataValueType.builder().qualifier('T3').build())
            .build(),
            FieldType.builder()
                .fieldname('b')
                .type(DataValueType.builder().qualifier('T2').build())
            .build()
        ]
        def ccRequest = CCRequest.builder().composition(composition).initialContext(initialContext).build()
        def compiledComp = simpleGateway.compileComposition(ccRequest)
        def instAnalysis = compiledComp.getInstructionAnalysis()

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
        def inst = instAnalysis[0]
        def accessS1 = inst.fieldAccesses[0]
        def types = inst.typeContext
        then:
        inst.instruction.index == 0
        accessS1.index == 0
        accessS1.field == "s1"
        accessS1.accessType == IFieldAccess.AccessType.ASSIGN
        types.any {}




    }


}
