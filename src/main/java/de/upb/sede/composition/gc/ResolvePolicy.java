package de.upb.sede.composition.gc;

/**
 * TODO move this class to a better location later on.
 * @author aminfaez
 *
 */
public class ResolvePolicy {
	public boolean isToReturn(ResolveInfo resolveInfo, String fieldName) {
		return true; // TODO
	}
	
	public boolean isPersistentService(String fieldName) {
		return true;
	}
}
