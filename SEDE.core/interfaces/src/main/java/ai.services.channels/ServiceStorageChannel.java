package ai.services.channels;

import ai.services.core.ServiceInstanceHandle;

public interface ServiceStorageChannel {

    DownloadLink loadService(ServiceInstanceHandle serviceInstanceHandle);

    UploadLink storeService(ServiceInstanceHandle serviceInstanceHandle);

}
