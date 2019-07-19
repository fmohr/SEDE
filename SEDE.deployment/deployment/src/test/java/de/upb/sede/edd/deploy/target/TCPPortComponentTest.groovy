package de.upb.sede.edd.deploy.target

import de.upb.sede.edd.TestHome
import de.upb.sede.edd.deploy.target.components.TCPPortComponent
import spock.lang.Specification

class TCPPortComponentTest extends Specification {

    def "test default port allocate"() {
        def home = new TestHome()
        TCPPortComponent tcpPortComponent = new TCPPortComponent("", home.getChild("PortsTest1"))
        when:
        tcpPortComponent.allocatePort()
        then:
        tcpPortComponent.getAllocatedTcpPort().isPresent()
    }
}
