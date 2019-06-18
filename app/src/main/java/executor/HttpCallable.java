package executor;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.concurrent.Callable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
/**
 * Created by EDZ on 2018/8/20.
 */
public abstract class HttpCallable<T> implements Callable<T> {
    private ComparableFutureTask futureTask;
    private YControl yControl;
    private Context mContext;
    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public ComparableFutureTask getFutureTask() {
        return futureTask;
    }


    public void setFutureTask(ComparableFutureTask futureTask) {
        this.futureTask = futureTask;
    }

    public YControl getyControl() {
        return yControl;
    }

    public void setyControl(YControl yControl) {
        this.yControl = yControl;
    }
    private Handler handler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    duUi((T) msg.obj);
                    if(futureTask!=null&&futureTask.getSubmit()!=null)
                    futureTask.getSubmit().multitaskRequestStart(futureTask.getPriorityBlockingQueue());
                    break;
                case 2://error
                    onError((YError) msg.obj);
                    if(futureTask!=null&&futureTask.getSubmit()!=null)
                        futureTask.getSubmit().multitaskRequestStart(futureTask.getPriorityBlockingQueue());
                    break;
            }
        }

    };
    public abstract T backResponse(ResponseEntity response) throws JSONException;

    public abstract void onError(YError e);

    @Override
    public T call() {
        T t = null;
        try {
             backRespose(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (!futureTask.isCancelled()) {
                        Message msg = new Message();
                        msg.what = 2;
                        msg.obj = new YError(e);
                        handler.sendMessage(msg);
                    }
                }
                @Override
                public void onResponse(Call call, Response response) {
                    if (!futureTask.isCancelled())
                    parserResponse(response);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            if (!futureTask.isCancelled()) {
                Message msg = new Message();
                msg.what = 2;
                msg.obj = new YError(e);
                handler.sendMessage(msg);
            }
        }
        return t;
    }


    private void backRespose(Callback callback) throws IOException {
        switch (yControl.getType()) {
            case YControl.POST:
                if (yControl.isPostJson()){
                    YHttpRequest.picoocPostJson(yControl, yControl.getUrl(), getmContext(),callback);
                }else {
                    YHttpRequest.picoocPost(yControl, yControl.getUrl(), getmContext(), callback);
                }
                break;
            case YControl.GET:
                YHttpRequest.picoocGet(yControl, yControl.getUrl(), getmContext(), callback);
                break;
        }
    }

    //    T t= backResponse(response);
//    Message msg = new Message();
//    msg.what = 1;
//    msg.obj = t;
//                if(!futureTask.isCancelled())
//                        handler.sendMessage(msg);
    public abstract void duUi(T t);

    private void parserResponse(Response response) {
        try {
            String res = response.body().string();
            if (res != null) {
                Log.i("yangzhinanhttp", "响应:" + res);
            }
            int code = response.code();
            if (new JSONObject(res).has("code")) {
                code = new JSONObject(res).getInt("code");
            }
            if (code == 200 || code == 0) {
                ResponseEntity resp = new ResponseEntity(new JSONObject(res));
                T t = backResponse(resp);
                if (!futureTask.isCancelled()) {
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = t;
                    handler.sendMessage(msg);
                }
            } else {
                ResponseEntity resp = new ResponseEntity(new JSONObject(res));
                if (!futureTask.isCancelled()) {
                    Message msg = new Message();
                    msg.what = 2;
                    msg.obj = new YError(new Exception(resp.getMessage())).setErrorCode(code);
                    handler.sendMessage(msg);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            if (!futureTask.isCancelled()) {
                Message msg = new Message();
                msg.what = 2;
                msg.obj = new YError(new Exception(e));
                handler.sendMessage(msg);
            }

        } catch (IOException e) {
            e.printStackTrace();
            if (!futureTask.isCancelled()) {
                Message msg = new Message();
                msg.what = 2;
                msg.obj = new YError(new Exception(e));
                handler.sendMessage(msg);
            }
        }
    }



}
