package executor;

import android.content.Context;

public class RequestArray {

    private HttpCallable runnable;

    private YControl yControl;

    public RequestArray(Builder builder) {
        this.runnable = builder.runnable;
        this.yControl=builder.yControl;
    }

    public HttpCallable getRunnable() {
        return runnable;
    }

    public void setRunnable(HttpCallable runnable) {
        this.runnable = runnable;
    }

    public YControl getyControl() {
        return yControl;
    }

    public void setyControl(YControl yControl) {
        this.yControl = yControl;
    }

    public static class Builder{
        private HttpCallable runnable;
        private YControl yControl;
        public Builder runnable(HttpCallable runnable){
            this.runnable=runnable;
            return this;
        }
        public Builder yControl(YControl yControl){
            this.yControl=yControl;
            return this;
        }
        public RequestArray build(){
            return new RequestArray(this);
        }
    }
}
