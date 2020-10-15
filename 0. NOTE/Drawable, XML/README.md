#  Assets, Drawable, XML

## Description
-  **app/build/outputs/apk/debug/app-debug.apk**: After  build, apk file will be created. Apk file will run the program on the personal phone. 빌드 후, APK파일이 안드로이드 폰에 설치가 가능하다.

#### **assets**
res 폴더와 같은 위치에 두며, 음악 파일이나 웹페이지 같은 용량이 큰 데이터를 저장하는 곳으로 빌드가 되지 않음.
#### **drawable**
해상도 별로 이미지를 따로 저장을 한다. 요즘은 고해상도 이미지 하나를 같이 쓰는 추세.

|Type|
|----|
|drawable| 
|drawable-hdpi|
|~~drawable-ldpi~~|
|~~drawable-mdpi~~|
|drawable-v24|
|drawable-xhdpi|
|drawable-xxhdpi|
|drawable-xxxhdpi|
|drawable-xxxxhdpi|


#### **string.xml**
문자열을 저장하고 있는 파일로서 폴더 명을 서로 다르게 하므로 다국어 지원 처리를 할 수 가 있음. (drawable 내 이미지 파일도 폴더 분류로 다국어 처리 가능). 폴더로 분리시 핸드폰의 세팅을 가져와서 적용해줌.
```xml
<resources> 
    <string name="app_name">Resources</string>
    <string name="app_title">App Name</string>
</resources>
```
네임으로 내용을 가져와서 통일 시킬 수 있다. 
|항목|한정자|설명|예제|
|---|----|---|--|
|언어|ko,en,fr,es,zh,ja,de등|ISO 639-1에서 정의한 두글자 언어 코드|

**설정 방법**
```xml
// values-ko.xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="TEST">안녕하세요!!</string>
</resources>
```

```xml
// values-en.xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="TEST">Hello!!</string>
</resources>
```

```xml
//  layout-xml
<TextView
    android:id="@+id/textView1"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="@string/TEST" />
```


#### **color** 

각종 색깔에 대한 정보가 저장되어 있음. xml 레이아웃에서 
```@color/이름```형식으로 가져옴.

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="colorPrimary">#6200EE</color>
    <color name="colorPrimaryDark">#3700B3</color>
    <color name="colorAccent">#03DAC5</color>
</resources>
```


#### **style**
안드로이드 디자인 스타일을 세팅하는 것으로, 반복적인 코드를 줄이거나 기본 디자인을 바꿀 수 있음.
- 나인패치: 주로 말풍선에서 사용되는데 다른 사이즈들을 쉽게 구분하기 위해서 사용한다.

