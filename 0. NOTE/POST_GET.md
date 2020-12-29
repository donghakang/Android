# Post, Get 방식 


### Post
```java
    /** post **/
    RequestQueue stringRequest = Volley.newRequestQueue(this);
    String url = "http://192.0.0.0:8080/gather/page.do";

    StringRequest myReq = new StringRequest(Request.Method.POST, url,
            successListener, errorListener) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            params.put("id", "bbbb");
            params.put("pw", "11111");
            params.put("name", "한글되냐?");
            params.put("phone", "0101010");
            return params;
        }
    };
    myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f)
    );
    stringRequest.add(myReq);
```

### GET
```java

  String url = "http://link.co.kr/delete/data.php?"+id+"&"+pw+"&";
               //192.0.0.0:8080/gather/data.do?id=aaa&pw=1234

  RequestQueue requestQueue = Volley.newRequestQueue(this);
  StringRequest myReq = new StringRequest(Request.Method.GET, url, successListener, errorListener);
  requestQueue.add(myReq);

```
