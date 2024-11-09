package com.example.flash_card_application;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEditFlashcardActivity extends AppCompatActivity {

    private EditText questionInput;
    private EditText answerInput;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_flashcard);

        questionInput = findViewById(R.id.questionInput);
        answerInput = findViewById(R.id.answerInput);
        database = FirebaseDatabase.getInstance().getReference("flashcards");

        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = questionInput.getText().toString().trim();
                String answer = answerInput.getText().toString().trim();

                if (!question.isEmpty() && !answer.isEmpty()) {
                    String id = database.push().getKey();
                    Flashcard flashcard = new Flashcard(id, question, answer);
                    database.child(id).setValue(flashcard);
                    finish();
                }
            }
        });
    }
}
