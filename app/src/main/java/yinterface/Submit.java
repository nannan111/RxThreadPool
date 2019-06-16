package yinterface;

import android.app.Activity;
import android.content.Context;

import executor.HttpCallable;
import executor.YCallable;
import executor.YControl;

public interface Submit {

    /**
     *
     * @param activity 当前activity对象
     * @param isShutDownTask 在退出当前actvity的时候是否要关闭该任务
     * @param runnable 回调接口
     */
    public void sumitTask(Activity activity, boolean isShutDownTask, YCallable runnable);
    /**
     * 该方法进行网络请求
     * @param activity 不在activity中使用
     * @param yControl 请求参数以及get or post 其它
     * @param runnable 回调对象
     */
    public void request(Context activity, YControl yControl, HttpCallable runnable);

}
