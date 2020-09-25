package com.example.loginlist.Dashboard;

public class Reply {
    public int idx;
    public String content;
    public String writer;
    public String mine;
    public String date;

    public Reply(int idx, String content, String writer, String mine, String date) {
        this.idx = idx;
        this.content = content;
        this.writer = writer;
        this.mine = mine;
        this.date = date;
    }
}
