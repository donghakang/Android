# Handler
Android will not able you to add Thread to visible UI (such as Progress Bar, Canvas)
If it is visual Thread working, use handler.

## setup
```JAVA
Handler handler = new Handler() {
    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        ...
    }
};
```

|type|description|
|-|-|
|```handler.sendEmptyMessage(0)```|Start of Thread (?)|
|```handler.sendEmptyMessageDelayed(0, 3000)```|Delay the thread, (working similiar like recursion) <br> 3000 will be just a time thread 3 seconds|
|```handler.removeMessage(0)```|end the thread.|

## usage
#### ```sendEmtpyMessage(0)```
```Java
int count = 0;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    handler.sendEmptyMessage(0);
}

@Override
protected void onDestroy() {
    super.onDestroy();
    handler.removeMessages(0);
}

Handler handler = new Handler() {
    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        handler.sendEmptyMessageDelayed(0, 1000);
        Log.d("DEBUG", count);
        count += 1;
        handler.removeMessages(0);
    }
};
```
```sendEmptyMessage(0)```을 부르고, 바로 handler 메소드로 들어간다. ```sendEmptyMessageDelayed(0, 1000)```를 이용해서 각 루프마다 1초씩 딜레이를 시킨다. 그 후 count 값을 변경 해주며
즉 일초 간격으로 로그에 0 부터 계속 숫자를 증가시키며 프린트 할 것이다.

#### ```sendEmptyMessageDelayed(0, 1000)```
```Java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    handler.sendEmptyMessageDelayed(0, 5000);
}

@Override
protected void onDestroy() {
    super.onDestroy();
    handler.removeMessages(0);
}

Handler handler = new Handler() {
    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        Log.d("DEBUG", "Print");
        handler.removeMessages(0);
    }
};
```
그냥 한번만 5초 딜레이를 할 경우, 굳이 ```sendEmptyMessage(0)```를 사용하지 않아도 한번 Message를 보낼 수 있는데, 맨 처음에 5초 딜레이 후에 handler 메소드 안에 있는 Log를 프린트한다.
즉, 5초 뒤에 로그에 "Print"를 프린트 할 것이다.
여기서 ```removeMessages(0)```를 부르지 않으면 계속 메시지를 부르니 꼭 removeMessages를 해주자. 

* 무조건! ```removeMessage(0)```를 사용한다. 안그러면 앱이 꺼지는 상황에서도 handler가 작동 될 수 있다. 
* Thread와 차이점을 알자 (```while(true)```)
