package com.example.lab_1;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView question = findViewById(R.id.flashcard_question);
        TextView answer = findViewById(R.id.flashcard_answer);

        question.setOnClickListener(v -> {
            question.setVisibility(View.GONE);
            answer.setVisibility(View.VISIBLE);
        });

        answer.setOnClickListener(v -> {
            answer.setVisibility(View.GONE);
            question.setVisibility(View.VISIBLE);
        });
    }
}