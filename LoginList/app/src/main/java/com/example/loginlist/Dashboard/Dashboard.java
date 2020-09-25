package com.example.loginlist.Dashboard;

public class Dashboard {
    public int idx;
    private String token;
    public String title;
    public String content;
    public String wdate;
    public String writer;
    public String count_reply;

    public Dashboard(int idx, String title, String content, String wdate, String writer, String count_reply) {
        this.idx = idx;
//        this.token = token;
//        this.setToken(token);
        this.title = title;

        this.content = content;
        this.wdate = wdate;
        this.writer = writer;
        this.count_reply = count_reply;
    }

    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }


}
