import ai.services.SMath
import ai.services.Defaults
import ai.services.SDL
import groovy.transform.BaseScript
import static ai.services.StandardDefs.*

@BaseScript SDL description

collection "StaticServices", {
    service "SS.Math", {
        aux {
            javaDispatch {
                staticInvocation = true
                className = SMath.class.name
            }
        }

        Defaults.withDefaults({
            setDefaultMethodSignature inputs: [ number, number ], output: number
            setDefaultMethod {
                isPure = true
                isContextFree = true
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
    service "SS.String", {

    }
}

