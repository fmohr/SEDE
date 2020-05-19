package de.upb.sede


import de.upb.sede.exec.MethodRef
import spock.lang.Specification
import spock.lang.Unroll

import static IServiceCollectionRef.of

class SDLLookupServiceTest extends Specification {

    def static final TEST_BASE = "src/test/servicedesc/SDLLookupServiceTest.servicedesc.groovy"


    @spock.lang.Shared SDLBaseLookupService baseLookupService
    @spock.lang.Shared SDLCacheLookupService cacheLookupService
    @spock.lang.Shared SDLCacheLookupService doubleCacheLookupService

    void setupSpec() {
        SDLReader reader = new SDLReader()
        reader.read(new File(TEST_BASE))
        def serviceDescCollection = reader.collections
        def sdlBase = SDLAssembly.builder().collections(serviceDescCollection).build()
        baseLookupService = new SDLBaseLookupService(sdlBase)
        cacheLookupService = new SDLCacheLookupService(baseLookupService)
        doubleCacheLookupService = new SDLCacheLookupService(cacheLookupService)
    }

    @Unroll
    void "test collection lookup"() {

        when: 'Positive collection lookup test'
        def optCol1 = ls.lookup(
            of("col1"))
        def optCol2 = ls.lookup(
            of("col2"))

        then:
        optCol1.isPresent()
        optCol2.isPresent()
        optCol1.get().qualifier == 'col1'
        optCol2.get().qualifier == 'col2'
        optCol1.get() != optCol2.get()

        when: 'looking for an undefined collection'
        def optCol3 = ls.lookup(
            of("col3"))

        then: 'the lookup service shall return an empty Optional'
        !optCol3.isPresent()

        where:
        ls | _
        baseLookupService | _
        cacheLookupService | _
        doubleCacheLookupService | _
    }

    @Unroll
    void "test service lookup" (){
        when:
        def optCol1A = ls.lookup(IServiceRef.of("col1", 'A'))
        def optCol1B = ls.lookup(IServiceRef.of("col1", 'B'))
        def optCol1C = ls.lookup(IServiceRef.of("col1", 'C'))

        def optCol2A = ls.lookup(IServiceRef.of("col2", 'A'))
        def optCol2B = ls.lookup(IServiceRef.of("col2", 'B'))
        def optCol2C = ls.lookup(IServiceRef.of("col2", 'C'))


        then:
        optCol1A.isPresent()
        optCol1B.isPresent()
        !optCol1C.isPresent()

        !optCol2A.isPresent()
        optCol2B.isPresent()
        optCol2C.isPresent()

        when:
        def optA = ls.lookup(IServiceRef.of(null, "A"))
        def optB = ls.lookup(IServiceRef.of(null, "B"))
        def optC = ls.lookup(IServiceRef.of(null, "C"))

        then:
        optA.isPresent()
        optA.get() == optCol1A.get()
        !optB.isPresent()
        optC.isPresent()
        optC.get() == optCol2C.get()

//        when:
//        IServiceRef ref = null
//        ls.lookup(ref)
//        then:
//        thrown(NullPointerException)

        where:
        ls | _
        baseLookupService | _
        cacheLookupService | _
        doubleCacheLookupService | _
    }

    void "data-driven test method lookup"(SDLLookupService ls,
                                          String collection, String service, String method, boolean result) {
        when:
        def methodRef = MethodRef.builder()
            .serviceRef(IServiceRef.of(collection, service))
            .ref(IQualifiable.of(method))
            .build()

        def optMethod = ls.lookup(methodRef)

        then:
        optMethod.isPresent() == result


        where:
        ls = baseLookupService
        collection  | service   | method    || result
        'col1'      | 'A'       | 'm1'      || true
        'col1'      | 'A'       | 'm2'      || true
        'col1'      | 'A'       | 'm3'      || false

        'col1'      | 'B'       | 'm1'      || true
        'col1'      | 'B'       | 'm2'      || false
        'col1'      | 'B'       | 'm3'      || true

        'col2'      | 'A'       | 'm1'      || false
        'col2'      | 'B'       | 'm1'      || true

        null        | 'A'       | 'm1'      || true
        null        | 'B'       | 'm1'      || false
        null        | 'C'       | 'm1'      || true
        null        | 'A'       | 'm2'      || true
        null        | 'B'       | 'm2'      || false
        null        | 'C'       | 'm2'      || false
    }

    @Unroll
    void "test method lookup"() {
        when:
        def methodRef = MethodRef.builder()
            .serviceRef(IServiceRef.of('col1', 'A'))
            .ref(IQualifiable.of('m1'))
            .build()

        def optMethod = ls.lookup(methodRef)

        then:
        optMethod.isPresent()

        where:
        ls | _
        baseLookupService | _
        cacheLookupService | _
        doubleCacheLookupService | _
    }

}
