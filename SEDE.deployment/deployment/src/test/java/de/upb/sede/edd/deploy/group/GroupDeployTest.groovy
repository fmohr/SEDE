package de.upb.sede.edd.deploy.group

import de.upb.sede.edd.EDD
import de.upb.sede.edd.TestHome
import de.upb.sede.edd.deploy.group.transaction.AddServicesTransaction
import de.upb.sede.edd.deploy.group.transaction.CreateGroupTransaction
import de.upb.sede.edd.deploy.group.transaction.RemoveGroupTransaction
import de.upb.sede.edd.deploy.model.DeploymentSpecificationRegistry
import de.upb.sede.edd.process.ProcessHandleState
import de.upb.sede.util.FileUtil
import de.upb.sede.util.Uncache
import spock.lang.Specification


class GroupDeployTest extends Specification {
    def "test group deployment"() {
        given:
        def testHome = new TestHome();
        def registry = DeploymentSpecificationRegistry
            .fromString(FileUtil.readResourceAsString("deployment/sede.services-deployconf.json"));
        def groupRepo = new GroupRepository(testHome.toEDDHome(), { registry });

        expect:
        !groupRepo.getGroup(null).isPresent()

        when:
        def createGroup = new CreateGroupTransaction();
        def created = groupRepo.createGroup(createGroup)
        then:
        created
        groupRepo.getGroup(null).isPresent()

        when:
        def newServices = new AddServicesTransaction()
        newServices.settings.update = false
        newServices.settings.waitInQueue = false
        newServices.services = ["Weka.ml"]
        groupRepo.getGroup(null).get().commitAdditionalServices(newServices)
        then:
        ((LocalDeploymentGroup)groupRepo.getGroup(null).get()).componentInstances.getJavaExecutor(null).executorProcess.javaProcessHandle.get().state == ProcessHandleState.STARTED

        when:
        Thread.sleep(10000)
        createGroup = new CreateGroupTransaction()
        createGroup.groupName = "catalanoexecutor"
        groupRepo.createGroup(createGroup);
        newServices = new AddServicesTransaction()
        newServices.settings.update = false
        newServices.settings.waitInQueue = false
        newServices.services = ["Catalano.image"]
        groupRepo.getGroup("catalanoexecutor").get().commitAdditionalServices(newServices)
        then:
        ((LocalDeploymentGroup)groupRepo.getGroup(null).get()).componentInstances.getJavaExecutor(null).executorProcess.javaProcessHandle.get().state == ProcessHandleState.STARTED
        ((LocalDeploymentGroup)groupRepo.getGroup(null).get()).componentInstances.getJavaExecutor(null).executorProcess.javaProcessHandle.get().waitForFinish()


    }

}
