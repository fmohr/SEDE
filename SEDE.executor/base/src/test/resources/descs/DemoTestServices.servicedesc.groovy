import de.upb.sede.Defaults
import de.upb.sede.SDL
import groovy.transform.BaseScript
import static de.upb.sede.StandardDefs.*

@BaseScript SDL description

collection "StaticServices", {
    service "SMath", {
        Defaults.withDefaults({
            setDefaultMethodSignature inputs: [ number, number ], output: number
            setDefaultMethod {
                isPure = true
                isContextFree = true
                aux {
                    javaDispatch {
                        staticInvocation = true
                    }
                }
            }
        }) {
            method(name: "addPrimitive")
            method(name: "subtractPrimitive")
            method(name: "multiplyPrimitive")
            method(name: "dividePrimitive")
            method(name: "addObject")
            method(name: "subtractObject")
            method(name: "multiplyObject")
            method(name: "divideObject")
        }
    }
    service "SString", {

    }
}

