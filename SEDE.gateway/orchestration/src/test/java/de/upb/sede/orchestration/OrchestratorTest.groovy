package de.upb.sede.orchestration

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.internal.operators.flowable.FlowableCombineLatest
import spock.lang.Specification

class OrchestratorTest extends Specification {

    def "test rxJava"() {
        when:
        Flowable.just("Hello world").subscribe({println it});
        then:
        true
    }
}
