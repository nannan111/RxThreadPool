package yinterface;

import android.app.Activity;
import android.content.Context;

import java.util.concurrent.PriorityBlockingQueue;

import executor.ComparableFutureTask;
import executor.HttpCallable;
import executor.RequestArray;
import executor.YCallable;
import executor.YControl;

public interface Submit {

    /**
     * @param context       当前activity对象
     * @param isShutDownTask 在退出当前actvity的时候是否要关闭该任务
     */
    void sumitTask(Context context, boolean isShutDownTask,  YCallable ... runnables);
    /**
     * 该方法进行网络请求
     *
     * @param yControl 请求参数以及get or post 其它
     * @param runnable 回调对象
     */
    void request(YControl yControl, HttpCallable runnable);
    /**
     * 多任务开始执行
     * @return
     */
    void multitaskStart(PriorityBlockingQueue<ComparableFutureTask> waitQueue);
    /**
     * 开始执行多任务网络请求按照顺序执行
     * @return
     */
    void multitaskRequestStart(PriorityBlockingQueue<ComparableFutureTask> waitQueue);

    /**
     * 多任务按顺序网络请求
     * @param requestArrays
     */
    void initRequest(RequestArray ... requestArrays);

}
