package de.upb.sede.edd

import de.upb.sede.edd.deploy.DeploymentContext
import de.upb.sede.edd.deploy.DeploymentWorkflow
import de.upb.sede.edd.deploy.SingleTargetOR
import de.upb.sede.edd.deploy.model.DeploymentSpecificationRegistry
import de.upb.sede.edd.process.ClassPath
import de.upb.sede.util.FileUtil
import spock.lang.Specification


class TestGithubRepos extends Specification {

    static DeploymentSpecificationRegistry registry
    def setupSpec()  {
        registry = DeploymentSpecificationRegistry.fromString(
            FileUtil.readResourceAsString("test-deployconf-testrepos.json"))
    }

    def "test gradle project"() {
        when:
        def context = new DeploymentContext()
        context.settings.update = true
        def workflow = DeploymentWorkflow.createWorkflow(context, registry, ["EDD.TestRepo.GradleProject.Library"])
        then:
        workflow.steps.size() == 1

        when:
        def home = new TestHome()
        workflow.deploy(home.getChild("services"))
        SingleTargetOR or = new SingleTargetOR();
        or.setClassPath(new ClassPath())
        context.collectOutputs(or)
        then:
        true
//        or.getClassPath().
    }

    def "test sede.services project"() {
        given:
        def registry = DeploymentSpecificationRegistry.fromString(
            FileUtil.readResourceAsString("deployment/sede.services-deployconf.json"))
        when:
        def context = new DeploymentContext()
        context.settings.update = true
        def workflow = DeploymentWorkflow.createWorkflow(context, registry,
            ["Demolib"])
        def home = new TestHome()
        workflow.deploy(home.getChild("services"))
        then:
//        for(def depl : context.deployment) {
//            for(def output : depl.outputs) {
//                output.exists()
//            }
//        }
        true
    }
}
