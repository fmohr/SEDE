package ai.services.util;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Wraps a checked exception.
 */
public final class Uncheck extends RuntimeException {
    private Uncheck(Throwable cause) {
        super(cause);
    }

    private Uncheck(String message, Throwable cause) {
        super(message, cause);
    }

    public static RuntimeException throwAsUncheckedException(Throwable t) {
        return throwAsUncheckedException(t, false);
    }

    public static RuntimeException throwAsUncheckedException(Throwable t, boolean preserveMessage) {
        if (t instanceof InterruptedException) {
            Thread.currentThread().interrupt();
        }
        if (t instanceof RuntimeException) {
            throw (RuntimeException) t;
        }
        if (t instanceof Error) {
            throw (Error) t;
        }
        if (t instanceof IOException) {
            if (preserveMessage) {
                throw new UncheckedIOException(t.getMessage(), (IOException) t);
            } else {
                throw new UncheckedIOException((IOException) t);
            }
        }
        if (preserveMessage) {
            throw new Uncheck(t.getMessage(), t);
        } else {
            throw new Uncheck(t);
        }
    }

    public static <T> T call(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Throwable e) {
            throw throwAsUncheckedException(e);
        }
    }

    public static <T> List<T> callEach(final Callable<T>... callables) {
        if(callables == null || callables.length == 0) {
            return Collections.EMPTY_LIST;
        }
        boolean errorOccured = false;
        RuntimeException firstError = null;
        List<T> results = new ArrayList<>();
        for (int i = 0; i < callables.length; i++) {
            T t = null;
            try {
                t = call(callables[i]);
            } catch (RuntimeException ex) {
                errorOccured = true;
                firstError = ex;
            } finally {
                results.add(t);
            }
        }
        if(errorOccured) {
            throw firstError;
        }
        return results;
    }

    /**
     * Unwraps passed InvocationTargetException hence making the stack of exceptions cleaner without losing information.
     *
     * Note: always throws the failure in some form. The return value is to keep the compiler happy.
     *
     * @param e to be unwrapped
     * @return an instance of RuntimeException based on the target exception of the parameter.
     */
    public static RuntimeException rewrap(InvocationTargetException e) {
        return Uncheck.throwAsUncheckedException(e.getTargetException());
    }

}
