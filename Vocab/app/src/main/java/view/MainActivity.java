package view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.vocab.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd;
    Button btnQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btn_add);
        btnQuiz = findViewById(R.id.btn_quiz);

        btnAdd.setOnClickListener(this);
        btnQuiz.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_add:
                intent = new Intent(this, view.AddActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_quiz:
                intent = new Intent(this, view.QuizAcitivty.class);
                startActivity(intent);
                break;
            default:
                Log.d("VIEW", "View Activity does not go in");
                break;
        }
    }
}