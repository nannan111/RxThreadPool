package executor;

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

    public static YControl create(){
        if(yControl==null){
            synchronized (YControl.class){
                if(yControl==null)
                    yControl=new YControl();

            }
        }
        return yControl;
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

}
