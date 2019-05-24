package de.upb.sede.util

import spock.lang.Specification

import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong

class LiveMergeTest extends Specification {

    def time = new AtomicLong(0)

    def setup() {
        TTLCache._TIME_PROVIDER = { time.get() }
    }

    def "test setModificationDelegation"() {
        given:
        def merge = new LiveMerge<Integer, Integer>(10, TimeUnit.MILLISECONDS)

        when:
        def mod = [1: 1, 2: 2]
        merge.setModificationDelegation(mod)
        then:
        merge.get(1) == 1
        merge.get(2) == 2

        when:
        int old1 = merge.put(1, 0)
        boolean removed2 = merge.remove(2)
        Integer old3 = merge.put(3, 3)
        then:
        old1 == 1
        old3 == null
        removed2
        merge.get(1) == 0
        merge.get(3) == 3
        mod == [1: 0, 3: 3]

        when:
        mod[1] = 2
        then:
        merge[1] == 0

        when:
        time.set(11)
        then:
        merge[1] == 2
    }

    def "test isModifiable"() {
        given:
        def merge = new LiveMerge(10, TimeUnit.MILLISECONDS)
        expect:
        !merge.isModifiable()

        when:
        merge[1] = 1
        then:
        thrown(IllegalStateException)

        when:
        merge.setModificationDelegation([:])
        then:
        merge.isModifiable()

    }

    def "test getCurrentMerge"() {
        given:
        def merge = new LiveMerge(10, TimeUnit.MILLISECONDS)
        expect:
        merge.getCurrentMerge() == [:]

        when:
        merge.maps().add([1: 1])
        merge.maps().add([1: 2, 2: 2])
        merge.maps().add([3: 3])

        then:
        merge.getCurrentMerge() == [1: 1, 2: 2, 3: 3]

        when:
        merge.clear()
        def map1 = [1: 1]
        def map2 = [1: 2, 2: 3]
        merge.maps().add(map1)
        merge.maps().add(map2)
        then:
        merge.getCurrentMerge() == [1: 1, 2: 3]
        when:
        map1[2] = 2
        then:
        merge.getCurrentMerge() == [1: 1, 2: 3]
        when:
        time.set(11)
        then:
        merge.getCurrentMerge() == [1: 1, 2: 2]
    }

    def "test maps"() {
        given:
        def merge = new LiveMerge(10, TimeUnit.MILLISECONDS)
        expect:
        merge.maps() == []

        when:
        merge = new LiveMerge([1: 1], 10, TimeUnit.MILLISECONDS)
        then:
        merge.maps() == [[1: 1]]

        when:
        merge.clear()
        then:
        merge.maps() == []
    }

    def "test size"() {
        given:
        def merge = new LiveMerge([:], 10, TimeUnit.MILLISECONDS)
        expect:
        merge.size() == 0
        when:
        merge[1] = 1
        then:
        merge.size() == 1

        when:
        def livemap = [1: 2, 2: 2]
        merge.maps() << livemap
        then:
        merge.size() == 2
        merge[1] == 1
        merge[2] == 2

        when:
        merge.maps() << merge.maps().remove(0)
        then:
        merge.size() == 2
        merge[1] == 2
        merge[2] == 2

        when:
        livemap[3] = 3
        then:
        merge.size() == 2

        when:
        time.set(11)
        then:
        merge.size() == 3
        merge[3] == 3

        when:
        merge.maps().remove(0)
        then:
        merge.size() == 1
        merge[1] == 1
        merge[2] == null
    }

}
