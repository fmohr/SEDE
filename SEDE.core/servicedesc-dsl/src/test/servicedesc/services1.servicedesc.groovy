@groovy.transform.BaseScript de.upb.sede.SDL description

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
         * Declare a method with multiple signatures:
         */
        overloadMethod ('m1') {
            simpleName = 'method 1'
            /*
             * Each signature block creates a new method signature for a.A::m1
             * 4 ways of defining method signature.
             */
            signature {
                addInputTypes('t1', 't2', 't3') // or setInputTypes
                addOutputTypes('t4', 't5') // or setOutputTypes
            }
            signature {
                addInput type: 't1', name: 'in1'
                addInput type: 't2', name: 'in2'
                addOutput type: 't3', name: 'out1'
            }

            signature {
                inputs += param type: 't1', name: 'in1'
                inputs += param {
                    type = 't2'
                    callByValue = false
                    fixedValue = "SOME_FIXED_VALUE"
                }
            }
            /*
             * This signature block first defines the input and output types.
             */
            signature inputs: ['t1', 't2'], outputs: ['t3'], {
                /*
                 * And here it continues to configure the signature.
                 */
                input(0) {
                    name = "First Input Parameter"
                }
                input(1) {
                    callByValue = false
                }
                output {
                    name = "Return Value"
                }
            }


        }

        /*
         * It is also possible to declare a method with a single signature:
         */
        method name: "m2", inputs: ["t1", "t2"], outputs: ["t3"], {
            input(0) {
                /*
                 * Customizing the first input parameter:
                 */
                name = "First Input Parameter of $method.qualifier"
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
                eachSignature {
                    javaAux.staticInvocation = true
                }
            }
        }
    }
}
