package com.example.contacts;

public class Contact {
    public int image;
    public String name;
    public String number;
    public String sex;
    public String email;

    public Contact (String name, String sex, String number, String email, int image) {
        this.name = name;
        this.sex = sex;
        this.number = number;
        this.email = email;
        this.image = image;
    }
}
