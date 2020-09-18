package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity {

    TextView profileName;
    TextView profileEmail;
    TextView profileNumber;
    ImageView profileImage;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        pos = getIntent().getIntExtra("position", -1);

        profileName = findViewById(R.id.profile_name);
        profileEmail = findViewById(R.id.profile_email);
        profileNumber = findViewById(R.id.profile_number);
        profileImage = findViewById(R.id.profile_image);

        setup();
    }

    private void setup() {
        Contact contact = MainActivity.arr.get(pos);

        profileName.setText(contact.name);
        profileEmail.setText(contact.email);
        profileNumber.setText(contact.number);
        profileImage.setImageResource(contact.image);
    }
}