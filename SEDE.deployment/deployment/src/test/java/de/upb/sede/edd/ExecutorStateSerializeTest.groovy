package de.upb.sede.edd

import de.upb.sede.config.ExecutorConfiguration
import de.upb.sede.util.FileUtil
import de.upb.sede.util.KneadableJsonObject
import spock.lang.Specification

class ExecutorStateSerializeTest extends Specification {
    def "test knead to ExecutorConfiguration"() {
        given:
        def defaultConfigJsonString = FileUtil.readResourceAsString("deployment/base-executor-config.json");
        def defaultConfig = KneadableJsonObject.fromJson(defaultConfigJsonString)
        when:
        def configuration = defaultConfig.knead(ExecutorConfiguration)
        then:
        configuration.gateways == []
        configuration.executorId != null
        configuration.supportedServices == []
    }
}
