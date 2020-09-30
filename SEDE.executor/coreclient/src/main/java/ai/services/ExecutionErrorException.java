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
        errors.forEach(err -> appendError(err, sb));
        return sb.toString();
    }

    private void appendError(IExecutionError error, StringBuilder sb) {
        sb.append("Error message: ").append(error.getMessage());
        if(error.getMessage().equals("Dependency failed")) {
            appendSimpleLog(error, sb);
        } else {
            appendDetailedLog(error, sb);
        }
        sb.append("\n----\n");
    }

    private void appendSimpleLog(IExecutionError error, StringBuilder sb) {
        if(error.getErroredNode() != null) {
            sb.append("\nNode kind: ").append(error.getErroredNode().getNodeKind());
        }
    }

    private void appendDetailedLog(IExecutionError error, StringBuilder sb) {
        try {
            sb.append("\nNode: ").append(PRETTY_PRINTER.writeValueAsString(error.getErroredNode()));
        } catch (JsonProcessingException ignored) {
        }
        if(error.getStacktrace() != null)
            sb.append("\nStacktrace: \n").append(error.getStacktrace());
    }
}
