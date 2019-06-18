package executor;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import tools.ExecutorTools;
import yinterface.Submit;

public class PoolExecutor implements Submit {
    private static int corePoolSize = 5;
    private static int maximumPoolSize = 10;
    private static long keepAliveTime = 2;
    private static TimeUnit unit = TimeUnit.SECONDS;
    private static PriorityBlockingQueue<Runnable> workQueue = new PriorityBlockingQueue<Runnable>();
    private volatile PicPausableThreadPoolExecutor PoolExecutor;//线程池对象
    private static PoolExecutor poolExecutor;
    private static ScheduledExecutorService scheduledExecutorService;
    //    private static PriorityBlockingQueue<ComparableFutureTask> waitQueue = new PriorityBlockingQueue<>();
    private static PriorityBlockingQueue<ComparableFutureTask> waitRequestQueue = new PriorityBlockingQueue<>();

    public static PoolExecutor create() {
        if (poolExecutor == null) {
            synchronized (PoolExecutor.class) {
                if (poolExecutor == null)
                    poolExecutor = new PoolExecutor();
            }
        }
        return poolExecutor;
    }

    public static ScheduledExecutorService getScheduledExecutorService() {
        if (scheduledExecutorService == null) {
            synchronized (PoolExecutor.class) {
                if (scheduledExecutorService == null)
                    scheduledExecutorService = Executors.newScheduledThreadPool(3);
            }
        }
        return scheduledExecutorService;
    }

