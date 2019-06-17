# RxThreadPool
自定义类似RxJava和改造的结合体的线程池0.1，可以网络请求，2。开本地线程。使用简单，防止内存泄漏。扩展性比较强，使用了OKhttp3，FASTJSON
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
            
            QQ:450508605    欢迎交流
