package com.example.loginlist.Dashboard;

public class Dashboard {
    public int idx;
    private String token;
    public String title;
    public String content;
    public String wdate;
    public String writer;

    public Dashboard(int idx, String token, String title, String content, String wdate, String writer) {
        this.idx = idx;
        this.token = token;
        this.title = title;
        this.content = content;
        this.wdate = wdate;
        this.writer = writer;
    }

    public String getToken() {
        return getToken();
    }
    public void setToken(String token) {
        this.token = token;
    }


}
