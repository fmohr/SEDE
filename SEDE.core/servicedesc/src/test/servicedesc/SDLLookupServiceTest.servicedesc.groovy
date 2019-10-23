collection ("col1") {

    service ('A') {
        constructor()

        method name: "m1"

        method name: "m2"

    }

    service('B') {
        method name: "m1"
        method name: "m3"
        method name: "m3" // overload method definiton
    }
}

collection ("col2") {

    service ('B') {
        method name: "m1"
    }

    service ('C') {
        method name: 'm1'
    }

}
