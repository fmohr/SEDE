package de.upb.sede.composition.choerography;

import de.upb.sede.exec.IExecutorHandle;

import java.util.List;

public class ChoreographyException extends RuntimeException {

    public ChoreographyException() {
    }

    public ChoreographyException(String message) {
        super(message);
    }

    public ChoreographyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChoreographyException(Throwable cause) {
        super(cause);
    }

    public ChoreographyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static ChoreographyException initialServiceHostNotRegistered(String fieldName, String serviceID, String serviceClasspath, String executorId){
        return new ChoreographyException(
            String.format("Field '%s' is an initial service with id '%s' of type '%s' " +
                    "but its host '%s' is no registered to the executor.",
                fieldName, serviceID, serviceClasspath, executorId));
    }

    public static ChoreographyException noReachableExecutorAmongCandidates() {
        return new ChoreographyException(
            "No executor among collected candidates is reachable. " +
            "To execute a choreography at least one executor must be reachable."
        );
    }

    public static ChoreographyException initialServiceMultipleCandidates(String contextFieldName, List<IExecutorHandle> candidates) {
        return new ChoreographyException(
            String.format("The initial service '%s' has multiple candidates: %s", contextFieldName, candidates.toString())
        );
    }

    public static ChoreographyException missingFieldException(String fieldname, String instruction, String executorID) {
        return new ChoreographyException(String.format("The field '%s' " +
            "used for instruction '%s' running on executor '%s' is missing." +
            " The type checker should have already caught this error so this error indicates a bug in the type checker.",
            fieldname, instruction, executorID));
    }
}
