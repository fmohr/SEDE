package de.upb.sede.util


import spock.lang.Specification

import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import java.util.function.Supplier

class CacheTest extends Specification {

    def "static cache test" () {
        given:
        def a = 1
        def b = "hello"

        when:
        def cacheA = new StaticCache(a)
        def cacheB = new StaticCache(b)
        then:
        cacheA.get() == 1
        cacheA.access() == 1
        cacheB.get() == "hello"
        cacheB.access() == "hello"

        when:
        new StaticCache<>(null)
        then:
        thrown(NullPointerException)
    }

    def "uncache test"() {
        given:
        def contentSupplier = Mock(Supplier)

        when:
        def cache = new Uncache(contentSupplier)
        then:
        0 * contentSupplier.get() >> 1 // supplier wasnt access yey
        when:
        def content = cache.access()
        then:
        content == 1
        1 *  contentSupplier.get() >> 1 // supplier is accessed at first access

        when:
        content = cache.access()
        content = cache.get()

        then:
        content == 2
        2 *  contentSupplier.get() >> 2 // supplier is accessed anotther two times

    }

    def "supplier cache test"() {
        given:
        def contentSupplier = Mock(Supplier)
        when:

        def contentSupplied = new LazyAccessCache(contentSupplier).get()
        def contentSupplied2 = new LazyAccessCache(contentSupplier).get()
        then:
        contentSupplied == 1
        contentSupplied2 == 2
        1 * contentSupplier.get() >> 1
        1 * contentSupplier.get() >> 2

        when:
        def cache = new LazyAccessCache<Integer>(contentSupplier)
        then:
        0 * contentSupplier.get() // supplier wasnt access yet

        when:
        def content = cache.access()
        content = cache.access()
        then:
        content == 1
        1 *  contentSupplier.get() >> 1// supplier is accessed at first access

    }

    def "ttl cache test" () {
        given:
        def content = "some content"
        def cache

        when:
        cache = new TTLCache<String>(content)
        def contentAccess = cache.access()
        def contentGet = cache.get()
        then:
        contentAccess == content
        contentGet == "some content"

        when:
        def customTime = Stub(Supplier)
        customTime.get() >>> [0L, 5L, 20L]
        TTLCache._TIME_PROVIDER = customTime
        cache = new TTLCache<String>(content, 10, TimeUnit.MILLISECONDS, new StaticCache(content))

        then:
        // 5
        cache.access() == "some content"
        // 20
        cache.access() == "some content"

        when:
        AtomicLong time = new AtomicLong(-1);
        TTLCache._TIME_PROVIDER = { time.get() }
        def contentSupplier = Stub(Supplier)
        AtomicInteger counter = new AtomicInteger(0);
        contentSupplier.get() >> { counter.getAndIncrement() }
        cache = new TTLCache<Integer>(-1, 100, TimeUnit.MILLISECONDS, contentSupplier)
        time.set(0L)
        def content0 = cache.access()
        def content0_ = cache.access()
        time.set(50L)
        def content50 = cache.access()
        time.set(100L)
        def content100 = cache.access()
        def content100_ = cache.access()
        time.set(200L)
        def content200 = cache.access()
        time.set(250L)
        def content250 = cache.access()
        def content250_ = cache.access()
        time.set(280L)
        def content280 = cache.access()
        time.set(300L)
        def content300 = cache.access()
        time.set(350L)
        def content350 = cache.access()

        then:
        content0 == -1
        content0_ == -1
        content50 == -1
        content100 == 0
        content100_ == 0
        content200 == 1
        content250 == 1
        content250_ == 1
        content280 == 1
        content300 == 2
        content350 == 2
    }
    def "ttl no supplier defined test"() {
        given:
        AtomicLong time = new AtomicLong(0L)
        TTLCache._TIME_PROVIDER = { time.get() }
        def cache = new TTLCache("some content", 10L)
        when:
        def access1 = cache.access()
        time.set(5)
        def access2 = cache.access()
        def access3 = cache.access()
        time.set(9)
        def access4 = cache.access()
        def access5 = cache.access()
        then:
        access1 == "some content"
        access2 == "some content"
        access3 == "some content"
        access4 == "some content"
        access5 == "some content"

        when:
        time.set(10)
        cache.access()
        then:
        thrown(IllegalStateException)

        when:
        cache.setContentSupplier({"some new content"})
        def access6 = cache.access()
        time.set(15)
        def access7 = cache.access()
        time.set(25)
        def access8 = cache.access()

        then:
        access6 == "some new content"
        access7 == "some new content"
        access8 == "some new content"

    }
}