    /**
     * @param corePoolSize    核心线程数的大小
     * @param maximumPoolSize 最大线程数量
     * @param keepAliveTime   除了核心线程外，其余的空闲线程存活的时间
     * @param unit            上面时间的单位
     * @param workQueue       线程列队
     * @see {@link ThreadPoolExecutor#ThreadPoolExecutor(int, int, long, TimeUnit, BlockingQueue)}
     */
    public PoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, PriorityBlockingQueue<Runnable> workQueue) {
        PoolExecutor = new PicPausableThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        workQueue.poll();
    }

    /**
     * 该构造方法创建线程池的时候采用默认的方法
     */
    public PoolExecutor() {
        PoolExecutor = new PicPausableThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    /**
     * 获取线程池对象
     *
     * @return
     */
    public PicPausableThreadPoolExecutor getPoolExecutor() {
        return PoolExecutor != null ? PoolExecutor : new PicPausableThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    private static volatile Map<String, List<ComparableFutureTask>> futureMap = new HashMap<>();

    private static volatile Map<String, PriorityBlockingQueue<ComparableFutureTask>> priorityBlockingQueueHashMap = new HashMap<>();

    private synchronized void addFutureMap(String act, ComparableFutureTask comparableFutureTask) {
        if (futureMap.get(act.toString()) == null) {
            synchronized (PoolExecutor.class) {
                if (futureMap.get(act.toString()) == null) {
                    List<ComparableFutureTask> futureList = new ArrayList<>();
                    futureList.add(comparableFutureTask);
                    futureMap.put(act.toString(), futureList);
                } else {
                    List<ComparableFutureTask> futureList = futureMap.get(act.toString());
                    futureList.add(comparableFutureTask);
                    futureMap.put(act.toString(), futureList);
                }
            }
        } else {
            List<ComparableFutureTask> futureList = futureMap.get(act.toString());
            futureList.add(comparableFutureTask);
            futureMap.put(act.toString(), futureList);
        }
    }

    /**
     * 关闭任务
     *
     * @param /** 那么cancel是如何工作的呢？
     *            <p>
     *            当你想要取消你已提交给执行者的任务，使用Future接口的cancel()方法。
     *            根据cancel()方法参数和任务的状态不同，这个方法的行为将不同：
     *            1、如果这个任务已经完成或之前的已经被取消或由于其他原因不能被取消，
     *            那么这个方法将会返回false并且这个任务不会被取消。
     *            2、如果这个任务正在等待执行者获取执行它的线程，那么这个任务将被取消而且不会开始他的执行。
     *            如果这个任务已经正在运行，则视方法的参数情况而定。
     *            cancel()方法接收一个Boolean值参数。
     *            如果参数为true并且任务正在运行，那么这个任务将被取消。
     *            如果参数为false并且任务正在运行，那么这个任务将不会被取消。
     */
    public synchronized void shutDownTask(String threadKey) {
        if (futureMap.get(threadKey.toString()) != null) {
            List<ComparableFutureTask> futureList = futureMap.get(threadKey.toString());
            if (futureList.size() > 0)
                for (int i = 0; i < futureList.size(); i++) {
                    if (futureList.get(i) != null && futureList.get(i).isShutDownTask()) {
                        try {
                            boolean isCanel = futureList.get(i).cancel(true);
                            Log.i("yzn111", "isCanel=" + isCanel + "--act==" + threadKey);
                        } catch (Exception e) {
                        }
                    }
                }

            futureList.clear();
        }

    }

    private void sumitOneTask(Context act, boolean isShutDownTask, YCallable runnable) {
        if (PoolExecutor == null) {
            PoolExecutor = getPoolExecutor();
        }
        try {
            ComparableFutureTask futureTask = new ComparableFutureTask<>(runnable);
            futureTask.setShutDownTask(isShutDownTask);
            if (act instanceof Activity) {
                addFutureMap(act.toString(), futureTask);
            }
            PoolExecutor.execute(futureTask);
        } catch (Exception e) {
            Log.i("yangzhinan", e.toString());
        }
    }

    /**
     * 添加任务
     *
     * @param act
     * @param isShutDownTask
     * @param runnables
     */
    @Override
    public void sumitTask(Context act, boolean isShutDownTask, YCallable... runnables) {
        if (runnables.length == 1) {
            for (YCallable runnable : runnables) {
                sumitOneTask(act, isShutDownTask, runnable);
            }
        } else {
            PriorityBlockingQueue<ComparableFutureTask> waitQueue = new PriorityBlockingQueue<>();
            for (YCallable runnable : runnables) {
                ComparableFutureTask futureTask = new ComparableFutureTask<>(runnable);
                futureTask.setShutDownTask(isShutDownTask);
                futureTask.setSubmit(this);
                futureTask.setPriorityBlockingQueue(waitQueue);
                if (act instanceof Activity) {
                    addFutureMap(act.toString(), futureTask);
                }
                waitQueue.put(futureTask);
            }
            multitaskStart(waitQueue);
        }
    }


    public void multitaskStart(PriorityBlockingQueue<ComparableFutureTask> waitQueue) {
        if (PoolExecutor == null) {
            PoolExecutor = getPoolExecutor();
        }
        if (waitQueue == null) return;
        if (waitQueue != null && waitQueue.size() > 0) {
            ComparableFutureTask futureTask = waitQueue.poll();
            PoolExecutor.execute(futureTask);
        }
    }

    @Override
    public void request(YControl yControl, HttpCallable runnable) {
        if (PoolExecutor == null) {
            PoolExecutor = getPoolExecutor();
        }
        try {
            Log.i("999888888", "---ComparableFutureTask");
//            Looper.prepare();
            if (!ExecutorTools.isNetworkConnected(yControl.getmContext()) || yControl.getmContext() == null) {
                runnable.onError(new YError(new Exception("网络错误")));
                return;
            }
            ComparableFutureTask futureTask = new ComparableFutureTask<>(runnable, yControl);
            futureTask.setShutDownTask(yControl.isShutDownTask());
            if (yControl.getmContext() instanceof Activity) {
                addFutureMap(yControl.getmContext().toString(), futureTask);
            }
            if (yControl.getDelay() <= 0) {//看看该任务需要delay吗
                PoolExecutor.execute(futureTask);
            } else {
                getScheduledExecutorService().schedule(futureTask, yControl.getDelay(), TimeUnit.MILLISECONDS);
            }
        } catch (Exception e) {
            Log.i("yangzhinan", e.toString());
        }
    }
    @Override
    public void multitaskRequestStart(PriorityBlockingQueue<ComparableFutureTask> waitQueue) {
        if (PoolExecutor == null) {
            PoolExecutor = getPoolExecutor();
        }
        if (waitQueue != null && waitQueue.size() > 0) {
            ComparableFutureTask futureTask = waitQueue.poll();
            if (!ExecutorTools.isNetworkConnected(futureTask.getyControl().getmContext()) || futureTask.getyControl().getmContext() == null) {
                futureTask.getCallable().onError(new YError(new Exception("网络错误")));
            }
            if (futureTask.getyControl().getDelay() <= 0) {//看看该任务需要delay吗
                PoolExecutor.execute(futureTask);
            } else {
                getScheduledExecutorService().schedule(futureTask, futureTask.getyControl().getDelay(), TimeUnit.MILLISECONDS);
            }
        }
    }

    @Override
    public  void initRequest(RequestArray ... requestArrays){
        PriorityBlockingQueue<ComparableFutureTask> waitRequestQueue = new PriorityBlockingQueue<>();
        for (RequestArray requestArray:requestArrays){
            YControl yControl = requestArray.getyControl();
            ComparableFutureTask futureTask = new ComparableFutureTask<>(requestArray.getRunnable(), yControl);
            futureTask.setShutDownTask(yControl.isShutDownTask());
            futureTask.setSubmit(this);
            futureTask.setPriorityBlockingQueue(waitRequestQueue);
            if (yControl.getmContext() instanceof Activity) {
                addFutureMap(yControl.getmContext().toString(), futureTask);
            }
            waitRequestQueue.put(futureTask);
        }
        multitaskRequestStart(waitRequestQueue);
    }
}
