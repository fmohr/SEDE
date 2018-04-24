

def getjavasource_dataclass():
    return """package de.upb.sede.{package};


    /**
    * Immutable Dataclass 
    */
    public class {JavaClassName} {{
        /*
            Attribute declarations
        */
        {declaration}

        /*
            Constructor
        */
        {constructor}

        /*
            With methods
        */
        {withmethods}

        /*
            Has methods
        */
        {hasmethods}

        /*
            Get methods
        */
        {getmethods}
    }}
    """

def getjavasource_attrdeclaration():
    return """
    private final static {type} UNDEFINED_{attrname_caps} = "NO_RID";
    private final {type} {attrname};
    """


def getjavasource_constructors():
    return """ 
    public {JavaClassName}() {{
        {SetDefaults}
    }}

    private {JavaClassName}({ConstructorParameters}){{
        {SetParameters}
    }}
    """

def getjavasource_constructorattributeset():
    return """
        this.{attrname} = {set_to};
    """

def generate(classinfo):
    classname=classinfo["classname"]
    javasource = getjavasource_dataclass()  \
        .format(package=classinfo["package"],JavaClassName=classname)

    declarations = []
    constructor_setdefaults = []
    constructor_setparams = []
    constructor_params = []
    for attributename in classinfo["attributes"]:
        # attribute declarations
        typename = classinfo["attributes"][attributename]
        attribute_caps = attributename.upper()
        attrdeclration = getjavasource_attrdeclaration().format(type=typename, attrname=attributename, attrname_caps=attribute_caps)
        declarations.append(attrdeclration)

        # constructor
        staticundefinedreference = """{JavaClassName}.{StaticVariable}""" .format(JavaClassName=classname, StaticVariable)
        getjavasource_constructorattributeset().format(attrname=attributename,set_to)



