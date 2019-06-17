package de.upb.sede.edd


import de.upb.sede.edd.deploy.model.DeploymentSpecificationRegistry
import de.upb.sede.util.FileUtil
import spock.lang.Specification


class ServiceCollectionModelTests extends Specification {
    static DeploymentSpecificationRegistry registry
    def setupSpec()  {
        registry = DeploymentSpecificationRegistry.fromString(
            FileUtil.readResourceAsString("test-deployconf-1.json"))
    }

    def "test registry" () {
        expect:
        registry.size() == 3

        registry.findByName("ServiceCollection1").isPresent()
        registry.findByName("ServiceCollection2").isPresent()
        registry.findByName("ServiceCollection3").isPresent()
        ! registry.findByName("Anything else").isPresent()
        ! registry.findByName("").isPresent()
        ! registry.findByName(null).isPresent()

        registry.findByName("Collection1").isPresent()
        registry.findByName("Services1").isPresent()
        registry.findByName("Collection1").get().name == "ServiceCollection1"
        registry.findByName("Services1").get().name == "ServiceCollection1"

        registry.findByName("KindaUselessCollection").isPresent()
        registry.findByName("KindaUselessCollection").get().name == "ServiceCollection3"

        registry.findByService("SC1.s1").isPresent()
        registry.findByService("SC2.s2").isPresent()
        registry.findByService("SC2.s2").get().name == "ServiceCollection2"

//        when:
//        def set = registry.collect(names, services, quite)
//        then:
//        set.size() == size
//        where:
//        services | quite | 
        registry.collect([], false, true).isEmpty()
        registry.collect(["ServiceCollection1"], false, true).size() == 1
        registry.collect(["ServiceCollection1", "ServiceCollection2"], false, true).size() == 2
        registry.collect(["ServiceCollection1", "SC1.s1"], false, true).size() == 1
        registry.collect(["ServiceCollection1", "SC1.s1"], true, true).size() == 1
        registry.collect(["ServiceCollection1", "SC2.s3"], false, true).size() == 1
        registry.collect(["ServiceCollection1", "SC2.s3"], true, true).size() == 2
        registry.collect(["SC1.s2", "SC2.s3"], false, true).size() == 0
        registry.collect(["SC1.s2", "SC2.s3"], true, true).size() == 2

        when:
        def specList = registry.order().get()
        def sc3DeplIndex = specList.indexOf(registry.findByName("ServiceCollection3").get())
        def sc1DeplIndex = specList.indexOf(registry.findByName("ServiceCollection1").get())

        then:
        sc3DeplIndex > sc1DeplIndex
    }

    def "test spec"() {
        given:
        def sc1 = registry.findByName("ServiceCollection1").get()
        expect:
        sc1.services == ["SC1.s1",
                         "SC1.s2",
                         "SC1.s3"]
        sc1.target == "JavaExecutor"
        sc1.alias == ["Collection1", "Services1"]

    }

}
