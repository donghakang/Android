package com.example.customlistview;

public class ItemData {
    public int image;
    public String name;
    public String number;
    public String sex;
    public String email;

    public ItemData (String name, String sex, String number, String email, int image) {
        this.name = name;
        this.sex = sex;
        this.number = number;
        this.email = email;
        this.image = image;
    }
}
