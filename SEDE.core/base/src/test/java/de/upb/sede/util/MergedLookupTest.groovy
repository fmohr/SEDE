package de.upb.sede.util

import spock.lang.Specification

class MergedLookupTest extends Specification {

    def "merge lookup test" () {
        given:
        def mergedLookup

        when:
        mergedLookup = new MergedLookup<>([-1, -2, -3, 4, -5, 6])
        def firstPositive = mergedLookup.firstMatching({
            it >= 0
        })
        def allPositive = mergedLookup.allMatching({
            it >= 0
        }).iterator()

        then:
        firstPositive.isPresent()
        firstPositive.get() == 4
        [allPositive.next(), allPositive.next()] == [4, 6]
        !allPositive.hasNext()

        when:
        allPositive.next()
        then:
        thrown(NoSuchElementException)
    }
}
