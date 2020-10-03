import ai.services.SDL

@groovy.transform.BaseScript SDL description

collection ("services1") {

    // Add comments
    comment "This is a comment."

    // Define a simple name for the collection:
    simpleName = (qualifier + "_collection").toUpperCase()
    /*
     * Services:
     */
    service ('a.A') {
        simpleName = "Service A"
        isAbstract = true
        implOf "abstracts.A"
        // Set the service to be stateful:
        setStateful {
            semanticType = 'semantics.A'
        }
        /*
         * Declare an empty constructor:
         * This is the the same as:
         *
         * method(name: '$construct', outputs: ['a.A']) {
         *      java { constructorInvocation = true }
         * }
         */
        constructor()

        /*
         * It is also possible to declare a method with a single signature:
         */
        method name: "m2", inputs: ["t1", "t2"], outputs: ["t3"], {
            input(0) {
                /*
                 * Customizing the first input parameter:
                 */
                name = "First Input Parameter of ${qualifier}"
            }
            input(1) {
                callByValue = false
            }
            output {
                name = "Return Value"
            }
        }

        /*
         * Some post processing AFTER the declarations.
         * Iterate over all methods created above:
         */
        eachMethod {
            /*
             * Filter by method qualifier, ie. name:
             */
            if(qualifier == "m1") {
                /*
                 * Iterate over all signatures and change some properties:
                 */
                aux {
                    javaDispatch {
                        staticInvocation = true
                    }
                }
            }
        }
    }
}
