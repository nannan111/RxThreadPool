package executor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ResponseEntity {
	private String method;
	private JSONObject resp;
	private JSONArray resps;
	public static final String SUCCESS = "200";
	public JSONObject jsonObject;
	public String message;
	public ResponseEntity(JSONObject jsonObject) throws JSONException {
	this.jsonObject=jsonObject;
	if(jsonObject.has("message")){
		setMessage(jsonObject.getString("message"));
	}
	}
	public JSONObject getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public <T> T getRespByName(String name, Class<T> t) {
		if (resp == null) {
			return null;
		}
		T value = null;
		try {
			Object v = resp.get(name);
			value = (T) v;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 得到请求状况消息提示
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	public void setMessage(String message){
		this.message=message;
	}

	/**
	 * 得到请求接口名
	 * 
	 * @return
	 */
	public String getMethod() {
		return method;
	}
}
