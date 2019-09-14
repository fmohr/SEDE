def dataset = 'LabeledInstances'
def number = 'Number'
def str = 'String'
def list = 'builtin.List'

collection ("weka.ml") {

    comment """
        Weka is a collection of machine learning algorithms for data mining tasks. It contains tools for data preparation, classification, regression, clustering, association rules mining, and visualization.""",
    """
        Weka is open source software issued under the GNU General Public License.""",
    """
        Website: https://www.cs.waikato.ac.nz/~ml/weka/index.html
    """
    comments = comments.collect { it.trim() }

    simpleName = "Weka Library"

    /*
     * Services:
     */
    service('de.upb.sede.services.mls.Labeler') {

        /*
         * This service is stateless and thus 'setStateful' is not invoked.
         */

         // Method definitions: 
        method name: 'labelDataset',
            inputs: [dataset, list],
            outputs: [dataset], {
                input(0) { callByValue = false }
                java { redirectArg = 0 }
            }

        method name: 'setClassIndex',
            inputs: [dataset, number],
            outputs: [dataset], {
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
        methods { signatures { java { staticInvocation = true } } }
    }

    service('de.upb.sede.services.mls.DataSetService') {
        def serviceName = 'de.upb.sede.services.mls.DataSetService'
        setStateful()
        constructor inputs: [str]
        method name: 'createUnique'
    }

}
