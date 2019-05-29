package de.upb.sede.util

import spock.lang.Specification

class OptionalFieldTest extends Specification {
    def "test WobblyField to Optional"() {

        given: "2 wobbly fields of strings"
        def string1 = OptionalField.of("Some Data")
        def string2 = OptionalField.ofNullable("Some Other Data")

        and: "2 empty wobbly fields"
        def empty1 = OptionalField.empty()
        def empty2 = OptionalField.ofNullable(null)

        when: "transforming wobbly fields to optionals"
        def string1Opt = string1.opt()
        def string2Opt = string2.opt()
        def empty1Opt = empty1.opt()
        def empty2Opt = empty2.opt()

        then: "the behaviour matched"
        string1Opt.isPresent()
        !empty1Opt.isPresent()
        string2Opt.isPresent()
        !empty2Opt.isPresent()
    }

    def "test WobblyField from Optional"() {

        given:
        def string1Opt = Optional.of("Some Data")
        def string2Opt = Optional.ofNullable("Some Data")
        def empty1Opt = Optional.empty()
        def empty2Opt = Optional.ofNullable(null)

        when:
        def string1 = OptionalField.of(string1Opt)
        def string2 = OptionalField.of(string1Opt)
        def empty1 = OptionalField.of(empty1Opt)
        def empty2 = OptionalField.of(empty2Opt)

        then:
        string1.isPresent()
        string2.isPresent()
        empty1.isAbsent()
        empty2.isAbsent()
    }

    def "test serialization of wobbly fields"() {

        given:
        def fieldList = [OptionalField.of("Hallo"), OptionalField.empty(), OptionalField.of(123) ]

        when:
        def byteStream = new ByteArrayOutputStream()
        new ObjectOutputStream(byteStream).writeObject(fieldList)
        def byteSerialization = byteStream.toByteArray()
        List<OptionalField<?>> deserializedFieldList =
                new ObjectInputStream(new ByteArrayInputStream(byteSerialization)).readObject()

        then:
        deserializedFieldList.size() == 3
        deserializedFieldList.each {it != null}
        deserializedFieldList[0].isPresent()
        deserializedFieldList[0].get() == "Hallo"
        deserializedFieldList[1].isAbsent()
        deserializedFieldList[2].isPresent()
        deserializedFieldList[2].get() == 123
    }

    def "test serialization of a wobbly field with an un-serializable type"() {
        given:
        def unserializableField = OptionalField.of(Mock(Iterable))

        when:
        new ObjectOutputStream(new ByteArrayOutputStream()).writeObject(unserializableField)

        then:
        thrown(NotSerializableException)
    }
}
