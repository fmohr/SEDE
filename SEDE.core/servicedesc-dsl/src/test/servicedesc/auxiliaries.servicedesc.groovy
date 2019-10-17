import de.upb.sede.exec.auxiliary.JavaDispatchAux
import de.upb.sede.exec.auxiliary.MutableJavaDispatchAux

import static de.upb.sede.Helpers.*;
@groovy.transform.BaseScript de.upb.sede.SDL description


collection ("C1") {
    /*
     * Services:
     */
    service ('S1') {
        javaAux = newJavaDispatchAux {
            staticInvocation = true
            className = "org.example.S1"
        }
        params {
            javaAux = newJavaParamAux {
                dispatchAux = newJavaDispatchAux {
                    className = "org.example.S1ParamSetter"
                }
                paramOrder = ['1', '2', '3']
            }
        }
    }

    type('t2') {

        javaAux = newJavaTypeAux {
            mappedClassName = "org.example.T2"
            dataCastHandler = newJavaDispatchAux {
                className = 'org.example.T2Handler'
            }
        }
    }


}
