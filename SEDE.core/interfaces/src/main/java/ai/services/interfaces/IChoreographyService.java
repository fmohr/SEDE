package ai.services.interfaces;

import ai.services.requests.resolve.beta.IChoreography;
import ai.services.requests.resolve.beta.IResolveRequest;

public interface IChoreographyService {

    /**
     * This is the old way of executing compositions.
     * It is now being replaced by compile composition.
     */
    IChoreography resolve(IResolveRequest resolveRequest);

}
