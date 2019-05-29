package de.upb.sede.edd.deploy

import spock.lang.Specification

class AcrPathTest extends Specification {


    def "test parse"() {
        when:
        def parsed = AcrPath.parse(acrPath)

        then:
        parsed.acr == acr
        parsed.path == path

        where:
        acrPath     | acr       | path
        "a.file"    | "0"       | "a.file"
        "path/to/file"    | "0"       | "path/to/file"
        "path/to/dir/"    | "0"       | "path/to/dir"
        "file_with_under__score"    | "0"       | "file_with_under__score"
        ".a..b..."    | "0"       | ".a..b..."
        "-a-b--"    | "0"       | "-a-b--"
        "./a.file"  | "0"       | "./a.file"
        "[0]/a"     | "0"       | "a"
        "[1]/a/b/c.txt"     | "1"       | "a/b/c.txt"
        "[2]/a/b/dir/"     | "2"       | "a/b/dir"
        "[234]/a"   | "234"       | "a"
        "[cool-acronym]/a"   | "cool-acronym"       | "a"
        "[acronym-with.special_characters]/a/b"   | "acronym-with.special_characters"       | "a/b"
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
    }
}
