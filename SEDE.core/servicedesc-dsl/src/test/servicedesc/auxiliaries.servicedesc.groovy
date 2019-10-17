import static de.upb.sede.Helpers.*;
@groovy.transform.BaseScript de.upb.sede.SDL description


collection ("C1") {
    /*
     * Services:
     */
    service ('S1') {
        javaAux = newJavaDispatchAux {
            staticInvocation = true
        }
        params {
            javaAux = newJavaParamAux {
                paramOrder = ['1', '2', '3']
            }
        }
    }

    type('t2') {
        javaAux = newJavaTypeAux {
            dataCastHandler = newJavaDispatchAux {
                className = "org.example.T2Handler"
            }
        }
    }


}
