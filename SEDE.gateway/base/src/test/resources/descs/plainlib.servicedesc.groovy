package descs

import de.upb.sede.SDL
import groovy.transform.BaseScript

@BaseScript SDL description

import static de.upb.sede.Helpers.*;
import static de.upb.sede.StandardDefs.*;

collection("plainlibs") {

    def NumbersT = "plainlib.package1.b.Numbers"

    type("plainlib.package1.b.Numbers") {
        aux {
            javaType {
                mappedClassName = 'plainlib.package1.b.NumbersCaster1'
            }
        }
        semanticType = "Arr"
    }

    service("plainlib.package1.b.B") {
        constructor inputs: [number, number]
        method name: "calc", input: number, output: number, {
            isPure = true
        }
        method name: "calc_append_inplace", inputs: [NumbersT, number],  {
            input(0) {
                readOnly = false
            }
        }
        method name: "calc_append", inputs: ["plainlib.package1.b.Numbers", number], output: number, {
            isPure = true
        }
        method name: "set_a", input: number, {
            isPure = false
        }
        method name: "set_b", input: number, {
            isPure = false
        }
        method name: "upper", input: str, output: str, {
            isContextFree = true
        }
        method name: "__str__", output: str, {
            isPure = true
        }
        method name: "random", output: model.qualifier, {
            isContextFree = true
        }
    }
}
