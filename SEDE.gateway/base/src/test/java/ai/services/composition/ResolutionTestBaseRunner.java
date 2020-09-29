package ai.services.composition;

import ai.services.beta.ExecutorRegistration;
import ai.services.exec.ExecutorCapabilities;
import ai.services.exec.ExecutorContactInfo;
import ai.services.exec.ExecutorHandle;
import ai.services.requests.resolve.beta.Choreography;
import ai.services.requests.resolve.beta.ResolveRequest;
import ai.services.util.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import ai.services.ISDLAssembly;
import ai.services.SDLReader;
import ai.services.beta.IExecutorRegistration;
import ai.services.composition.choerography.emulation.executors.ExecutionGraph;
import ai.services.composition.graphs.nodes.ICompositionGraph;
import ai.services.exec.IExecutorHandle;
import ai.services.gateway.ExecutorArbiter;
import ai.services.gateway.StandaloneExecutor;
import ai.services.gateway.StdGatewayImpl;
import ai.services.interfaces.IGateway;
import ai.services.requests.resolve.beta.IChoreography;
import ai.services.requests.resolve.beta.IResolveRequest;
import ai.services.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class ResolutionTestBaseRunner {

    private static final Logger logger = LoggerFactory.getLogger(ResolutionTestBaseRunner.class);

    private static final ObjectWriter WRITER = new ObjectMapper().writerWithDefaultPrettyPrinter();

    private static final ISDLAssembly TEST_BASE_SERVICE_DESC;

    static {
        TEST_BASE_SERVICE_DESC = loadServiceDesc();
    }

    private String testPlainText;

    private String testGroup;
    private String testName;

    private ExecutorArbiter executorArbiter;

    private IGateway gateway;

    private IExecutorRegistration clientRegistration;

    // exceptions that occurred
    private Exception staticAnalysisException;
    private Exception simulationException;

    // results
    private long timeCC = 0, timeChoreography = 0;
    private ICompositionCompilation cc;
    private IChoreography choreography;

    public ResolutionTestBaseRunner(String testGroup, String testName) {
        this.testGroup = testGroup;
        this.testName = testName;
        executorArbiter = new ExecutorArbiter();
        gateway = new StdGatewayImpl(getServiceDesc(), executorArbiter);
    }

    private static ISDLAssembly loadServiceDesc() {
        SDLReader reader = new SDLReader();
        try {
            reader.readResource("descs/testbase.servicedesc.groovy");
            reader.readResource("descs/primitivebase.servicedesc.groovy");
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read test base.", e);
        }
        return reader.getSDLAssembly();
    }

    public static ISDLAssembly getServiceDesc() {
        return TEST_BASE_SERVICE_DESC;
    }

    public File getOutputFolder() {
        return FileUtil.prepareTestOutputDir(testGroup, false);
    }

    public File getOutputFile(String fileName) {
        return new File(getOutputFolder(), testName + "__"  + fileName);
    }

    public ExecutorArbiter getExecutorArbiter() {
        return executorArbiter;
    }

    public IGateway getGateway() {
        return gateway;
    }

    public void addExecutor(IExecutorHandle handle) {
        executorArbiter.addSupplier(new StandaloneExecutor(handle));
    }

    public void addExecutor(String executorId, String... services) {
        this.addExecutor(ExecutorHandle.builder()
            .contactInfo(ExecutorContactInfo.builder()
                .qualifier(executorId)
                .uRL("host::"+executorId)
                .build())
            .capabilities(ExecutorCapabilities.builder()
                .addServices(services)
                .build())
            .build());
    }

    public void setClientExecutor(IExecutorRegistration registration) {
        this.clientRegistration = registration;
    }

    public void setClientExecutor(String executorId, String... services) {
        this.clientRegistration = ExecutorRegistration.builder()
            .executorHandle(ExecutorHandle.builder()
                .contactInfo(ExecutorContactInfo.builder()
                    .qualifier(executorId)
                    .uRL("host::" + executorId)
                    .build())
                .capabilities(ExecutorCapabilities.builder()
                    .addServices(services)
                    .build())
                .build())
            .build();
    }

    public void setTestPlainText(String plainText) {
        this.testPlainText = plainText;
    }

    public void start(IResolveRequest rr) {
        long startTime = System.currentTimeMillis();
        ICCRequest iccRequest = CCRequest.builder()
            .composition(rr.getComposition())
            .addAllInitialContext(rr.getInitialContext())
            .build();
        try {
            this.cc = getGateway().compileComposition(iccRequest);
            Objects.requireNonNull(cc, "Compile composition returned null!");
        } catch(Exception ex) {
            staticAnalysisException = ex;
            return;
        } finally {
            this.timeCC = System.currentTimeMillis() - startTime;
            logger.info("Time to CC of {}({}) = {} ms", testGroup, testName, timeCC);
        }

        startTime = System.currentTimeMillis();
        ResolveRequest.Builder builder = ResolveRequest
            .builder()
            .from(rr);
        if(clientRegistration != null) {
            builder.clientExecutorRegistration(clientRegistration);
        }
        IResolveRequest rrWithCC = builder
            .cC(this.cc)
            .build();
        try {
            choreography = getGateway().resolve(rrWithCC);
        } catch(Exception ex) {
            simulationException = ex;
            return;
        } finally {
            this.timeChoreography = System.currentTimeMillis() - startTime;
            logger.info("Time to choreograph of {}({}) = {} ms", testGroup, testName, timeChoreography);
        }

    }

    public void assertStaticAnalysisExceptionMatches(Predicate<Exception> match) {
        this.assertExceptionMatch(match, this.staticAnalysisException, "static composition analysis");
    }

    public void assertSimulationExceptionMatches(Predicate<Exception> match) {
        this.assertExceptionMatch(match, this.simulationException, "choreography simulation");
    }

    private void assertExceptionMatch(Predicate<Exception> match,
        Exception thrownException,
        String taskDescription) {
        if(match == null) {
            if (thrownException != null) {
                throw new AssertionError(String.format("Unexpected error while preforming %s.", taskDescription), thrownException);
            }
        } else {
            if(thrownException == null) {
                throw new AssertionError(String.format("Expected an error to occur while preforming %s, but none were thrown.",
                    taskDescription));
            }
            else if (!match.test(thrownException)) {
                throw new AssertionError(String.format("The expected error while preforming %s differs from the one actually thrown.",
                    taskDescription), thrownException);
            }
        }
    }

    public void writeOutputs() {
        if(testPlainText != null) {
            StringBuilder sb = new StringBuilder(testPlainText);
            sb.append("\n\nTime CC: ").append(timeCC).append(" ms");
            sb.append("\n\nTime choreography: ").append(timeChoreography).append(" ms");
            File outputFile = getOutputFile("plaintext.txt");
            FileUtil.writeStringToFile(outputFile.getAbsolutePath(), sb.toString());
        }
        // write error messages:
        if(staticAnalysisException != null) {
            String errorMessage = StringUtil.ErrToString(staticAnalysisException);
            File outF = getOutputFile("error_static_analysis.txt");
            FileUtil.writeStringToFile(outF.getAbsolutePath(), errorMessage);
        }
        if(simulationException != null) {
            String errorMessage = StringUtil.ErrToString(simulationException);
            File outF = getOutputFile("error_choreography_analysis.txt");
            FileUtil.writeStringToFile(outF.getAbsolutePath(), errorMessage);
        }
        if(cc != null) {
            File outF = getOutputFile("cc.json");
            try {
                WRITER.writeValue(outF, this.cc);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        if(choreography != null) {
            String dotSVG = choreography.getDotSVG();
            if(dotSVG != null) {
                File outF = getOutputFile("graph.svg");
                FileUtil.writeStringToFile(outF.getAbsolutePath(), dotSVG);
                choreography = Choreography.builder().from(choreography).dotSVG("").build(); // delete the svg
            }
            File outF = getOutputFile("choreography.json");
            try {
                WRITER.writeValue(outF, this.choreography);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public String getTestPlainText() {
        return testPlainText;
    }

    public String getTestGroup() {
        return testGroup;
    }

    public String getTestName() {
        return testName;
    }

    public IExecutorRegistration getClientRegistration() {
        return clientRegistration;
    }

    public Exception getStaticAnalysisException() {
        return staticAnalysisException;
    }

    public Exception getSimulationException() {
        return simulationException;
    }

    public ICompositionCompilation getCc() {
        return cc;
    }

    public IChoreography getChoreography() {
        return choreography;
    }

    public ICompositionGraph getGraph(String executorId) {
        if(choreography == null) {
            throw new AssertionError("choreography not present.");
        }
        Optional<ICompositionGraph> matchedGraph = choreography.getCompositionGraph().stream()
            .filter(it -> it.getExecutorHandle().getQualifier().equals(executorId))
            .findFirst();
        if(matchedGraph.isPresent()) {
            return matchedGraph.get();
        } else {
            throw new AssertionError("Executor " + executorId + " doesn't have a composition graph.");
        }
    }

    public ExecutionGraph getExecGraph(String executorId) {
        return new ExecutionGraph(getGraph(executorId));
    }
}
