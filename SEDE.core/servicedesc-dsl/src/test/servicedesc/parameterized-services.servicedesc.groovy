@groovy.transform.BaseScript de.upb.sede.SDL description

collection("service.Collection.1") {
    service("service1") {

        method name: "m1", inputs: ["T1", "T2", "T3"], output: "T4"

        params {

            bool {
                qualifier = 'b1'
                defaultValue = true
            }

            bool name: 'b2', default: true

            bool 'b3', true

            java {
                autoScanEachParam = true
                bundleInMap = true
                bundleInArray = true
                bundleInList = true
                precedeParamsWithNames = true
                paramOrder = ['b3', 'b2', 'b1']
            }
        }
    }
}
