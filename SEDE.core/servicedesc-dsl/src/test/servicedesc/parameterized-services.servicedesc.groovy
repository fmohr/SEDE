import de.upb.sede.Helpers

import static de.upb.sede.Helpers.*;
@groovy.transform.BaseScript de.upb.sede.SDL description

collection("service.Collection.1") {
    service("service1") {

        method name: "m1", inputs: ["T1", "T2", "T3"], output: "T4"

        params {

            bool {
                qualifier = 'bParam'
                defaultValue = true
                isOptional = true
            }

            bool name: 'bParam', default: true, optional: true

            bool 'bParam', true, true


            numeric {
                qualifier = 'nParam'
                defaultValue = 10
                isInteger = true
                min = 0.5
                max = 20.5
                splitsRefined = 4
                minInterval = 10
                isOptional = true
            }

            numeric name: 'nParam',
                default: 10,
                min: 0.5,
                max: 20.5,
                isInteger: true,
                splitsRefined: 4,
                minInterval: 10,
                optional: true

            numeric 'nParam',
                10,
                0.5, 20.5,
                true,
                4,
                10,
                true

            category {
                qualifier = 'cParam'
                defaultValue = 'A'
                categories = ['A', 'B', 'C']
                isOptional = true
            }

            category name: 'cParam',
                default: 'A',
                categories: ['A', 'B', 'C'],
                optional: true

            category 'cParam', 'A', ['A', 'B', 'C'], true

            requiredInterface {
                qualifier = 'iParam'
                // cannot set default value:
                // defaultValue = null
                isOptional = true
                interfaceQualifier = "interface.I1"
            }

            requiredInterface name: 'iParam', default: 'A', optional: true, interfaceQualifier: 'interface.I1'

            requiredInterface 'iParam', 'interface.I1', true



            dependency {
                premise = 'b2 in {true}'
                conclusion = 'b3 in {false}'
            }

            dependency pre: 'b2 in {true}', con: 'b3 in {false}'

            dependency 'b2 in {true}', 'b3 in {false}'

            java {
                parameterHandler = newJavaDispatchAux {
                    className = 'Service1ParameterHandler'
                }
                autoScanEachParam = true
                bundleInMap = true
                bundleInArray = true
                bundleInList = true
                precedeParamsWithNames = true
                paramOrder = ['b3', 'b2', 'b1']
            }
        }
    }
}
