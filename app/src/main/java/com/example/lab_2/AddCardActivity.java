package com.example.lab_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class AddCardActivity extends AppCompatActivity{
    private static final String TAG = "AddCardActivity";
    EditText inputs[] = new EditText[5];
    String inputArray[] =  new String[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_question);

        Button save = findViewById(R.id.save);
        Button close = findViewById(R.id.close);

        save.setOnClickListener(v -> {
            submitData();
        });

        close.setOnClickListener(v -> {
            Intent intent = new Intent(AddCardActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    public void submitData() {
        for(int i=0;i<5;i++) {
            if (i == 0) {
                inputs[i] = findViewById(R.id.input_question);
            } else {
                inputs[i] = findViewById(getResources().getIdentifier("input_answer" + i, "id", getPackageName()));
            }
            inputArray[i] = inputs[i].getText().toString();
        }
        Intent intent = new Intent(AddCardActivity.this, MainActivity.class);
        intent.putExtra("data", inputArray);
        startActivity(intent);
    }
}