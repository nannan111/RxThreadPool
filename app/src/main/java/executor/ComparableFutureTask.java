package executor;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import yinterface.Submit;

/**
 * Created by EDZ on 2018/8/22.
 */

public class ComparableFutureTask<T> extends FutureTask<T> implements Comparable {
    private Submit submit;
    private boolean isShutDownTask=true;
    private HttpCallable callable;
    private YControl yControl;
    private PriorityBlockingQueue<ComparableFutureTask> priorityBlockingQueue;

    public ComparableFutureTask(@NonNull YCallable callable) {
        super(callable);
        callable.setFutureTask(this);
    }
    public ComparableFutureTask(@NonNull HttpCallable callable, YControl yControl) {
        super(callable);
        callable.setFutureTask(this);
        callable.setyControl(yControl);
        callable.setmContext(yControl.getmContext());
        this.callable=callable;
        this.yControl=yControl;
    }
    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
    public boolean isShutDownTask() {
        return isShutDownTask;
    }

    public void setShutDownTask(boolean shutDownTask) {
        isShutDownTask = shutDownTask;
    }

    public Submit getSubmit() {
        return submit;
    }
    public void setSubmit(Submit submit) {
        this.submit = submit;
    }
    public HttpCallable getCallable() {
        return callable;
    }
    public YControl getyControl() {
        return yControl;
    }

    public PriorityBlockingQueue<ComparableFutureTask> getPriorityBlockingQueue() {
        return priorityBlockingQueue;
    }

    public void setPriorityBlockingQueue(PriorityBlockingQueue<ComparableFutureTask> priorityBlockingQueue) {
        this.priorityBlockingQueue = priorityBlockingQueue;
    }
}
