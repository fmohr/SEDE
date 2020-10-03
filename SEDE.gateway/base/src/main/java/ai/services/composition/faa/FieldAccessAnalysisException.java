package ai.services.composition.faa;

import ai.services.composition.IIndexedInstruction;

public class FieldAccessAnalysisException extends RuntimeException {

    FieldAccessAnalysisException() {
    }

    FieldAccessAnalysisException(String message) {
        super(message);
    }

    FieldAccessAnalysisException(String message, Throwable cause) {
        super(message, cause);
    }

    FieldAccessAnalysisException(Throwable cause) {
        super(cause);
    }

    FieldAccessAnalysisException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    static FieldAccessAnalysisException stepError(IIndexedInstruction indexedInstruction, FieldAccessAnalysisException cause) {
        String message = "Error while analysing the access on fields for instruction: %S (index: %d)";
        throw new FieldAccessAnalysisException(message, cause);
    }

    static FieldAccessAnalysisException noMethodCognition() {
        String message = "No method cognition was supplied.";
        throw new FieldAccessAnalysisException(message);
    }


}
