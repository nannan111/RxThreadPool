package yzn.com.rxcustomizeexecutor;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import executor.PoolExecutor;
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * 退出一个activity就要杀掉响应的任务，防止内存泄漏
         */
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }
            @Override
            public void onActivityDestroyed(Activity activity) {
                PoolExecutor.create().shutDownTask(activity.toString());
            }
        });
    }
}
