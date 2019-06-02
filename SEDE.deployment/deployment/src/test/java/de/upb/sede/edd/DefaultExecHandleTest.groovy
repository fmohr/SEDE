package de.upb.sede.edd

import de.upb.sede.edd.process.DefaultProcessHandleBuilder
import de.upb.sede.util.Streams
import spock.lang.Specification

import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.ThreadPoolExecutor

class DefaultExecHandleTest extends Specification {

    def "test script exists" () {
        expect:
        new File("testrsc/script").exists()
    }

    def "test start"() {
        given:
        def executor = Executors.newCachedThreadPool()
        def builder = new DefaultProcessHandleBuilder(executor)
        def output = new ByteArrayOutputStream()
        builder.setStandardOutput(output)
        builder.setWorkingDir(new File("./testrsc"));
        builder.setDaemon(false)
        builder.redirectErrorStream()
        File script = OperatingSystem.current().findAnyInDir(new File("./testrsc"), "script").get()
        builder.setExecutable(script.absolutePath)
        def handle = builder.build().start()
        def finish = handle.waitForFinish()


        expect:
        finish.exitValue == 0
        output.toString() == "Hello\n"
        executor.shutdown()
        executor.terminated
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
