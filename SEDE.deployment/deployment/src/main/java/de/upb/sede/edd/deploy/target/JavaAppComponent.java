package de.upb.sede.edd.deploy.target;

import de.upb.sede.edd.process.JavaProcessHandleBuilder;
import de.upb.sede.edd.process.ProcessHandle;
import de.upb.sede.edd.process.ProcessHandleState;
import de.upb.sede.util.MutableOptionalField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.Executor;

public class JavaAppComponent extends TargetComponent{

    private final static Logger logger = LoggerFactory.getLogger(JavaAppComponent.class);

    private JavaProcessHandleBuilder javaProcessBuilder;

    private MutableOptionalField<ProcessHandle> javaProcessHandle =
        MutableOptionalField.empty();


    public JavaAppComponent(String displayName, Executor executor) {
        javaProcessBuilder = new JavaProcessHandleBuilder(executor);
        this.setDisplayName(displayName);
    }

    public synchronized void start() {
        logger.info("{}: start was requested.", getDisplayName());
        javaProcessHandle.set(javaProcessBuilder.build());
        javaProcessHandle.get().start();
        logger.info("{}: java process was started.", getDisplayName());
    }

    public synchronized void stop() {
        logger.info("{}: stop was requested.", getDisplayName());
        if(javaProcessHandle.isAbsent()) {
            logger.warn("{}: cannot stop because no process handle is available.", getDisplayName());
            return;
        }
        ProcessHandle executor = this.javaProcessHandle.get();
        if(executor.getState().isTerminal()) {
            logger.warn("{}: has already terminated.", getDisplayName());
            return;
        }
        if(executor.getState() != ProcessHandleState.STARTED) {
            logger.warn("{}: has not been started yet.", getDisplayName());
            return;
        }
        executor.abort();
        javaProcessHandle.unset();
    }

    public Optional<ProcessHandle> getJavaProcessHandle() {
        return javaProcessHandle.opt();
    }

    public JavaProcessHandleBuilder getJavaProcessBuilder() {
        return javaProcessBuilder;
    }


}
