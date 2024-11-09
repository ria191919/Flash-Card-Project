package com.example.flash_card_application;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FlashcardViewActivity extends AppCompatActivity {

    private TextView textViewQuestion, textViewAnswer;
    private Button buttonMarkKnown, buttonShuffle;
    private Flashcard flashcard;
    private boolean isFlipped = false;  // Track the state of the card (flipped or not)
    private DatabaseReference firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_view);

        // Initialize views
        textViewQuestion = findViewById(R.id.textViewQuestion);
        textViewAnswer = findViewById(R.id.textViewAnswer);
        buttonMarkKnown = findViewById(R.id.buttonMarkKnown);
        buttonShuffle = findViewById(R.id.buttonShuffle);

        // Retrieve the flashcard passed from MainActivity
        flashcard = (Flashcard) getIntent().getSerializableExtra("flashcard");

        if (flashcard != null) {
            textViewQuestion.setText(flashcard.getQuestion());
            textViewAnswer.setText(flashcard.getAnswer());
        } else {
            Toast.makeText(this, "No flashcard data available!", Toast.LENGTH_SHORT).show();
        }

        // Set up shuffle button (optional for a single card)
        buttonShuffle.setVisibility(View.GONE);  // Hide shuffle button if you only have one card

        // Set up mark as known button
        buttonMarkKnown.setOnClickListener(v -> markAsKnown());

        // Set up flip animation on card tap
        findViewById(R.id.cardViewFlashcard).setOnClickListener(v -> flipCard());
    }

    private void flipCard() {
        float fromRotation = isFlipped ? 0f : 90f;
        float toRotation = isFlipped ? 90f : 0f;

        ObjectAnimator questionAnimator = ObjectAnimator.ofFloat(textViewQuestion, "rotationY", fromRotation, toRotation);
        ObjectAnimator answerAnimator = ObjectAnimator.ofFloat(textViewAnswer, "rotationY", fromRotation, toRotation);
        questionAnimator.setDuration(500);
        answerAnimator.setDuration(500);

        if (isFlipped) {
            textViewAnswer.setVisibility(View.GONE);
            textViewQuestion.setVisibility(View.VISIBLE);
        } else {
            textViewAnswer.setVisibility(View.VISIBLE);
            textViewQuestion.setVisibility(View.GONE);
        }

        questionAnimator.start();
        answerAnimator.start();

        isFlipped = !isFlipped;
    }

    private void markAsKnown() {
        if (flashcard != null) {
            firebaseDatabase = FirebaseDatabase.getInstance().getReference();
            firebaseDatabase.child("flashcards").child(flashcard.getId()).child("known").setValue(true);
            Toast.makeText(this, "Marked as known!", Toast.LENGTH_SHORT).show();
        }
    }
}
