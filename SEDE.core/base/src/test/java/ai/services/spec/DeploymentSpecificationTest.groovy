package ai.services.spec


import ai.services.util.DynTypeObject
import spock.lang.Specification

class DeploymentSpecificationTest extends Specification {

    def "test deserialization of json spec"() {
        given:
        def jsonData = """
        {
            "name" : "ServiceCollectionName"
        }
        """
        when:
        def kneadable = DynTypeObject.fromJson(jsonData)
        def spec = kneadable.cast(DeploymentSpecification)
        then:
        spec.name == "ServiceCollectionName"
    }

    def "test deserialization of yaml spec"() {
        given:
        def yamlData =
            """\
        |---
        |name: ServiceCollectionName
        |
        """.stripMargin()
        print(yamlData)
        when:
        def kneadable = DynTypeObject.fromYaml(yamlData)
        def spec = kneadable.cast(DeploymentSpecification)
        then:
        spec.name == "ServiceCollectionName"
    }
}
