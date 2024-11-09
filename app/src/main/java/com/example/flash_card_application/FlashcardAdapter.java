package com.example.flash_card_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FlashcardAdapter extends RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder> {

    private List<Flashcard> flashcardList;
    private OnItemClickListener listener;
    private OnEditClickListener editClickListener;
    private OnDeleteClickListener deleteClickListener;
    private Context context;

    public FlashcardAdapter(Context context, List<Flashcard> flashcardList, OnItemClickListener listener,
                            OnEditClickListener editClickListener, OnDeleteClickListener deleteClickListener) {
        this.context = context;
        this.flashcardList = flashcardList;
        this.listener = listener;
        this.editClickListener = editClickListener;
        this.deleteClickListener = deleteClickListener;
    }

    @NonNull
    @Override
    public FlashcardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_flashcard, parent, false);
        return new FlashcardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlashcardViewHolder holder, int position) {
        Flashcard flashcard = flashcardList.get(position);
        holder.questionText.setText(flashcard.getQuestion());
        holder.answerText.setText(flashcard.getAnswer());

        // Set item click listener for the entire flashcard
        holder.itemView.setOnClickListener(v -> listener.onItemClick(flashcard));

        // Set edit button click listener
        holder.editButton.setOnClickListener(v -> editClickListener.onEditClick(flashcard));

        // Set delete button click listener
        holder.deleteButton.setOnClickListener(v -> deleteClickListener.onDeleteClick(flashcard, position));
    }

    @Override
    public int getItemCount() {
        return flashcardList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Flashcard flashcard);
    }

    public interface OnEditClickListener {
        void onEditClick(Flashcard flashcard);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(Flashcard flashcard, int position);
    }

    static class FlashcardViewHolder extends RecyclerView.ViewHolder {
        TextView questionText, answerText;
        Button editButton, deleteButton;

        public FlashcardViewHolder(@NonNull View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.questionText);
            answerText = itemView.findViewById(R.id.answerText);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
