package com.example.loginlist.control;

public class PersonalInfo {
    private static String writer;
    private static String token;

    public static void setWriter(String writer) {
        PersonalInfo.writer = writer;
    }

    public static void setToken(String token) {
        PersonalInfo.token = token;
    }

    public static String getWriter() {
        return writer;
    }

    public static String getToken() {
        return token;
    }
}
