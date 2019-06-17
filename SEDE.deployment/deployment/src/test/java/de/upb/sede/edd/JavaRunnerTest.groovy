package de.upb.sede.edd

import de.upb.sede.edd.process.ClassPath
import de.upb.sede.edd.process.JavaProcessHandleBuilder
import de.upb.sede.edd.process.ProcessHandleState
import de.upb.sede.util.ExtendedByteArrayOutputStream
import de.upb.sede.util.Streams
import spock.lang.Specification

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class JavaRunnerTest extends Specification {
    ExecutorService executor
    void setup() {
        executor = Executors.newCachedThreadPool()
    }

    void cleanup() {
        executor.shutdownNow()
    }

    def "test java process blocking app"() {
        when:
        JavaProcessHandleBuilder builder = new JavaProcessHandleBuilder(executor);
        builder.getClasspath().files(new File("testrsc/test.jar"))
        ClassPath cp = builder.getClasspath()
        builder.setMain("de.upb.sede.edd.deploy.test.TestBlockingApp")
        def out = new ExtendedByteArrayOutputStream()
        builder.setStandardOutput(out)
        builder.redirectErrorStream()
        def processHandle = builder.build()
        processHandle.start()

        then:
        processHandle.state == ProcessHandleState.STARTED

        when:
        Thread.sleep(1000)
        processHandle.abort()
        then:
        processHandle.state == ProcessHandleState.ABORTED

    }

    def "test java process error"() {
        when:
        JavaProcessHandleBuilder builder = new JavaProcessHandleBuilder(executor);
        builder.getClasspath().files(new File("testrsc/test.jar"))
        ClassPath cp = builder.getClasspath()
        builder.setMain("de.upb.sede.edd.deploy.test.TestExceptionApp")
        def out = new ExtendedByteArrayOutputStream()
        builder.setStandardOutput(out)
        builder.redirectErrorStream()
        def processHandle = builder.build()
        processHandle.start()
        def result = processHandle.waitForFinish()
        Thread.sleep(1000)
        then:
        processHandle.state == ProcessHandleState.FAILED
        result.exitValue == 1

    }

    def "test java process handler"() {
        when:
        JavaProcessHandleBuilder builder = new JavaProcessHandleBuilder(executor);
        builder.getClasspath().files(new File("testrsc/test.jar"))
        ClassPath cp = builder.getClasspath()
        builder.setMain("de.upb.sede.edd.deploy.test.TestJavaBasicApp")

        then:
        cp.files.size() == 1
        new File(cp.files[0]).exists()

        when:
        def out = new ExtendedByteArrayOutputStream()
        builder.setStandardOutput(out)
        builder.redirectErrorStream()
        def processHandle = builder.build()
        processHandle.start()
        def result = processHandle.waitForFinish()
        def processOut = Streams.InReadString(out.toInputStream())
        result.assertNormalExitValue()

        then:
        processHandle.state == ProcessHandleState.FAILED
        result.exitValue == 11
        processOut.isEmpty()
        thrown(RuntimeException)

        when:
        out = new ExtendedByteArrayOutputStream()
        builder.setStandardOutput(out)
        builder.setArgs(["hello"])
        processHandle = builder.build()
        processHandle.start()
        result = processHandle.waitForFinish()
        processOut = Streams.InReadString(out.toInputStream())
        result.assertNormalExitValue()

        then:
        processHandle.state == ProcessHandleState.FAILED
        result.exitValue == 1
        processOut.contains(NumberFormatException.simpleName)
        thrown(RuntimeException)

        when:
        out = new ExtendedByteArrayOutputStream()
        builder.setStandardOutput(out)
        builder.setArgs(["12"])
        processHandle = builder.build()
        processHandle.start()
        result = processHandle.waitForFinish()
        processOut = Streams.InReadString(out.toInputStream())
        result.assertNormalExitValue()

        then:
        processHandle.state == ProcessHandleState.SUCCEEDED
        result.exitValue == 0
        processOut == "12 times two is: 24\n"
    }
}
