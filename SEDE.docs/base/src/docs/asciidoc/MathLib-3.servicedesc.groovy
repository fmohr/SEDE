collection ('MathLib') {


    def Function = type('ai.libs.Function') {
        javaAux.mappedClassName = 'ai.libs.FunctionDataTypeHandler' // <1>
        semanticType = 'ai.libs.Function' // <2>
    }

    def Dot = type('ai.libs.Dot') {
        javaAux.mappedClassName = 'ai.libs.DotDataTypeHandler'
        semanticType = 'Arr'
    }

    service("DotService") { // <3>
        setStateType(Function.qualifier) // <4>

        method name: 'plug', input: 'Number', output: Dot // <5>
    }

}
