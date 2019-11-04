package de.upb.sede.util

import spock.lang.Specification
import spock.lang.Unroll

import java.util.stream.Collectors
import java.util.stream.IntStream
import java.util.stream.Stream

class StreamsTest extends Specification {

    List<Integer> listOf(int start, int exclusiveFinish) {
        return IntStream.range(start, exclusiveFinish).mapToObj {it} .collect(Collectors.toList())
    }

    def "flatten and collect"() {
        when:
        def outputList = Streams.flatten(inputStreams).collect(Collectors.toList())

        then:
        outputList == expectedOutput

        where:
        //Stream<Stream<Integer>>
        inputStreams << [
            [[]].stream().map {it.stream()},
            [listOf(0, 10).stream()].stream(),
            [listOf(0, 10), listOf(10, 20)].stream().map {it.stream()},
            [
                listOf(0, 10),
                listOf(10, 20),
                listOf(20, 100)
            ].stream().map {it.stream()}
        ]
        // List<Integer>
        expectedOutput << [
            [],
            listOf(0, 10),
            listOf(0, 20),
            listOf(0, 100)
        ]
    }

    @Unroll
    def "flatten, apply some operations and collect"() {
        when:
        // [[1, 2, .. , 9], [10, 11, .. , 19],  .. , [990, 991, .. , 999]]
        List<List<Integer>> inputLists = new ArrayList<>()
        for (int i = 0; i < 100; i++) {
            inputLists.add(listOf(i*10, (i+1)*10))
        }
        Stream<Stream<Integer>> inputStreams = inputLists.stream().map {it.stream()}
        def outputList
//        outputList = Streams.flatten(inputStreams).collect(Collectors.toList())

        def flatten = streamOps(Streams.flatten(inputStreams))
         outputList = flatten.collect(Collectors.toList())

        then:
        outputList == expectedOutput

        where:
        // Closure<Stream>
        streamOps << [
            {
                it
            },
            {
                Stream s -> s.filter{false}
            },
            { Stream s ->
                s.limit(0)
            },
            { Stream s ->
                s.limit(10)
            },
            { Stream s ->
                s.limit(30)
            },
            { Stream s ->
                s.filter {it%2 == 0}
            },
            { Stream s ->
                s.filter {it%2 == 0}.limit(10)
            },
            { Stream s ->
                s.map {it * -1}
            },
            { Stream s ->
                s.skip(65).limit(10).map {it -> Character.valueOf((char)it)}
            },
        ]
        // List<Integer>
        expectedOutput << [
            listOf(0, 1000),
            [],
            [],
            listOf(0, 10),
            listOf(0, 30),
            listOf(0, 1000).stream().filter(){it%2 == 0}.collect {it},
            listOf(0, 20).stream().filter(){it%2 == 0}.collect {it},
            listOf(-999, 1).stream().sorted(Collections.reverseOrder()).collect {it},
            ['A','B','C','D','E','F','G','H','I', 'J']
        ]
    }
}
