package de.upb.sede.util;

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
        boolean toBeReturned = isSelected(resolvePolicy.getReturnPolicy(), resolvePolicy.getReturnFieldnames(), fieldname);
        if(toBeReturned && isService && !isInitialService) {
            if(!toBeStored(resolvePolicy, fieldname) ) {
                throw new IllegalStateException("Return policy is flawed:\n" +resolvePolicy.getReturnPolicy()
                    + "\n The field " + fieldname + " is to be returned but it isnt a initial service and it isn't to be store.");
            }
        }
        return toBeReturned;
    }

    public static boolean toBeStored(IResolvePolicy resolvePolicy, String fieldname) {
        boolean toBeStored = isSelected(resolvePolicy.getServicePolicy(), resolvePolicy.getPersistentServices(), fieldname);
        return toBeStored;
    }

}
