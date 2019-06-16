package executor;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by EDZ on 2018/8/22.
 */

public class YHttpRequest {
    public static void picoocGet(YControl yControl, String javaUrl, Context mContext, Callback picoocCallBack) throws IOException {
        String url = javaUrl;
        // 初始化 OkHttpClient
        url = appendParams(url, yControl.getRequestEntity().getOkParams(mContext));
        Log.i("yangzhinanhttp", "url=" + url);
        // 初始化请求体
        Request request = new Request.Builder().url(url).get().build();
//        Call call = client.newCall(request);
//        return call.execute();
        OkHttpHelp.getInstance().getOkHttpClient().newCall(request).enqueue(picoocCallBack);
    }

    /**
     * application/json
     * @param yControl
     * @param javaUrl
     * @param mContext
     * @param picoocCallBack
     * @throws IOException
     * 这个方法是公共参数拼接到？后面
     */
    public static void picoocPost(YControl yControl, String javaUrl, Context mContext, Callback picoocCallBack) throws IOException {
//        String javaUrl = getUseUrl(requestEntity.getHttpType());
        String url = javaUrl;
        url = appendParams(url, yControl.getRequestEntity().getPublicAttributes());
        Log.i("yangzhinanhttp", "url=" + url);
        MediaType mediaType = MediaType.parse(yControl.getMediaType());//application/x-www-form-urlencoded
//        String requestBody = mapToString(requestEntity.getOkPostBody(mContext));
        com.alibaba.fastjson.JSONObject json= (com.alibaba.fastjson.JSONObject) JSON.toJSON(yControl.getRequestEntity().getOkPostBody(mContext));
//        if(requestBody!=null)
//        Log.i("yangzhinanhttp", "url=" + url + "----" + requestBody.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(mediaType, json.toString()))
                .build();
//        return call.execute();
        OkHttpHelp.getInstance().getOkHttpClient().newCall(request).enqueue(picoocCallBack);
    }

    /**
     * 后端指定了json接收格式，content-type值和上面的方法有区别
     * @param javaUrl
     * @param mContext
     * @param picoocCallBack
     * @throws IOException
     * "application/json; charset=utf-8"
     * 这个方法是把所有的参数以json填到body里面
     */
    public static void picoocPostJson(YControl yControl, String javaUrl, Context mContext, Callback picoocCallBack) throws IOException {
//        String javaUrl = getUseUrl(requestEntity.getHttpType());
        String url = javaUrl;
        MediaType mediaType = MediaType.parse(yControl.getMediaType());//application/x-www-form-urlencoded
//        String requestBody = mapToString(requestEntity.getOkParams(mContext));
//        JSONObject json = JSONObject.(requestEntity.getOkParams(mContext).toString());
        JSONObject json= (JSONObject) JSON.toJSON(yControl.getRequestEntity().getOkParams(mContext));
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(mediaType, json.toString()))
                .build();
        OkHttpHelp.getInstance().getOkHttpClient().newCall(request).enqueue(picoocCallBack);
    }

    protected static String appendParams(String url, Map<String, String> params) {
        if (url == null || params == null || params.isEmpty()) {
            return url;
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            builder.appendQueryParameter(key, params.get(key));
        }
        return builder.build().toString();
    }

    public static String mapToString(Map<String, Object> req) {
        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("?");
        if (req == null) {
            return "";
        }
        Iterator<String> iterator = req.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object value = req.get(key);
            stringBuilder.append(key);
            stringBuilder.append("=");
            stringBuilder.append(value);
            if (iterator.hasNext()) {
                stringBuilder.append("&");
            }
        }
        return stringBuilder.toString();
    }

}
