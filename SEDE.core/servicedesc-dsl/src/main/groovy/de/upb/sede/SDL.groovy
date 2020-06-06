package de.upb.sede

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import de.upb.sede.util.DeepImmutableCopier
import de.upb.sede.util.FileUtil
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

    abstract def runScript()

    def run() {
        runScript();
        ISDLAssembly sdlAssembly = DeepImmutableCopier.copyAsImmutable(SDLAssembly.builder()
            .collections(cols.values())
            .build())

        if(sdlAssembly.collections.isEmpty()) {
            return;
        }
        IServiceCollectionDesc serviceCollection  = sdlAssembly.collections.stream()
            .sorted(Comparator.comparingInt({ IServiceCollectionDesc it -> it.getServices().size()}).reversed())
            .findFirst()
            .get();
        def filename = new File(serviceCollection.qualifier + ".servicedesc.json")
        FileUtil.prepareOutputFile(filename)
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(filename, sdlAssembly)
    }

}
