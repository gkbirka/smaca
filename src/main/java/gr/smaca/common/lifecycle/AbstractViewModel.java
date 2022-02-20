package gr.smaca.common.lifecycle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class AbstractViewModel {
    private final ExecutorService executor;

    protected AbstractViewModel(boolean initExecutor) {
        executor = initExecutor ? new ThreadPoolExecutor(
                1,
                1,
                0L,
                TimeUnit.MILLISECONDS,
                new SynchronousQueue<>()) {
            @Override
            public void setRejectedExecutionHandler(RejectedExecutionHandler handler) {
                new DiscardPolicy();
            }
        } : null;
    }

    protected final void execute(Runnable runnable) {
        if (executor != null) {
            executor.execute(runnable);
        }
    }

    public final void dispose() {
        if (executor != null) {
            executor.shutdown();
        }
    }
}