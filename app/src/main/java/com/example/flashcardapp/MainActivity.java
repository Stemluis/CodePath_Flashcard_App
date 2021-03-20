package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {
    // For logging purposes
    private static final String TAG = "MyActivity";

    int questionIndex = 0;

    Boolean rand;

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardDatabase = new FlashcardDatabase(this);
        flashcardDatabase.initFirstCard();

        questionIndex = getIntent().getIntExtra("index", 0);

        updateElements();
        buttons();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                questionIndex = data.getIntExtra("index",0);
                Snackbar.make(findViewById(R.id.answers), "Card created/edited successfully!", Snackbar.LENGTH_SHORT).show();
                updateElements();
            } else if (resultCode == 2) {
                if(allFlashcards.size()>1) {
                    if (questionIndex == 0)
                        questionIndex = allFlashcards.size() - 1;
                    else
                        questionIndex -= 1;
                    updateElements();
                }
                Snackbar.make(findViewById(R.id.answers), "Card deleted successfully!", Snackbar.LENGTH_SHORT).show();
                updateElements();
            }
        }
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
                flashcardDatabase.insertCard(new Flashcard(text[0],text[1],text[2],text[3],text[4]));
            }
            myReader.close();
        } catch (IOException e) {
        }
    }

    public void updateElements(){
        // Array of textview elements containing question and all answers
        TextView textElements[] = new TextView[5];

        allFlashcards = flashcardDatabase.getAllCards();

        // Creates a string containing all the answers from the flashcard to make programming textviews easier
        String[] answers = {allFlashcards.get(questionIndex).getAnswer(),
                            allFlashcards.get(questionIndex).getWrongAnswer1(),
                            allFlashcards.get(questionIndex).getWrongAnswer2(),
                            allFlashcards.get(questionIndex).getWrongAnswer3()};

        // Sets the elements of textview array to a corresponding question/answer.
        for(int i=0;i<5;i++) {
            if (i == 0)
                textElements[i] = findViewById(R.id.question);
            else {
                textElements[i] = findViewById(getResources().getIdentifier("answer" + i, "id", getPackageName()));
                textElements[i].setBackground(getDrawable(R.drawable.answer));
            }
        }

        // The function sorts the answers in a random order
        answers = randomizeAnswers(answers);

        // The function sorts the questions in a random order
//        if(rand) {
//            randomizeQuestions();
//            rand = !rand;
//        }

        // Allows user to toggle between the question and answer
        textElements[0].setText(allFlashcards.get(questionIndex).getQuestion());
        AtomicReference<Boolean> e = new AtomicReference<>(true);
        textElements[0].setOnClickListener(v -> {
            e.set(!e.get());
            if(e.get()) {
                textElements[0].setBackground(getDrawable(R.drawable.question));
                textElements[0].setText(allFlashcards.get(questionIndex).getQuestion());
            }else{
                textElements[0].setBackground(getDrawable(R.drawable.question_answer));
                textElements[0].setText(allFlashcards.get(questionIndex).getAnswer());
            }
        });


        // Finds which card is the right answer and sets the onClickListener functions accordingly
        int rightAnswer = - 1;
        for(int i=0;i<answers.length;i++) {
            if(rightAnswer == -1){
                for(int j=0;j<answers.length;j++){
                    if(answers[j].equals(allFlashcards.get(questionIndex).getAnswer()))
                        rightAnswer = j;
                }
            }

            // Need to finalize these for some reason
            int finalI = i;
            int finalRightAnswer = rightAnswer;

            if(rightAnswer == i){
                textElements[i + 1].setText(answers[i]);

                textElements[i + 1].setOnClickListener(v -> {
                    textElements[finalI + 1].setBackground(getDrawable(R.drawable.answer_right));
                });
            } else {
                textElements[i + 1].setText(answers[i]);

                textElements[i + 1].setOnClickListener(v -> {
                    textElements[finalI + 1].setBackground(getDrawable(R.drawable.answer_wrong));
                    textElements[finalRightAnswer + 1].setBackground(getDrawable(R.drawable.answer_right));
                });
            }
        }
    }

    private String[] randomizeAnswers(String[] answers) {
        String[] ar = answers;
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);

            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
        return ar;
    }

    private int getRandom(int current, int max){
        Random r = ThreadLocalRandom.current();
        int s = r.nextInt(max);
        if(current == s)
            return getRandom(current, max);
        else
            return s;
    }


    public void buttons(){
        Button previous = findViewById(R.id.previous_question);
        Button edit = findViewById(R.id.edit_question);
        Button visibility = findViewById(R.id.toggle_visibility);
        Button add = findViewById(R.id.add_question);
        Button next = findViewById(R.id.next_question);

        previous.setOnClickListener(v -> {
            if(allFlashcards.size()>1) {
                if (questionIndex == 0)
                    questionIndex = allFlashcards.size() - 1;
                else
                    questionIndex -= 1;
                updateElements();
            }
        });

        next.setOnClickListener(v -> {
            if(allFlashcards.size()>1) {
                if (questionIndex == allFlashcards.size() - 1)
                    questionIndex = 0;
                else
                    questionIndex = getRandom(questionIndex, allFlashcards.size());
//                    questionIndex += 1;
                updateElements();
            }
        });

        add.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
            intent.putExtra("mode", "add");
            startActivityForResult(intent, 1);
        });

        edit.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
            intent.putExtra("mode","edit");
            intent.putExtra("index", questionIndex);
            startActivityForResult(intent, 1);
        });

        final Boolean[] e = {true};
        LinearLayout answers = findViewById(R.id.answers);
        visibility.setOnClickListener(v -> {
            e[0] = !e[0];
            if(e[0]) {
                answers.setVisibility(View.VISIBLE);
                visibility.setBackground(getDrawable(R.drawable.button_invisible));
            }else{
                answers.setVisibility(View.INVISIBLE);
                visibility.setBackground(getDrawable(R.drawable.button_visible));
            }
        });
    }
}