package de.upb.sede

import de.upb.sede.util.SDLUtil

abstract class SDL extends Script {

    Map<String, MutableServiceCollectionDesc> cols = new LinkedHashMap<>()


    def collection(String qualifier, @DelegatesTo(ServiceCollectionDomain) Closure describer) {
        /*
         * Search for an existing collection with the given qualifier.
         */
        def col = cols[qualifier]
        if(col == null) {
            /*
             * Define new collection
             */
            col = MutableServiceCollectionDesc.create().setQualifier(qualifier)
            cols[qualifier] = col
        }

        def colDomain = new ServiceCollectionDomain(model: col)
        /*
         * Apply user script
         */
        colDomain.read(describer)

        return col
    }


}
