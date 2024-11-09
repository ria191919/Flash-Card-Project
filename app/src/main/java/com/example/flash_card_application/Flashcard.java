package com.example.flash_card_application;

import java.io.Serializable;

public class Flashcard implements Serializable {
    private String id;
    private String question;
    private String answer;

    public Flashcard(String id, String question, String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    // Setters
    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}


