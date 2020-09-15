import ai.services.SDL

import static ai.services.Helpers.*;
@groovy.transform.BaseScript SDL description


collection ("C1") {
    /*
     * Services:
     */
    service ('S1') {
        aux {
            javaDispatch {
                staticInvocation = true
                className = "org.example.S1"
            }
        }
        params {
            aux {
                javaParam {
                    parameterHandler = newJavaDispatchAux {
                        className = "org.example.S1ParamSetter"
                    }
                    paramOrder = ['1', '2', '3']
                }
            }
            aux {
                setFields {
                    a = 1
                }

            }
        }
    }

    type('t2') {
        aux {
            javaMarshalling {
                mappedJavaClass = "org.example.T2"
                useCustomHandler = true
                handlerClass = 'org.example.T2Handler'
            }
        }
    }


}
