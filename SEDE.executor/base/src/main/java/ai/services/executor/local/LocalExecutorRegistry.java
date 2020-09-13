package ai.services.executor.local;

import ai.services.executor.Executor;
import de.upb.sede.exec.IExecutorContactInfo;

import java.util.concurrent.ConcurrentHashMap;

public class LocalExecutorRegistry extends ConcurrentHashMap<String, Executor> {

    public static final LocalExecutorRegistry INSTANCE = new LocalExecutorRegistry();

    public Executor get(IExecutorContactInfo contactInfo) {
        return super.get(contactInfo.getQualifier());
    }

}
