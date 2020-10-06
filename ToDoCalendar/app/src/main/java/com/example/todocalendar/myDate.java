package com.example.todocalendar;

public class myDate {
    int yy;
    int dd;
    int mm;
    String day;

    public myDate(int yy, int mm, int dd, String day) {
        this.yy = yy;
        this.dd = dd;
        this.mm = mm;
        this.day = day;
    }

    public String toString() {
        String currentDate = this.yy + "-" + this.mm + "-" + addZero(this.dd) + " (" + day + ")";

        return currentDate;
    }

    private String addZero(int dd) {
        String date = "";
        if (dd < 10) {
            date = "0" + dd;
        } else {
            date = dd + "";
        }
        return date;
    }
}
