package executor;
import android.content.Context;
import java.util.HashMap;
import java.util.Map;
public class RequestEntity {
	private String method="";// 请求的接口的名称
	private String methodPHP;
	public static String appver = "i3.7.0";// 接口版本号，以便于后续做接口升级时用	// private String sign;// 验证证书
	private HashMap<String, Object> req; // 请求时携带的参数
	private String HttpType;//get,post
	public RequestEntity() {
		req = new HashMap<String, Object>();
	}
	public String getHttpType() {
		return HttpType;
	}

	public void setHttpType(String httpType) {
		HttpType = httpType;
	}
	public RequestEntity(String method) {
		this.method = method;
		this.methodPHP = "";
		// this.appver = appver;
		req = new HashMap<String, Object>();
	}

	public HashMap<String, Object> getReq() {
		return req;
	}

	public void setReq(HashMap<String, Object> req) {
		this.req = req;
	}

	public RequestEntity addParam(String key, Object value) {
		req.put(key, value);
		return this;
	}

	public Object getParam(String key) {
		return req.get(key);
	}

	public String getMethod() {
		return method;
	}


    public Map<String, String> getOkParams
			(Context context){
		addPublicAttributes();
        Map<String,String> newMap=new HashMap<String,String>();
        for (String key : req.keySet()) {
			if(req.get(key)!=null)
            newMap.put(key, req.get(key).toString());
        }
        return newMap;
    }

	public Map<String, Object> getOkPostBody
			(Context context){
		Map<String,Object> newMap=new HashMap<String,Object>();
		for (String key : req.keySet()) {
			if(req.get(key)!=null)
				newMap.put(key, req.get(key));
		}
		return newMap;
	}
	/**
	 * 添加公共参数
	 */
	private void addPublicAttributes(){
		long timestamp = System.currentTimeMillis() / 1000;
		this.addParam("appver", RequestEntity.appver);
		this.addParam("timestamp", timestamp + "");
		this.addParam("os", "android");
		this.addParam("lang", "zh");
		this.addParam("method", method);
		this.addParam("sign", "11111111");
		this.addParam("push_token", "2222222");
		this.addParam("version", "1");
	}

	/**
	 * 获取公共参数
	 * @return
	 */
	public Map<String, String> getPublicAttributes(){
		HashMap<String, String> req = new HashMap<String, String>();
		long timestamp = System.currentTimeMillis() / 1000;
		req.put("appver", RequestEntity.appver);
		req.put("timestamp", timestamp + "");
		req.put("os", "android");
		req.put("lang", "zh");
		req.put("method", method);
		req.put("sign", "11111111");
		req.put("push_token", "2222222");
		req.put("version", "1");
		return req;

	}



}
