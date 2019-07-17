package de.upb.sede.edd.deploy.group


import de.upb.sede.edd.TestHome
import de.upb.sede.edd.deploy.AscribedService
import de.upb.sede.edd.deploy.deplengine.InstallationState
import spock.lang.Specification


class DeplEngineTest extends Specification {


    def "test deployment"() {
        given:
        def testHome = new TestHome()
        def edd = TestHome.edd()
        def engine = edd.deploymentEngine.default
        expect:
        edd.getHome().homeDir.toFile() == testHome.toFile()
        engine.currentState.isEmpty()

        when:
        def testRepoService = AscribedService.fromClasspath("test-deployconf-testrepos.json", "EDD.TestRepo.GradleProject.Library")
        engine.addServices([testRepoService])
        then:
        engine.currentState.size() == 1
        engine.currentState[0].serviceCollectionName == "EDD-TestRepo-GradleProject"
        engine.currentState[0].includedServices.size() == 1
        engine.currentState[0].state == InstallationState.Init

        when:
        engine.prepareDeployment(true)
        then:
        engine.currentState[0].state == InstallationState.Success
        when:
        def weka = AscribedService.fromClasspath("deployment/sede.services-deployconf.json", "Weka.ml")

        def catalano = AscribedService.fromClasspath("deployment/sede.services-deployconf.json", "Catalano.image")
        engine.addServices([weka, catalano])
        then:
        engine.currentState.size() == 3
        engine.currentState[1].serviceCollectionName == "Weka.ml"
        engine.currentState[1].state == InstallationState.Init
        when:
        engine.prepareDeployment(true)
        then:
        engine.currentState[1].state == InstallationState.Success
        engine.currentState[2].state == InstallationState.Success
    }

    def "test http source deployment"() {
        given:
        def testHome = new TestHome()
        def edd = TestHome.edd()
        def engine = edd.deploymentEngine.default
        expect:
        edd.getHome().homeDir.toFile() == testHome.toFile()
        engine.currentState.isEmpty()

        when:
        def testRepoService =
            AscribedService.parseURI("http://localhost:7000#Catalano.Imaging.Filters.Crop")
            //new AscribedService(SpecURI.parseURI("localhost:7000"), "Catalano.Imaging.Filters.Crop")
            //AscribedService.fromGateway("localhost:7000", "Catalano.Imaging.Filters.Crop")
        engine.addServices([testRepoService])
        then:
        engine.currentState.size() == 1
        engine.currentState[0].serviceCollectionName == "Catalano.image"
//        engine.currentState[0].includedServices.size() == 33
        engine.currentState[0].state == InstallationState.Init

        when:
        engine.prepareDeployment(true)
        then:
        engine.currentState[0].state == InstallationState.Success
    }

}
