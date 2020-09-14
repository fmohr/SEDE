package ai.services.executor.local

import ai.services.executor.ExecutorFactory
import de.upb.sede.gateway.StdGatewayImpl
import spock.lang.Specification

class TestStdExecution extends Specification {

    def "singleExecutor"() {
        def gateway = new StdGatewayImpl()
        def factory = new ExecutorFactory()
        factory.getConfigBuilder().addServices("")
    }
}
