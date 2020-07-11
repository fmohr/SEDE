import de.upb.sede.SDL
import groovy.transform.BaseScript

@BaseScript SDL description

import static de.upb.sede.Helpers.*;
import static de.upb.sede.StandardDefs.*;

collection "PrimitiveServices", {
    [0, 1].each {
        service("p.PrimService"+it) {

            constructor(name: "const")

            constructor(name: "constNumber_Number", inputs: [number, number])

            constructor(name: "constStr_Str", inputs: [str, str])

            constructor(name: "constBool_Bool", inputs: [bool, bool])

            method(name: "mNumber_Str_Bool", inputs: [number, str, bool])

            method(name: "mT0_Str", inputs: ["p.T0", str])

        }
    }

    type("p.T0") {
        semanticType= "T0"
    }
}

