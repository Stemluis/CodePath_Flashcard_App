package com.example.lab_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";
    TextView answers[] = new TextView[5];

    int index = 0;
    ArrayList<String[]> cards = new ArrayList<String[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String data[] = getIntent().getStringArrayExtra("data");
        if(data != null){
            cards.add(data);
        }

        importQuestions();
        updateQuestions();
        buttons();
    }

    public void importQuestions() {
        try {
            InputStream inputStream = getAssets().open("card_questions.txt");
            Scanner myReader = new Scanner(inputStream);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String text[] = new String[5];
                if(data.equals("?")){
                    for(int i=0;i<5;i++){
                        text[i] = myReader.nextLine();
//                        Log.v(TAG, text[i]);
                    }
                }
                cards.add(text);
            }
            myReader.close();
        } catch (IOException e) {
            Log.v(TAG, "*************An error occurred.****************");
            e.printStackTrace();
        }
    }

    public void updateQuestions(){
        for(int i=0;i<5;i++) {
            if (i == 0){
                answers[i] = findViewById(R.id.question);
            }else {
                answers[i] = findViewById(getResources().getIdentifier("answer" + i, "id", getPackageName()));
            }
            answers[i].setText(cards.get(index)[i]);
        }
//        index += 1;
    }

    public void buttons(){
        Button previous = findViewById(R.id.previous_question);
        Button edit = findViewById(R.id.edit_question);
        Button add = findViewById(R.id.add_question);
        Button next = findViewById(R.id.next_question);

        previous.setOnClickListener(v -> {
            if(index == 0)
                index = cards.size() - 1;
            else
                index -= 1;
            updateQuestions();
        });

        next.setOnClickListener(v -> {
            if(index == cards.size() - 1)
                index = 0;
            else
                index += 1;
            updateQuestions();
        });

        add.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
            startActivity(intent);
        });
    }
}