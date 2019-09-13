collection ("services1") {

    // Add comments
    addComments "This is a comment."

    // Define a simple name for the collection:
    simpleName = (qualifier + "_collection").toUpperCase()

    /*
     * Services:
     */
    service ('a.A') {
        simpleName = "Service A"
        isAbstract = true
        addInterfaces "abstracts.A"
        setStateful {
            semanticType = 'semantics.A'
        }
        method ('m1') {
            simpleName = 'method 1'
            /*
             * Each signature block creates a new method signature for a.A::m1
             * 3 ways of defining method signature.
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
                    isMutable = false
                    fixedValue = "SOME_FIXED_VALUE"
                }
            }

        }
    }
}
