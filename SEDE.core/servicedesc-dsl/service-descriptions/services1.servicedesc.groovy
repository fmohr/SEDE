collection "services1", {

    // Add comments
    addComments "This is a comment."

    // Define a simple name for the collection:
    simpleName = (qualifier + "_collection").toUpperCase()

    /*
     * Services:
     */
    service 'a.A', {
        simpleName = "Service A"
        isAbstract = true
        addInterfaces "abstracts.A"
        setStateful {
            semanticType = 'semantics.A'
        }
        method 'm1', {
            simpleName = 'method 1'
        }
    }
}
