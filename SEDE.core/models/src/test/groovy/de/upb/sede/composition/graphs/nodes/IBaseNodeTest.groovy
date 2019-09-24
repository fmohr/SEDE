package de.upb.sede.composition.graphs.nodes

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import spock.lang.Specification

class IBaseNodeTest extends Specification {

    static ObjectMapper mapper = new ObjectMapper()
        .configure(SerializationFeature.INDENT_OUTPUT, true)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    def "test Deserialize Delegate"() {
        def deleteNode = MutableDeleteFieldNode.create()
        deleteNode.fieldName = "a"

        def deleteNodeJsnStr = mapper.writeValueAsString(deleteNode)


        println(deleteNodeJsnStr)

        DeleteFieldNode deleteNode2 = mapper
            .readValue(deleteNodeJsnStr, IBaseNode) as DeleteFieldNode

        expect:
        deleteNode2.class == DeleteFieldNode
        deleteNode.fieldName == "a"
    }
}
