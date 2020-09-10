package ai.services.execution.operator.local

import ai.services.DemoDataClass
import ai.services.execution.Task
import ai.services.execution.local.LocalFieldContext
import com.fasterxml.jackson.databind.ObjectMapper
import de.upb.sede.composition.graphs.nodes.MutableMarshalNode
import de.upb.sede.composition.types.DataValueType
import de.upb.sede.composition.types.serialization.IMarshalling
import de.upb.sede.composition.types.serialization.MutableMarshalling
import de.upb.sede.core.ObjectDataField
import de.upb.sede.core.SemanticDataField
import spock.lang.Shared
import spock.lang.Specification

class DataMarshalOpTest extends Specification {

    @Shared def mapper = new ObjectMapper()

    def testData = new DemoDataClass("TestValue")

    def testDataValObj = new ObjectDataField(testData)

    def dataValueMarshalNode = MutableMarshalNode.create().tap {
        fieldName = "f"
        marshalling = MutableMarshalling.create().tap {
            direction = IMarshalling.Direction.MARSHAL
            semanticName = "sem"
            valueType = DataValueType.builder().typeQualifier(DemoDataClass.simpleName).build()
        }
    }

    def fields = new LocalFieldContext("c").tap {
        setFieldValue("f", testDataValObj)
    }

    def task = new Task(fields, dataValueMarshalNode)

    def op = new DataMarshalOp()

    void assertCorrectSerialisation() {
        def unmarshalledObj = fields.getFieldValue("f") as ObjectDataField
        assert unmarshalledObj.isReal()
        assert unmarshalledObj.type == DemoDataClass.simpleName
        def unmarshalledTestObj = unmarshalledObj.getDataField() as DemoDataClass
        assert unmarshalledTestObj.value == "TestValue"
    }

    def "test marshalling with jackson"() {
        dataValueMarshalNode.putAllRuntimeAuxiliaries(
            [
                "javaMarshalAux" : [
                    "mappedJavaClass"        : DemoDataClass.name,
                    "useJacksonSerialisation": true
                ]
            ]
        )
        (dataValueMarshalNode.marshalling as MutableMarshalling).direction = IMarshalling.Direction.MARSHAL

        when:
        task.set(Task.State.RUNNING)
        def tr = op.runTask(task)
        tr.performTransition(task)

        then:
        task.is(Task.State.RUNNING)
        task.mainTaskPerformed
        def marshalledObj = fields.getFieldValue("f") as SemanticDataField
        marshalledObj.isSemantic()
        marshalledObj.type == "sem"

        when:
        def manualDeserialized = mapper.readValue(marshalledObj.dataField, Map.class)
        then:
        manualDeserialized.json == "TestValue"

        when:
        (dataValueMarshalNode.marshalling as MutableMarshalling).direction = IMarshalling.Direction.UNMARSHAL
        task = new Task(fields, dataValueMarshalNode)
        task.set(Task.State.RUNNING)
        tr = op.runTask(task)
        tr.performTransition(task)

        then:
        task.is(Task.State.RUNNING)
        task.mainTaskPerformed
        assertCorrectSerialisation()
    }

    def "test marshalling with object serialisation"() {
        dataValueMarshalNode.putAllRuntimeAuxiliaries(
            [
                "javaMarshalAux" : [
                    "useObjectSerialisation": true
                ]
            ]
        )
        (dataValueMarshalNode.marshalling as MutableMarshalling).direction = IMarshalling.Direction.MARSHAL

        when:
        task.set(Task.State.RUNNING)
        def tr = op.runTask(task)
        tr.performTransition(task)

        then:
        task.is(Task.State.RUNNING)
        task.mainTaskPerformed
        def marshalledObj = fields.getFieldValue("f") as SemanticDataField
        marshalledObj.isSemantic()
        marshalledObj.type == "sem"

        when:
        ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(marshalledObj.dataField))
        def manualDeserialized = objIn.readObject() as DemoDataClass

        then:
        manualDeserialized.value == "TestValue"

        when:
        (dataValueMarshalNode.marshalling as MutableMarshalling).direction = IMarshalling.Direction.UNMARSHAL
        task = new Task(fields, dataValueMarshalNode)
        task.set(Task.State.RUNNING)
        tr = op.runTask(task)
        tr.performTransition(task)

        then:
        task.is(Task.State.RUNNING)
        task.mainTaskPerformed
        assertCorrectSerialisation()
    }

    def "test marshalling with custom handler"() {
        dataValueMarshalNode.putAllRuntimeAuxiliaries(
            [
                "javaMarshalAux" : [
                    "mappedJavaClass"   : DemoDataClass.name,
                    "useCustomHandler"  : true,
                    "handlerMarshalMethod"     : "serialize",
                    "handlerUnmarshalMethod"   : "deserialize",
                ]
            ]
        )
        (dataValueMarshalNode.marshalling as MutableMarshalling).direction = IMarshalling.Direction.MARSHAL

        when:
        task.set(Task.State.RUNNING)
        def tr = op.runTask(task)
        tr.performTransition(task)

        then:
        task.is(Task.State.RUNNING)
        task.mainTaskPerformed
        def marshalledObj = fields.getFieldValue("f") as SemanticDataField
        marshalledObj.isSemantic()
        marshalledObj.type == "sem"

        when:
        def manualDeserialized = mapper.readValue(marshalledObj.dataField, Map.class)
        then:
        manualDeserialized.customObjectSerialisation == "TestValue"

        when:
        (dataValueMarshalNode.marshalling as MutableMarshalling).direction = IMarshalling.Direction.UNMARSHAL
        task = new Task(fields, dataValueMarshalNode)
        task.set(Task.State.RUNNING)
        tr = op.runTask(task)
        tr.performTransition(task)

        then:
        task.is(Task.State.RUNNING)
        task.mainTaskPerformed
        assertCorrectSerialisation()
    }

}
