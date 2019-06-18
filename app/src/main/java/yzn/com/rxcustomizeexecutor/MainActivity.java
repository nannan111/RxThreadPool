package yzn.com.rxcustomizeexecutor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.json.JSONException;
import org.json.JSONObject;
import executor.HttpCallable;
import executor.RequestArray;
import executor.RequestEntity;
import executor.YCallable;
import executor.YError;
import executor.PoolExecutor;
import executor.ResponseEntity;
import executor.YControl;
import model.DataModel;
import model.User;
import yinterface.Submit;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        oneTask();
//        moreTask();
//        moreTask2();
//        oneRequest();
        moreRequest();
        moreRequest();
    }
    /**
     * 开启一个线程执行任务
     */
    private void oneTask(){
        Submit submit=PoolExecutor.create();
        submit.sumitTask(this,true,new YCallable<User>() {
            @Override
            public User run() throws JSONException {
                JSONObject jsonObject=new JSONObject();
                String a=jsonObject.getString("sssss");
                for (int i = 0; i <5 ; i++) {
                    Log.i("text","第一个任务"+i);
                }
                return new User();
            }
            @Override
            public void error(Exception e) {
                Log.i("text","第一个任务异常了");
            }

            @Override
            public void duUi(User o) {
                Log.i("text","第一个任务结束----------------");
            }
        });
    }
    /**
     * 处理多个任务按顺序执行
     */
    private void moreTask(){
        Submit submit=PoolExecutor.create();
        submit.sumitTask(this,true,new YCallable<User>() {
            @Override
            public User run() throws JSONException {
//                JSONObject jsonObject=new JSONObject();
//                String a=jsonObject.getString("sssss");
                for (int i = 0; i <5 ; i++) {
                    Log.i("text","第一个任务"+i);
                }
                return new User();
            }
            @Override
            public void error(Exception e) {
                Log.i("text","第一个任务异常了");
            }
            @Override
            public void duUi(User o) {
                Log.i("text","第一个任务结束----------------");
            }
        },new YCallable<User>() {
            @Override
            public User run() throws JSONException {
//                JSONObject jsonObject=new JSONObject();
//                String a=jsonObject.getString("sssss");
                for (int i = 0; i <5 ; i++) {
                    Log.i("text","第二个任务"+i);
                }
                return new User();
            }
            @Override
            public void error(Exception e) {
                Log.i("text","第二个任务异常了");
            }
            @Override
            public void duUi(User o) {
                Log.i("text","第二个任务结束----------------");
            }
        });
    }
    private void moreTask2(){
        Submit submit=PoolExecutor.create();
        submit.sumitTask(this,true,new YCallable<User>() {
            @Override
            public User run() throws JSONException {
//                JSONObject jsonObject=new JSONObject();
//                String a=jsonObject.getString("sssss");
                for (int i = 0; i <5 ; i++) {
                    Log.i("text","第三个任务"+i);
                }
                return new User();
            }
            @Override
            public void error(Exception e) {
                Log.i("text","第三个任务异常了");
            }
            @Override
            public void duUi(User o) {
                Log.i("text","第三个任务结束----------------");
            }
        },new YCallable<User>() {
            @Override
            public User run() throws JSONException {
//                JSONObject jsonObject=new JSONObject();
//                String a=jsonObject.getString("sssss");
                for (int i = 0; i <5 ; i++) {
                    Log.i("text","第四个任务"+i);
                }
                return new User();
            }
            @Override
            public void error(Exception e) {
                Log.i("text","第四个任务异常了");
            }
            @Override
            public void duUi(User o) {
                Log.i("text","第四个任务结束----------------");
            }
        });
    }
    /**
     * 开启一个网络请求
     */
    private void oneRequest(){
        Submit submit=PoolExecutor.create();
        String url="https://api.androidhive.info/volley/person_object.json";
        String url2="http://api.map.baidu.com/location/ip?ak=32f38c9491f2da9eb61106aaab1e9739&ip=";
        String url3="https://api2.picooc.com/v1/api/mixData/loadingBodyData?os=android&method=mixData%2FloadingBodyData&device_id=865296033258870&roleId=5721780&appver=4.0.7.1&sign=197E97EC02D801DC42A0ADAF08F8E33B&pageSize=200&timeZone=Asia%2FShanghai&userId=9832&version=4.0.7&isMainRole=true&device_mac=02%3A00%3A00%3A00%3A00%3A00&mainRoleId=18023&bodyTime=1502522292&lastXCXBodyIndexTime=0&reqType=2&lang=zh_CN&lastDeleteBodyIndexTime=0&push_token=android%3A%3A865296033258870&timestamp=1561013972";
        String url4="https://www.kuaidi100.com/query?type=yuantong&postid=11111111111";
        RequestEntity requestEntity =new RequestEntity();
//        requestEntity.addParam("user","1111");
        submit.request( new YControl.Builder().url(url4).type(YControl.GET).requestEntity(requestEntity).build(this), new HttpCallable<DataModel>() {
            @Override
            public DataModel backResponse(ResponseEntity response) throws JSONException {//子线程处理任务
                DataModel t = JSON.parseObject(response.getJsonObject().toString(), new TypeReference<DataModel>() {
                });
                return t;
            }
            @Override
            public void onError(YError e) {
                Log.i("picooc",e.getException().getMessage()+"");
            }
            @Override
            public void duUi(DataModel o) {//返回主线程
                Log.i("picooc",o.toString());
            }
        });
    }

    /**
     * 开启多个网络请求
     */
    private void moreRequest(){
        Submit submit=PoolExecutor.create();
        String url="https://api.androidhive.info/volley/person_object.json";
        String url4="https://www.kuaidi100.com/query?type=yuantong&postid=11111111111";
        RequestEntity requestEntity =new RequestEntity();
        requestEntity.addParam("user","1111");
        submit.initRequest( new RequestArray.Builder().runnable(new HttpCallable<User>() {
            @Override
            public User backResponse(ResponseEntity response) throws JSONException {
                User t = JSON.parseObject(response.getJsonObject().toString(), new TypeReference<User>() {
                });
                return t;
            }

            @Override
            public void onError(YError e) {

            }

            @Override
            public void duUi(User o) {
                Log.i("picooc",o.toString());
            }
        }).yControl(new YControl.Builder()
                .url(url)
                .type(YControl.GET)
                .requestEntity(requestEntity)
                .build(this))
                .build(),
                new RequestArray.Builder().runnable(new HttpCallable<DataModel>() {
            @Override
            public DataModel backResponse(ResponseEntity response) throws JSONException {
                DataModel t = JSON.parseObject(response.getJsonObject().toString(), new TypeReference<DataModel>() {
                });

                return t;
            }

            @Override
            public void onError(YError e) {
                Log.i("picooc",e.getException().getMessage());
            }

            @Override
            public void duUi(DataModel o) {
                Log.i("picooc",o.toString());
            }
        }).yControl(new YControl.Builder()
                        .url(url4)
                        .type(YControl.GET)
                        .requestEntity(requestEntity)
                        .build(this))
                        .build());




    }

}
