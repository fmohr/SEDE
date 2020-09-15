package ai.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import ai.services.util.DeepImmutableCopier
import ai.services.util.FileUtil

abstract class SDL extends Script {

    Map<String, MutableServiceCollectionDesc> cols = new LinkedHashMap<>()

    boolean writeCollectionAsJson = true

    void setWriteCollectionAsJson(boolean writeCollectionAsJson) {
        this.writeCollectionAsJson = writeCollectionAsJson;
    }

    def collection(String qualifier, @DelegatesTo(MutableServiceCollectionDesc) Closure describer) {
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

        /*
         * Apply user script
         */
        DomainAware.read(col, describer)

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
        if(!writeCollectionAsJson) {
            return
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
