package ai.services;

import ai.services.exec.IExecutionError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.List;

public class ExecutionErrorException extends Exception {

    private final static ObjectWriter PRETTY_PRINTER = new ObjectMapper().writerWithDefaultPrettyPrinter();

    private final String executionId;

    private final List<IExecutionError> errors;

    public ExecutionErrorException(String executionId, List<IExecutionError> errors) {
        this.executionId = executionId;
        this.errors = errors;
    }

    @Override
    public String toString() {
        return String.format("Execution %s failed. Errors:\n%s", executionId, detailedErrors());
    }

    private String detailedErrors() {
        StringBuilder sb = new StringBuilder();
        errors.forEach(err -> appendDetailedError(err, sb));
        return sb.toString();
    }

    private void appendDetailedError(IExecutionError error, StringBuilder sb) {
        sb.append("Error message: ").append(error.getMessage());
        try {
            sb.append("\nNode: ").append(PRETTY_PRINTER.writeValueAsString(error.getErroredNode()));
        } catch (JsonProcessingException ignored) {
        }
        if(error.getStacktrace() != null)
            sb.append("\nStacktrace: \n").append(error.getStacktrace());
        sb.append("\n----\n");
    }
}
