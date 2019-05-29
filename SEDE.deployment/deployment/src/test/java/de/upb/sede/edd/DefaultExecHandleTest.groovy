package de.upb.sede.edd

import de.upb.sede.util.Streams
import spock.lang.Specification

class DefaultExecHandleTest extends Specification {

    def "test script exists" () {
        expect:
        new File("testrsc/script").exists()
    }

    def "test start"() {
        given:
        def builder = new DefaultProcessHandleBuilder()
        builder.setWorkingDir(new File("./testrsc"));
        builder.setDaemon(false)
        builder.redirectErrorStream()
        builder.setExecutable("./script")
        def handle = builder.build().start()
        def finish = handle.waitForFinish()


        expect:
        finish.exitValue == 0
        finish.output == "Hello"
    }

    def "test normalProcessBuilder" () {
        given:
        ProcessBuilder pb = new ProcessBuilder("testrsc/script");
        pb.redirectErrorStream(true);
        Process process = pb.start();
        def lines = Streams.InReadLines(process.inputStream)
        process.waitFor();
        print(lines)
    }
}
