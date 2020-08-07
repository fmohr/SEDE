package ai.services.executor;

import de.upb.sede.exec.IExecutorContactInfo;

public interface CommService {

    ExComm executor(IExecutorContactInfo contactInfo);

}
