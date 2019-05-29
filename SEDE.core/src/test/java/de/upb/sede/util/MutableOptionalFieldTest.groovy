package de.upb.sede.util

import spock.lang.Specification

class MutableOptionalFieldTest extends Specification {

    def "test set unset and setNullable"() {

        when:
        def field = MutableOptionalField.of("text")
        then:
        field.isPresent()
        field.get() == "text"

        when:
        field.set("new text!")
        then:
        field.isPresent()
        field.get() == "new text!"

        when:
        field.unset()
        then:
        field.isAbsent()

        when:
        field.setNullable("Hello")
        then:
        field.isPresent()

        when:
        field.setNullable(null)
        then:
        field.isAbsent()

        when:
        MutableOptionalField.of("text")set(null)
        then:
        thrown(NullPointerException)

    }

}
