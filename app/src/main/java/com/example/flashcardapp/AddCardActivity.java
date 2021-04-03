package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class AddCardActivity extends AppCompatActivity{

    private static final String TAG = "AddCardActivity";

    EditText inputs[] = new EditText[5];

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;

    String MODE = null;
    int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_question);

        MODE = getIntent().getStringExtra("mode");
        index = getIntent().getIntExtra("index", 0);

        flashcardDatabase = new FlashcardDatabase(this);
        allFlashcards = flashcardDatabase.getAllCards();

        setup();
        buttons();
    }

    public void setup(){
        for(int i=0;i<5;i++) {
            if (i == 0) {
                inputs[i] = findViewById(R.id.input_question);
            } else {
                inputs[i] = findViewById(getResources().getIdentifier("input_answer" + i, "id", getPackageName()));
            }
        }
        if(MODE.equals("edit")){
            inputs[0].setText(allFlashcards.get(index).getQuestion());
            inputs[1].setText(allFlashcards.get(index).getAnswer());
            inputs[2].setText(allFlashcards.get(index).getWrongAnswer1());
            inputs[3].setText(allFlashcards.get(index).getWrongAnswer2());
            inputs[4].setText(allFlashcards.get(index).getWrongAnswer3());
        }
    }

    public void submitData() {

        String inputArray[] =  new String[5];
        Flashcard cardToEdit = allFlashcards.get(index);

        Intent intent = new Intent(AddCardActivity.this, MainActivity.class);

        for(int i=0;i<5;i++) {
            inputArray[i] = inputs[i].getText().toString();
        }

        if(inputArray[0].equals("") && inputArray[1].equals("")){
            Toast.makeText(this, "Missing question/right answer!", Toast.LENGTH_SHORT).show();
        }else {
            if (MODE.equals("add")) {
                flashcardDatabase.insertCard(new Flashcard(inputArray[0], inputArray[1], inputArray[2], inputArray[3], inputArray[4]));
                intent.putExtra("index", index + 1);
            } else if (MODE.equals("edit")) {
                cardToEdit.setQuestion(inputArray[0]);
                cardToEdit.setAnswer(inputArray[1]);
                cardToEdit.setWrongAnswer1(inputArray[2]);
                cardToEdit.setWrongAnswer2(inputArray[3]);
                cardToEdit.setWrongAnswer3(inputArray[4]);
                flashcardDatabase.updateCard(cardToEdit);
                intent.putExtra("index", index);
            }
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

    public void buttons(){
        Button save = findViewById(R.id.save);
        Button close = findViewById(R.id.close);
        Button delete = findViewById(R.id.delete);

        save.setOnClickListener(v -> {
            submitData();
            overridePendingTransition(R.anim.right_in,R.anim.left_out);
        });

        delete.setOnClickListener(v -> {
            if(MODE.equals("edit")) {
                flashcardDatabase.deleteCard(allFlashcards.get(index).getQuestion());
                Intent intent = new Intent(AddCardActivity.this, MainActivity.class);
                setResult(2, intent);
                finish();
            }
        });

        close.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.right_in,R.anim.left_out);
        });
    }
}