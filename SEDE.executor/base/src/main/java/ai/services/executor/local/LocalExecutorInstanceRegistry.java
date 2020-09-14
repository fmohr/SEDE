package ai.services.executor.local;

import ai.services.executor.Executor;
import de.upb.sede.exec.IExecutorContactInfo;

import java.util.concurrent.ConcurrentHashMap;

public class LocalExecutorInstanceRegistry extends ConcurrentHashMap<String, Executor> {

    public static final LocalExecutorInstanceRegistry INSTANCE = new LocalExecutorInstanceRegistry();

    public Executor get(IExecutorContactInfo contactInfo) {
        return super.get(contactInfo.getQualifier());
    }

}
