# Save Data (SharedPreference)

## Save
```JAVA
SharedPreferences pref = getSharedPreferences("mode", MODE_PRIVATE);
SharedPreferences.Editor editor = pref.edit();

editor.putString("str", str);
editor.putInt("age", 1000);
editor.commit();
```

## Load
```JAVA
String getStr = pref.getString("str", "printed sentence if there is no input");
int getInt = pref.getInt("age", -1);

showTv.setText(getStr + ", " + getInt); 
```