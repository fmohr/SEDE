package descs

import ai.services.SDL
import groovy.transform.NamedDelegate

import static ai.services.StandardDefs.*;

@groovy.transform.BaseScript SDL description


collection ("demolib") {
    simpleName = "SEDE Demo Library"

    info = """
    This library serves as a demonstration and testing library for integration tests in SEDE.
    Implementation: https://github.com/fmohr/SEDE.services/tree/master/demolib/src/main
"""

    /*
     * Types:
     */
    def typeDef = {
        aux {
            javaMarshalling{
                useCustomHandler = true
                handlerClass = 'ai.services.NewDemoCaster'
                useLegacyPattern = true
            }
        }
        semanticType = 'Arr'
    }
    def NummerList = type('demo.types.NummerList', typeDef)
    def Punkt = type('demo.types.Punkt', typeDef)

    /*
     * Services:
     */
    service('demo.math.Addierer') {

        setStateful()
        constructor(input: number)
        constructor()

        method (name: 'addier', input: number, output: number) {
            isPure = true
        }

        method (name: 'addierListe', input: NummerList, output: NummerList) {
            isPure = true
        }

        method (name: 'summier', inputs: [NummerList, NummerList], output: NummerList) {
            isPure = true
            isContextFree = true
            aux {
                javaDispatch {
                    methodName = "summierListe"
                }
            }
        }

        method (name: 'summier', input: NummerList, output: number) {
            isPure = true
        }

        method (name: 'fail', output: NummerList) {
            isContextFree = true
        }

        method (name: 'sleep') {
            isContextFree = true
        }

        method (name: 'addierBuiltIn', inputs: [list, number], output: list) {
            isContextFree = true
        }
    }

    service('demo.math.Gerade') {

        setStateful()
        constructor(inputs: [number, number])

        method (name: 'calc', input: number, output: Punkt)

        method (name: 'liegtAufGerade', input: Punkt, output: bool) {
            isPure = true
            aux{
                javaDispatch {
                    methodName = 'liagtAufGerade'
                }
            }
        }

        method (name: 'achsenabschnitt', output: Punkt) {
            isPure = true
        }

        method (name: 'nullstelle', output: Punkt) {
            isPure = true
        }
    }
}
