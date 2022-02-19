package gr.smaca.common.lifecycle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class AbstractViewModel {
    protected final ExecutorService executor;

    public AbstractViewModel(boolean initExecutor) {
        this.executor = initExecutor ? new ThreadPoolExecutor(
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

    public final void dispose() {
        if (executor != null) {
            executor.shutdown();
        }
    }
}