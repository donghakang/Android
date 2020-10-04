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
|```handler.setEmptyMessage(0)```|Start of Thread (?)|
|```handler.setEmptyMessageDelayed(0, 3000)```|Delay the thread, (working similiar like recursion) <br> 3000 will be just a time thread 3 seconds|
|```handler.removeMessage(0)```|end the thread.|