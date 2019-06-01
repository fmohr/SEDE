package de.upb.sede.edd.deploy

import spock.lang.Specification

class AcrPathTest extends Specification {

    def "test parse"() {
        when:
        def parsed = AcrPath.parse(acrPath)

        then:
        parsed.acr.orElse("empty") == acr
        parsed.path == path

        where:
        acrPath             | acr               | path
        "a.file1"           | "empty"           | "a.file1"
        "path/to/file"      | "empty"           | "path/to/file"
        "path/to/dir/"      | "empty"           | "path/to/dir"
        "file_with_under__score"| "empty"       | "file_with_under__score"
        ".a..b..."          | "empty"           | ".a..b..."
        "-a-b--"            | "empty"           | "-a-b--"
        "./a.file"          | "empty"           | "./a.file"
        "[0]/a"             | "0"               | "a"
        "[1]/a/b/c.txt"     | "1"               | "a/b/c.txt"
        "[2]/a/b/dir/"      | "2"               | "a/b/dir"
        "[234]/a"           | "234"             | "a"
        "[cool-acronym]/a"  | "cool-acronym"    | "a"
        "[acronym-with.special_characters]/a/b" | "acronym-with.special_characters"| "a/b"
    }

    def "test parse faulty"() {
        when:
        AcrPath.parse(acrPath)

        then:
        thrown(IllegalArgumentException)

        where:
        _ | acrPath
        _ | "/absolute/paths"
        _ | "illegal£charachter"
        _ | "illegal+charachter"
        _ | "illegal\\seperator"
        _ | "[acr]without/seperator"
        _ | "[illegal@character]/file"
        _ | "[]/file"
        _ | "[0]/"
        _ | "[as:]/file"
        _ | "[as:]/file/as"
    }

    def "test toString()"() {
        when:
        def parsed = AcrPath.parse(acrPath)
        then:
        parsed == AcrPath.parse(parsed.toString())

        where:
        _ | acrPath
        _ | "paths"
        _ | "paths213/as.g-file__.data"
        _ | "[acr]/path"
        _ | "[reference:acr]/path"
    }

    def "test ref"() {
        when:
        def parsed = AcrPath.parse(acrPath)

        then:
        parsed.acr.orElse("empty") == acr
        parsed.path == path

        where:
        acrPath             | ref           | acr               | path
        "a.file1"           | "emptyRef"    | "empty"           | "a.file1"
        "path/to/file"      | "emptyRef"    | "empty"           | "path/to/file"
        "path/to/dir/"      | "emptyRef"    | "empty"           | "path/to/dir"
        "file_with_under__score"| "emptyRef"| "empty"           | "file_with_under__score"
        ".a..b..."          | "emptyRef"    | "empty"           | ".a..b..."
        "-a-b--"            | "emptyRef"    | "empty"           | "-a-b--"
        "./a.file"          | "emptyRef"    | "empty"           | "./a.file"
        "[0]/a"             | "emptyRef"    | "0"               | "a"
        "[1]/a/b/c.txt"     | "emptyRef"    | "1"               | "a/b/c.txt"
        "[ref:2]/a/b/dir/"  | "ref"         | "2"               | "a/b/dir"
        "[ref-a_.:234]/a"   | "ref-a_."     | "234"             | "a"
        "[cool-acronym]/a"  | "emptyRef"    | "cool-acronym"    | "a"
        "[c_a-d.e:m]/a"     | "c_a-d.e"     | "m"               | "a"
    }
}
