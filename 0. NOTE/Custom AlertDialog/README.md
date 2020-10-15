# Custom AlertDialog

### Previous AlertDialog
```JAVA
private void showDial() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Title...");
    builder.setMessage("Message");
    builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    });

    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    });
}
```


### Custom AlertDialog
```JAVA
private void showDial() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);

    /** 기후에 맞춰서 title, message, negative button, positive button을 넣는다 **/
    builder.setTitle("Title...");


    LayoutInflater lnf = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View v = lnf.inflate(R.layout.item_layout, null, false);
    TextView a = v.findViewById(R.id.tv_lnf);
    a.setText("LAYOUT INFLATER");

    // builder에 layout/item_layout.xml에 저장된 화면 설정을 넣고 화면에 띄운다.
    builder.setView(v);
    builder.create().show();
    }
```

- **R.layout.item_layout** is the xml file that is created for custom AlertDialog 