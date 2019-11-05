import de.upb.sede.Helpers

import static de.upb.sede.Helpers.*;
@groovy.transform.BaseScript de.upb.sede.SDL description

collection("service.Collection.1") {
    service("service1") {

        implOf 'Provided_Interface_1', 'Provided_Interface_2'

        params {

            bool name: 'bParam', default: true, optional: true

            numeric name: 'nParam',
                default: 10,
                min: 0.5,
                max: 20.5,
                isInteger: true,
                splitsRefined: 4,
                minInterval: 10,
                optional: true

            numeric name: 'nParam',
                default: 10,
                min: 0.5,
                max: 20.5,
                isInteger: false,
                splitsRefined: 4,
                minInterval: 10,
                optional: true


            category name: 'cParam',
                default: 'A',
                categories: ['A', 'B', 'C'],
                optional: true

            requiredInterface name: 'iParam',
                optional: true,
                interfaceQualifier: 'interface.I1'


            dependency pre: 'b2 in {true}', con: 'b3 in {false}'

        }
    }
}
