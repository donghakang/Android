# SQL Lite
this note will not cover how SQL works, we will cover how to obtain SQL data.

## setup 
```JAVA
private void init() {
    SQLiteDatabase db = openOrCreateDatabase("sqlist_test.db", Context.MODE_PRIVATE, null);

    db.execSQL("CREATE TABLE IF NOT EXISTS member("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "name TEXT, "
            + "age  INTEGER, "
            + "addr TEXT" + ");");
}
```
- **openOrCreateDatabase**: open database, or create database with private/public mode
- **"CREATE TABLE IF NOT ..."**: database setup.
- **AUTOINCREMENT**: no need to set up integer, but it will automatically increment number starting from 0 (good for idx, or id)


#### Insert
```JAVA
db.execSQL("    INSERT INTO member (name, age, addr) VALUES ('하하', 10, 'Seoul')    ");

String data1 = "INSERT INTO member (name, age, addr) VALUES ('동하', 26, 'Seoul')";
db.execSQL(data1);
```


#### Delete
```JAVA
db.execSQL("DELETE FROM member WHERE name='하하' AND age=10 AND addr='Seoul'");

String data1 = "DELETE FROM member WHERE name='하하' AND age=10 AND addr='Seoul'";
db.execSQL(data1);
```

#### Search
```Java
Cursor c = db.rawQuery("SELECT * FROM member", null);
c.moveToFirst();
while (c.isAfterLast() == false) {
    Log.d("SQLTEST", "name: " + c.getString(1) + "   age: " + c.getInt(2) + "   addr: " + c.getString(3));
    c.moveToNext();
}
c.close();
```

|type|description|
|-|-|
|c.moveToFirst()|go to the first of the SQL table.<br>It is good to call this everytime when data is called|
|c.isAfterLast|if it is at this end, it ends (while true)|
|c.getString(1)|get the first item. (*getString(0)* will be idx).<br> In the example, *getString(1)* will be "name"|
|c.moveToNext()|next line|
|c.close()|close|


#### Close
```JAVA
db.close()
```
because of the memory problem. It is necessary to close the db.

---

### SQL Helper
```JAVA
public class SqlHelper {
    static Context con;

    //db 테이블 초기화 작업
    public static void init(Context context) {
        con = context;
        SQLiteDatabase db = con.openOrCreateDatabase("sqlist_test.db", Context.MODE_PRIVATE, null);

        db.execSQL("CREATE TABLE IF NOT EXISTS sch("
                + "idx INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "content TEXT,"
                + "year TEXT,"
                + "month TEXT,"
                + "day TEXT);");
        db.close();
    }

    public static void getData(String year, String month, String day, ArrayList<DialItemData> dialItemArr){
        SQLiteDatabase db = con.openOrCreateDatabase("sqlist_test.db", Context.MODE_PRIVATE, null);

        Cursor c = db.rawQuery("SELECT * FROM sch WHERE year='"+year+"' AND month='"+month+"' AND day='"+day+"'", null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            dialItemArr.add(new DialItemData(c.getString(1)));
            c.moveToNext();
        }
        c.close();

        db.close();
    }

    public static void deleteData(String name, String[] field, String value[]) {
//        String sql = "DELETE FROM sch WHERE year='"+year+"' AND month='"+month+"' AND day='"+day+"'";
        SQLiteDatabase db = con.openOrCreateDatabase("sqlist_test.db", Context.MODE_PRIVATE, null);
        String fieldStr = "";
        for (int i = 0; i < field.length; i++) {
            fieldStr +=" "+field[i]+"='"+value[i]+"' AND";
        }
        fieldStr = fieldStr.substring(0, fieldStr.length() - 4);

        String sql = "DELETE FROM "+name+" WHERE "+fieldStr;
        Log.d("aabb",sql);
        db.execSQL(sql);
        db.close();
    }

    public static void insertData(String name, String[] field, String value[]) {
        SQLiteDatabase db = con.openOrCreateDatabase("sqlist_test.db", Context.MODE_PRIVATE, null);
        String fieldStr = "";
        for (String temp : field) {
            fieldStr += temp + ",";
        }
        fieldStr = fieldStr.substring(0, fieldStr.length() - 1);

        String valueStr = "";
        for (String temp : value) {
            valueStr += "'" + temp + "',";
        }
        valueStr = valueStr.substring(0, valueStr.length() - 1);

        String sql = "INSERT INTO " + name + " (" + fieldStr + ") VALUES (" + valueStr + ")";
        Log.d("aabb",sql);
        db.execSQL(sql);
        db.close();
    }
}
```