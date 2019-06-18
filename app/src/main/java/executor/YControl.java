package executor;

import android.content.Context;

public class YControl {
    public static final String GET="GET";
    public static final String POST="POST";
    public static volatile YControl yControl;
    private String url;
    private String type;//请求方式
    private boolean isShutDownTask;//是否在退出acitivity的时候杀掉线程任务，为了防止内存泄漏，true
    private RequestEntity requestEntity;//请求参数
    private boolean isPostJson=false;//是否以json格式提交表单
    private String mediaType="application/json";
    private int delay;
    private Context mContext;

//    public static YControl create(){
//        if(yControl==null){
//            synchronized (YControl.class){
//                if(yControl==null)
//                    yControl=new YControl();
//
//            }
//        }
//        return yControl;
//    }

    public YControl(Context mContext,Builder builder) {
        this.url = builder.url;
        this.type=builder.type;
        this.isShutDownTask=builder.isShutDownTask;
        this.requestEntity=builder.requestEntity;
        this.isPostJson=builder.isPostJson;
        this.mediaType=builder.mediaType;
        this.delay=builder.delay;
        this.mContext=mContext;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public String getMediaType() {
        return mediaType;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public boolean isPostJson() {
        return isPostJson;
    }

    public void setPostJson(boolean postJson) {
        isPostJson = postJson;
    }

    public RequestEntity getRequestEntity() {
        if(requestEntity==null){
            synchronized (this){
                if(requestEntity==null)
                    requestEntity=new RequestEntity();
            }
        }
        return requestEntity;
    }

    public YControl setRequestEntity(RequestEntity requestEntity) {
        this.requestEntity = requestEntity;
        return this;
    }

    public boolean isShutDownTask() {
        return isShutDownTask;
    }

    public void setShutDownTask(boolean shutDownTask) {
        isShutDownTask = shutDownTask;
    }

    public String getUrl() {
        return url;
    }

    public YControl setUrl(String url){
        this.url=url;
        return this;
    }
    public String getType() {
        return type;
    }

    public YControl setType(String type) {
        this.type = type;
        return this;
    }

    public static class Builder{
        private String url;
        private String type;//请求方式
        private boolean isShutDownTask=true;//是否在退出acitivity的时候杀掉线程任务，为了防止内存泄漏，true
        private RequestEntity requestEntity;//请求参数
        private boolean isPostJson=false;//是否以json格式提交表单
        private String mediaType="application/json";
        private int delay;
        public Builder delay(int delay) {
            this.delay = delay;
            return this;
        }
        public Builder postJson(boolean postJson) {
            isPostJson = postJson;
            return this;
        }

        public Builder requestEntity(RequestEntity requestEntity) {
            this.requestEntity = requestEntity;
            return this;
        }

        public Builder isShutDownTask(boolean shutDownTask) {
            isShutDownTask = shutDownTask;
            return this;
        }
        public Builder url(String url){
            this.url=url;
            return this;
        }
        public Builder type(String type) {
            this.type = type;
            return this;
        }
        public YControl build(Context mContext){
            return new YControl(mContext,this);
        }
    }

}
