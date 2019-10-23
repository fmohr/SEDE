collection("MathLib") {
    service("service1") {
        params {

            bool name: 'b2', default: true
            bool name: 'b3', default: false  // <1>

            numeric name: 'nParam',  // <2>
                default: 10,
                min: 0.5,
                max: 20.5,
                isInteger: true,
                splitsRefined: 4,
                minInterval: 10,
                optional: true

            category {  // <3>
                qualifier = 'cParam'
                defaultValue = 'A'
                categories = ['A', 'B', 'C']
                isOptional = true
            }

            dependency pre: 'b2 in {true}', con: 'b3 in {false}'  // <4>
        }
    }
}
