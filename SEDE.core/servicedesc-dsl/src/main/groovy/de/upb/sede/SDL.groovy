package de.upb.sede

abstract class SDL extends Script {

    Map<String, ServiceCollectionDesc> cols = new HashMap<>()

    def collection(String qualifier, @DelegatesTo(ServiceCollectionDomain) Closure describer) {
        /*
         * Search for an existing collection with the given qualifier.
         */
        def col = cols[qualifier]
        def colDomain
        if(col != null) {
            /*
             * Overwrite the old definition of the collection
             */

            colDomain = new ServiceCollectionDomain(model:
                MutableServiceCollectionDesc.create()
                .from(col))
        } else {
            /*
             * Define new collection
             */
            colDomain = new ServiceCollectionDomain(model:
                MutableServiceCollectionDesc.create()
                .setQualifier(qualifier))
        }
        /*
         * Apply user script
         */
        colDomain.run(describer)

        /*
         * Add the new collection and return it
         */
        def newCol = colDomain.model.toImmutable()
        cols[qualifier] = newCol
        newCol
    }



}
