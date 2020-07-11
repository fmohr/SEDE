package de.upb.sede.util;

import de.upb.sede.composition.choerography.ChoreographyException;
import de.upb.sede.requests.resolve.beta.IResolvePolicy;
import de.upb.sede.requests.resolve.beta.IResolveRequest;

import java.util.List;

public class ResolvePolicyUtil {

    public static boolean isInitialService(IResolveRequest resolveRequest, boolean isService, String fieldname) {
        if(!isService) {
            return false;
        } else {
            return resolveRequest.getInitialServices().containsKey(fieldname);
        }
    }

    private static boolean isSelected(IResolvePolicy.FieldSelection fieldSelection, List<String> listedFields, String fieldname) {
        if(fieldSelection == IResolvePolicy.FieldSelection.NONE) {
            return false;
        }
        else if(fieldSelection == IResolvePolicy.FieldSelection.ALL) {
            return true;
        } else {
            // LISTED
            return listedFields.contains(fieldname);
        }
    }
    public static boolean toBeReturned(IResolveRequest resolveRequest, String fieldname, boolean isService) {
        IResolvePolicy resolvePolicy = resolveRequest.getResolvePolicy();
        boolean isInitialService = isInitialService(resolveRequest, isService, fieldname);
        if(isService) {
            return toBeStored(resolvePolicy, fieldname);
        }
        boolean toBeReturned = isSelected(resolvePolicy.getReturnPolicy(), resolvePolicy.getReturnFieldnames(), fieldname);
        if(isService && !isInitialService) {
            // TODO remove this block:
            // This case cannot happen anymore because service are returned iff they are marked persistent.
            boolean toBeStored = toBeStored(resolvePolicy, fieldname);
            if (toBeReturned && !toBeStored) {
                throw new ChoreographyException("Return policy is flawed:\n" + resolvePolicy.getReturnPolicy()
                    + "\nThe non-initial  field " + fieldname + " is to be returned while it isn't to be stored." +
                    "\nThe executor cannot return a pointer to a service instance that wasn't stored.");
            } else if(!toBeReturned && toBeStored) {
                throw new ChoreographyException("Return policy is flawed:\n" + resolvePolicy.getReturnPolicy()
                    + "\nThe non-initial field " + fieldname + " is to be stored but it isn't being returned." +
                    "\nThe client won't have a pointer to the service, " +
                    "so it cannot use it later or free its allocation.");
            }
        }
        return toBeReturned;
    }

    public static boolean toBeStored(IResolvePolicy resolvePolicy, String fieldname) {
        boolean toBeStored = isSelected(resolvePolicy.getServicePolicy(), resolvePolicy.getPersistentServices(), fieldname);
        return toBeStored;
    }

}
