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

