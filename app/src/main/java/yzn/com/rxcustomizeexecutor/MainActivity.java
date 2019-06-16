package yzn.com.rxcustomizeexecutor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.json.JSONException;
import executor.HttpCallable;
import executor.RequestEntity;
import executor.YCallable;
import executor.YError;
import executor.PoolExecutor;
import executor.ResponseEntity;
import executor.YControl;
import model.User;
import yinterface.Submit;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Submit submit=PoolExecutor.create();
        submit.sumitTask(this,true,new YCallable<User>() {
            @Override
            public User run() {
                return new User();
            }
            @Override
            public void duUi(User o) {

            }
        });
        String url="https://api.androidhive.info/volley/person_object.json";
          RequestEntity requestEntity =new RequestEntity();
        requestEntity.addParam("user","1111");
         submit.request(this,YControl.create().setUrl(url).setType(YControl.GET).setRequestEntity(requestEntity), new HttpCallable<User>() {
            @Override
            public User backResponse(ResponseEntity response) throws JSONException {//子线程处理任务
                User t = JSON.parseObject(response.getJsonObject().toString(), new TypeReference<User>() {
                });
                return t;
            }
            @Override
            public void onError(YError e) {
                Log.i("picooc",e.getException().getMessage()+"");
            }
            @Override
            public void duUi(User o) {//返回主线程
                Log.i("picooc",o.toString());
            }
        });
    }
}
