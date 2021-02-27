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

        TextView question = findViewById(R.id.question);
        TextView answer =findViewById(R.id.answer);
        TextView answer1 = findViewById(R.id.answer1);
        TextView answer2 = findViewById(R.id.answer2);
        TextView answer3 = findViewById(R.id.answer3);
//        Button changeQuestion = findViewById(R.id.change_question);

        question.setOnClickListener(v -> {
            question.setVisibility(View.GONE);
            answer.setVisibility(View.VISIBLE);
            answer1.setBackground(getDrawable(R.drawable.answer_card));
            answer2.setBackground(getDrawable(R.drawable.answer_card));
            answer3.setBackground(getDrawable(R.drawable.answer_card));
        });

        answer.setOnClickListener(v -> {
            answer.setVisibility(View.GONE);
            question.setVisibility(View.VISIBLE);
        });


        answer1.setOnClickListener(v -> {
            answer1.setBackground(getDrawable(R.drawable.wrong_answer));
            answer2.setBackground(getDrawable(R.drawable.right_answer));
        });

        answer2.setOnClickListener(v -> {
            answer2.setBackground(getDrawable(R.drawable.right_answer));
        });

        answer3.setOnClickListener(v -> {
            answer3.setBackground(getDrawable(R.drawable.wrong_answer));
            answer2.setBackground(getDrawable(R.drawable.right_answer));
        });

//        changeQuestion.setOnClickListener(v -> {
//            changeQuestion();
//            if(index > data.length - 1){
//                index = 0;
//            }
//        });
    }

//    public String data[][] = {{"Who is the 44th president of the US?","Barack Obama","Bill Clinton","George H.W. Bush","1"},{"When was the Declaration of Independence signed?","1790","1776","1783","2"},{"When did WWII end?","1942","1947","1945","3"}};
//    private int index = 0;
//    TextView temp[] = new TextView[]{(TextView)findViewById(R.id.question),(TextView)findViewById(R.id.answer1),(TextView)findViewById(R.id.answer2),(TextView)findViewById(R.id.answer3)};
//
//    public void changeQuestion(){
//        for(int i=0;i<data[0].length-1;i++){
//            temp[i].setText(data[index][i]);
//        }
//        index += 1;
//    }
}