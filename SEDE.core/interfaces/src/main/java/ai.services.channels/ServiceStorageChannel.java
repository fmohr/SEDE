package ai.services.channels;

import de.upb.sede.core.ServiceInstanceHandle;

public interface ServiceStorageChannel {

    DownloadLink loadService(ServiceInstanceHandle serviceInstanceHandle);

    UploadLink storeService(ServiceInstanceHandle serviceInstanceHandle);

}
