package com.example.flash_card_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FlashcardAdapter adapter;
    private List<Flashcard> flashcardList;
    private FloatingActionButton fabAddFlashcard;  // Add FAB reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the flashcard list with some sample data
        flashcardList = new ArrayList<>();
        flashcardList.add(new Flashcard("1", "What is Android?", "An operating system for mobile devices"));
        flashcardList.add(new Flashcard("2", "What is Firebase?", "A platform for developing mobile and web apps"));

        // Initialize the adapter with listeners for edit and delete
        adapter = new FlashcardAdapter(this, flashcardList, flashcard -> {
            // Handle item click (e.g., open FlashcardViewActivity)
            Intent intent = new Intent(MainActivity.this, FlashcardViewActivity.class);
            intent.putExtra("flashcard", flashcard);  // Pass the entire flashcard object
            startActivity(intent);
        }, this::onEditClick, this::onDeleteClick);

        recyclerView.setAdapter(adapter);

        // Initialize the FAB and set its click listener
        fabAddFlashcard = findViewById(R.id.fabAddFlashcard); // Get reference to the FAB
        fabAddFlashcard.setOnClickListener(v -> {
            // Start AddFlashcardActivity to add a new flashcard
            Intent intent = new Intent(MainActivity.this, AddFlashcardActivity.class);
            startActivityForResult(intent, 2);  // Start for result to receive the added flashcard
        });

        // Button to show a random flashcard
        Button buttonShowRandomFlashcard = findViewById(R.id.buttonShowRandomFlashcard);
        buttonShowRandomFlashcard.setOnClickListener(v -> onShowRandomFlashcardClick());
    }

    // Show random flashcard when the button is clicked
    private void onShowRandomFlashcardClick() {
        if (flashcardList.isEmpty()) {
            Toast.makeText(this, "No flashcards available!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Select a random flashcard
        Random random = new Random();
        int randomIndex = random.nextInt(flashcardList.size());
        Flashcard randomFlashcard = flashcardList.get(randomIndex);

        // Debugging log to check if random flashcard is selected
        System.out.println("Random flashcard selected: " + randomFlashcard.getQuestion());

        // Pass the random flashcard to FlashcardViewActivity
        Intent intent = new Intent(MainActivity.this, FlashcardViewActivity.class);
        intent.putExtra("flashcard", randomFlashcard);  // Pass the flashcard object
        startActivity(intent);  // Start the FlashcardViewActivity
    }

    private void onEditClick(Flashcard flashcard) {
        // Handle the edit button click
        Toast.makeText(this, "Edit clicked for: " + flashcard.getQuestion(), Toast.LENGTH_SHORT).show();

        // Start EditFlashcardActivity to edit the selected flashcard
        Intent intent = new Intent(MainActivity.this, EditFlashcardActivity.class);
        intent.putExtra("flashcard", flashcard);  // Pass the flashcard object
        intent.putExtra("position", flashcardList.indexOf(flashcard)); // Pass the flashcard position
        startActivityForResult(intent, 1);  // Start the activity for result to update the flashcard
    }

    private void onDeleteClick(Flashcard flashcard, int position) {
        // Handle the delete button click
        flashcardList.remove(position);
        adapter.notifyItemRemoved(position);
        Toast.makeText(this, "Deleted: " + flashcard.getQuestion(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK) {
            // Get the new flashcard from AddFlashcardActivity
            Flashcard newFlashcard = (Flashcard) data.getSerializableExtra("flashcard");

            // Add the new flashcard to the list and notify the adapter
            flashcardList.add(newFlashcard);
            adapter.notifyItemInserted(flashcardList.size() - 1);  // Notify the adapter of the new item
        } else if (requestCode == 1 && resultCode == RESULT_OK) {
            // Handle EditFlashcardActivity result (update the flashcard)
            Flashcard updatedFlashcard = (Flashcard) data.getSerializableExtra("flashcard");
            int position = data.getIntExtra("position", -1);

            // Update the flashcard list
            if (position >= 0) {
                flashcardList.set(position, updatedFlashcard);
                adapter.notifyItemChanged(position); // Notify the adapter about the update
            }
        }
    }
}
