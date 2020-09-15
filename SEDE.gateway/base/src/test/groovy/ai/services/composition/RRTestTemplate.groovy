package ai.services.composition

import spock.lang.Ignore

class RRTestTemplate {

    @Ignore
    def "template"() {
        def description =
            """""".stripMargin()

        when:
        def rr = RRGen.fromClosure {
            composition = """
            """
            resolvePolicy = RRGen.defaultResolvePolicy()
        }

        def testRunner = new ResolutionTestBaseRunner("", "")
        testRunner.setClientExecutor("client")

        testRunner.testPlainText = description
        testRunner.addExecutor("executor1", "c0.S0")
        testRunner.addExecutor("executor2", "c0.S1")

        then:
        testRunner.start(rr)
        testRunner.assertStaticAnalysisExceptionMatches(null)
        testRunner.assertSimulationExceptionMatches(null)
        testRunner.writeOutputs()

        def cc = testRunner.getCc()
        def ch = testRunner.getChoreography()
    }

}
