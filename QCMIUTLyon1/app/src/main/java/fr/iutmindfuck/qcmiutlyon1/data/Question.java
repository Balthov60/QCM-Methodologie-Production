package fr.iutmindfuck.qcmiutlyon1.data;

import java.util.ArrayList;

public class Question {

    private int id;
    private String title;
    private ArrayList<Answer> answers;

    public Question(int id, String title, ArrayList<Answer> answerArray) {
        this.id = id;
        this.title = title;
        this.answers = answerArray;
    }

    public int getId(){ return this.id; }
    public String getTitle() { return this.title; }
    public ArrayList<Answer> getAnswers(){ return this.answers; }
}
