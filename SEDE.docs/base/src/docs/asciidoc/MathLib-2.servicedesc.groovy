
def number = 'Number'   // <1>

collection ('MathLib') {

    simpleName = 'Classic Math Library' // <2>

    service ('ai.libs.Primes') { // <3>
        simpleName = 'Prime Numbers Service'

        method name: 'generateRandom',  // <4>
            inputs: [number, number],
            output: number

        method name: 'generateRandom', // <5>
            output: number


        method name: 'generateNext',
            input: number,
            output: number, {
                // <6>
                input(0) { name = 'floor' } // <7>
                isPure = true // <8>
        }
    }
}
