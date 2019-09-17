def Instances = 'LabeledInstances'
def number = 'Number'
def str = 'String'
def list = 'builtin.List'

collection ("weka.ml") {
    simpleName = "Weka Library"

    info = """
        Weka is a collection of machine learning algorithms for data mining tasks. It contains tools for data preparation, classification, regression, clustering, association rules mining, and visualization.
        
        Weka is open source software issued under the GNU General Public License.
        
        Website: https://www.cs.waikato.ac.nz/~ml/weka/index.html
    """

    /*
     * Services:
     */
    service('de.upb.sede.services.mls.Labeler') {

        /*
         * This service is stateless and thus 'setStateful' is not invoked.
         */

         // Method definitions: 
        method name: 'labelDataset',
            inputs: [Instances, list],
            outputs: [Instances], {
                input(0) { callByValue = false }
                java { redirectArg = 0 }
            }

        method name: 'setClassIndex',
            inputs: [Instances, number],
            outputs: [Instances], {
                input(0) { callByValue = false }
                java { redirectArg = 0}
            }

        method name: 'classIndicesToNames',
            inputs: [list, list],
            outputs: [list]

        method name: 'classIndicesPrettify',
            inputs: [list],
            outputs: [list]

        method name: 'categorize',
            inputs: [list],
            outputs: [list]
            
        // All methods are statically invoked:
        eachMethod { eachSignature { java { staticInvocation = true } } }
    }

    service('de.upb.sede.services.mls.DataSetService') {
        setStateful()

        /*
         * Constructors and builders:
         */
        constructor inputs: [str]
        method name: 'createUnique',
                inputs:[Instances],
                ouputs:[service.stateType], {
                    java { staticInvocation = true }
                }
        method name: 'createNamed',
                inputs: [Instances, str],
                outputs:[service.stateType], {
                    java { staticInvocation = true }
                }

        /*
         * All following methods are pure.
         * Set a default method configuration:
         */
        defaults.method = { isPure = true }
        method name: 'all',
                outputs: [Instances]

        method name: 'allLabeled',
                inputs: [number],
                outputs: [Instances]

        method name: 'fromIndices',
                inputs: [list],
                outputs: [Instances]

        method name: 'fromIndicesLabeled',
                inputs: [list, number],
                outputs: [Instances]

        // Clear default method configuration
        defaults.clearMethod()
    }

    service('$list_option_handler_config$') {
        isAbstract = true
        method name: 'set_options',
                inputs:[list]
    }

}
