package executor;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by EDZ on 2018/8/20.
 */

public abstract class YCallable<T> implements Callable<T> {
    public FutureTask futureTask;

    public FutureTask getFutureTask() {
        return futureTask;
    }

    public void setFutureTask(FutureTask futureTask) {
        this.futureTask = futureTask;
    }
    private Handler handler=new Handler(Looper.getMainLooper()){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    duUi((T) msg.obj);
                    break;
                case 2:
                    error((Exception) msg.obj);
                    break;
            }
        }

    };

    public abstract T run();

    @Override
    public T call() {
//        mHandler handler=new mHandler();
        T t=null;
        try {
             t = run();
            if(!futureTask.isCancelled()) {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = t;
                handler.sendMessage(msg);
            }
        }catch (Exception e){
            if(!futureTask.isCancelled()) {
                Message msg = new Message();
                msg.what = 2;
                msg.obj = e;
                handler.sendMessage(msg);
            }
        }

        return t;
    }

    public abstract  void duUi(T t);
    public void error(Exception e){}
}
