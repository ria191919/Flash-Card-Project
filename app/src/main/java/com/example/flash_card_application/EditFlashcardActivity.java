package com.example.flash_card_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditFlashcardActivity extends AppCompatActivity {

    private EditText editQuestion, editAnswer;
    private Button saveButton;
    private Flashcard flashcard;
    private int flashcardPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_flashcard);

        // Initialize the views
        editQuestion = findViewById(R.id.editQuestion);
        editAnswer = findViewById(R.id.editAnswer);
        saveButton = findViewById(R.id.saveButton);

        // Get the passed data (flashcard question, answer, and position)
        flashcard = (Flashcard) getIntent().getSerializableExtra("flashcard");
        flashcardPosition = getIntent().getIntExtra("position", -1);

        // Check if flashcard is null
        if (flashcard == null) {
            Toast.makeText(this, "Error: Flashcard data not found!", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if data is missing
            return;
        }

        // Set the EditText fields to the current flashcard values
        editQuestion.setText(flashcard.getQuestion());
        editAnswer.setText(flashcard.getAnswer());

        // Set the save button click listener
        saveButton.setOnClickListener(v -> {
            String updatedQuestion = editQuestion.getText().toString().trim();
            String updatedAnswer = editAnswer.getText().toString().trim();

            if (!updatedQuestion.isEmpty() && !updatedAnswer.isEmpty()) {
                // Update the flashcard with new data using setter methods
                flashcard.setQuestion(updatedQuestion);
                flashcard.setAnswer(updatedAnswer);

                // Return the updated flashcard to MainActivity
                Intent returnIntent = new Intent();
                returnIntent.putExtra("flashcard", flashcard);
                returnIntent.putExtra("position", flashcardPosition);
                setResult(RESULT_OK, returnIntent);  // Set result back to MainActivity
                Toast.makeText(EditFlashcardActivity.this, "Flashcard updated successfully!", Toast.LENGTH_SHORT).show();

                // Return to MainActivity
                finish();
            } else {
                Toast.makeText(EditFlashcardActivity.this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
