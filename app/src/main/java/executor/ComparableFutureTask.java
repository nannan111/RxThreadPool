package executor;
import android.content.Context;
import android.support.annotation.NonNull;
import java.util.concurrent.FutureTask;
/**
 * Created by EDZ on 2018/8/22.
 */

public class ComparableFutureTask<T> extends FutureTask<T> implements Comparable {
    public boolean isShutDownTask() {
        return isShutDownTask;
    }

    public void setShutDownTask(boolean shutDownTask) {
        isShutDownTask = shutDownTask;
    }

    private boolean isShutDownTask=true;

    public ComparableFutureTask(@NonNull YCallable callable) {
        super(callable);
        callable.setFutureTask(this);

    }
    public ComparableFutureTask(@NonNull HttpCallable callable, YControl yControl, Context mContext) {
        super(callable);
        callable.setFutureTask(this);
        callable.setyControl(yControl);
        callable.setmContext(mContext);
    }
    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}
