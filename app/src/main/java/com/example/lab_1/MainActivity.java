package com.example.lab_1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView question = findViewById(R.id.question);
//        TextView answer = findViewById(R.id.flashcard_answer);
        Button changeQuestion = findViewById(R.id.change_question);
        initializeCards();
        changeQuestion();

//        question.setOnClickListener(v -> {
//            question.setVisibility(View.GONE);
////            answer.setVisibility(View.VISIBLE);
//        });

//        answer.setOnClickListener(v -> {
//            answer.setVisibility(View.GONE);
//            question.setVisibility(View.VISIBLE);
//        });

        changeQuestion.setOnClickListener(v -> {
            changeQuestion();
            if(questionIndex > data.length - 1){
                questionIndex = 0;
            }
//            Toast.makeText(this , ""+index , Toast.LENGTH_SHORT).show();
        });
    }

    public TextView cards[] = new TextView[4];
    public String data[][] = {{"Who is the 44th president of the US?","Barack Obama","Bill Clinton","George H.W. Bush"," },
                              {"When was the Declaration of Independence signed?","1790","1776","1783","1"},
                              {"When did WWII end?","1942","1947","1945","2"}};
    private int questionIndex = 0;

    public void initializeCards() {
        String types[] = new String[]{"question" , "answer1" , "answer2" , "answer3"};
        for(int i = 0;i < 4; i++){
            int resID = getResources().getIdentifier(types[i], "id", getPackageName());
            cards[i] = findViewById(resID);
        }
    }

    public void changeQuestion(){
        for(int i=0;i<data[questionIndex].length - 1;i++){
            int finalI = i;
            cards[finalI].setText(data[questionIndex][finalI]);
            if(finalI>0) {
                cards[finalI].setBackground(getDrawable(R.drawable.answer_card));
                cards[finalI].setOnClickListener(v -> {
                    if (finalI == Integer.parseInt(data[questionIndex][data[questionIndex].length - 1])) {
                        Toast.makeText(this , ""+finalI, Toast.LENGTH_SHORT).show();
                        cards[finalI].setBackground(getDrawable(R.drawable.right_answer));
                    } else {
                        cards[finalI].setBackground(getDrawable(R.drawable.wrong_answer));
                        cards[Integer.parseInt(data[questionIndex][data[questionIndex].length - 1])].setBackground(getDrawable(R.drawable.right_answer));
                    }
                });
            }
        }
        questionIndex += 1;
    }
}