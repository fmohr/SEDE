package de.upb.sede.interfaces;

import de.upb.sede.requests.resolve.GatewayResolution;
import de.upb.sede.requests.resolve.beta.IChoreography;
import de.upb.sede.requests.resolve.beta.IResolveRequest;

public interface IChoreographyComposer {

    /**
     * This is the old way of executing compositions.
     * It is now being replaced by compile composition.
     */
    IChoreography resolve(IResolveRequest resolveRequest);

}
