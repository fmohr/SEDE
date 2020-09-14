package de.upb.sede.beta;

import de.upb.sede.IQualifiable;

/**
 * This intermediate interface is inherited by all requests that are qualifiable, ie. requests that have an id.
 *
 * In case of requests the qualifier is not unique to a single request, but to a group of requests that point to the same context.
 *
 * Usually the qualifier is identical to the execution id.
 */
public interface IQualifiableRequest extends IQualifiable {

}
