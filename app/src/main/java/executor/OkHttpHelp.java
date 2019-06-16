package executor;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class OkHttpHelp {
    public static final int CONNECTTIMEOUT=15;
    public static final int READTIMEOUT=10000;
    public static final int WRITETIMEOUT=10000;
    private volatile static OkHttpHelp mInstance;
    private OkHttpClient mOkHttpClient;
    public OkHttpHelp(OkHttpClient okHttpClient)
    {
        if (okHttpClient == null)
        {
             mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(CONNECTTIMEOUT, TimeUnit.SECONDS)      //设置连接超时
                    .readTimeout(READTIMEOUT, TimeUnit.SECONDS)         //设置读超时
                    .writeTimeout(WRITETIMEOUT, TimeUnit.SECONDS)        //设置写超时
                    .retryOnConnectionFailure(true)            //是否自动重连
                    .build();
        } else
        {
            mOkHttpClient = okHttpClient;
        }
    }
    public static OkHttpHelp initClient(OkHttpClient okHttpClient)
    {
        if (mInstance == null)
        {
            synchronized (OkHttpHelp.class)
            {
                if (mInstance == null)
                {
                    mInstance = new OkHttpHelp(okHttpClient);
                }
            }
        }
        return mInstance;
    }
    public static OkHttpHelp getInstance()
    {
        return initClient(null);
    }
    public OkHttpClient getOkHttpClient()
    {
        return mOkHttpClient;
    }
}
