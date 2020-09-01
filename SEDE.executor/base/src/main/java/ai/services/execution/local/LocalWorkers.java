package ai.services.execution.local;

import ai.services.executor.AccessControlQueue;
import ai.services.execution.QueueClosedException;
import ai.services.execution.operator.TaskOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * LocalWorkers is a class managing a group of threads that run in this java process to handle tasks.
 */
public class LocalWorkers implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(LocalWorkers.class);

    private final AccessControlQueue acq;

    private final TaskOperator taskOperator;

    private final ExecutorService service;

    private int currentThreadCount = 0;

    private int desiredThreadCount = 0;

    public LocalWorkers(AccessControlQueue acq, TaskOperator taskOperator,
                        ExecutorService service) {
        this.acq = acq;
        this.taskOperator = taskOperator;
        this.service = service;
    }

    public LocalWorkers(AccessControlQueue acq, TaskOperator operator) {
        this(acq, operator, Executors.newCachedThreadPool());
    }

    public synchronized void setDesiredThreadCount(int desiredThreadCount) {
        this.desiredThreadCount = desiredThreadCount;
        while(currentThreadCount < desiredThreadCount) {
            spawnThread();
            currentThreadCount++;
        }
    }


    public synchronized void shutdown() {
        desiredThreadCount = 0;
        service.shutdownNow();
    }

    private void spawnThread() {
        service.submit(this);
    }

    @Override
    public void run() {
        try {
            executeNextTask();
        } catch (QueueClosedException ex) {
            logger.info("Queue is closed. Shutting down workers.");
            setDesiredThreadCount(0);
        }
        catch (InterruptedException e) {
            logger.debug("Worker thread interrupted.");
        } catch(Throwable ex) {
            logger.error("Unexpected error: ", ex);
        } finally {
            synchronized (this) {
                if(currentThreadCount <= desiredThreadCount)
                    service.submit(this);
            }
        }
    }

    private void executeNextTask() throws InterruptedException, QueueClosedException {
        LocalTaskDispatch newDispatch = new LocalTaskDispatch(acq, taskOperator, Thread.currentThread());
        newDispatch.fetchJobAndExecute();
    }

}
