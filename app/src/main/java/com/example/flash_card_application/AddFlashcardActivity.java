package com.example.flash_card_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddFlashcardActivity extends AppCompatActivity {

    private EditText editQuestion, editAnswer;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flashcard);

        editQuestion = findViewById(R.id.editQuestion);
        editAnswer = findViewById(R.id.editAnswer);
        saveButton = findViewById(R.id.saveButton);

        // Set the save button click listener
        saveButton.setOnClickListener(v -> {
            String question = editQuestion.getText().toString().trim();
            String answer = editAnswer.getText().toString().trim();

            if (!question.isEmpty() && !answer.isEmpty()) {
                // Create a new flashcard
                Flashcard newFlashcard = new Flashcard(String.valueOf(System.currentTimeMillis()), question, answer);

                // Pass the new flashcard back to MainActivity
                Intent returnIntent = new Intent();
                returnIntent.putExtra("flashcard", newFlashcard);
                setResult(RESULT_OK, returnIntent);  // Set result back to MainActivity
                Toast.makeText(AddFlashcardActivity.this, "New Flashcard added!", Toast.LENGTH_SHORT).show();

                // Finish the activity and return to MainActivity
                finish();
            } else {
                Toast.makeText(AddFlashcardActivity.this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
