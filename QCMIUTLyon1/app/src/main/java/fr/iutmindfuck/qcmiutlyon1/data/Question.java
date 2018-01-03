package fr.iutmindfuck.qcmiutlyon1.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {

    private final Integer id;
    private final String title;
    private final ArrayList<Answer> answers;

    public Question(Integer id, String title, ArrayList<Answer> answerArray) {
        this.id = id;
        this.title = title;
        this.answers = answerArray;
    }

    public Integer getId(){ return this.id; }
    public String getTitle() { return this.title; }
    public ArrayList<Answer> getAnswers(){ return this.answers; }
    public int getAnswersQty() { return answers.size(); }
}
