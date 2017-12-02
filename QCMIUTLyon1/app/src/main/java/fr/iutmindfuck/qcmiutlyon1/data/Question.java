package fr.iutmindfuck.qcmiutlyon1.data;

import java.util.ArrayList;

import fr.iutmindfuck.qcmiutlyon1.data.Answer;

public class Question {

    private String id;
    private String title;
    private ArrayList<Answer> answerArray;
    private String idMCQ;

    public Question(String id, String title, ArrayList<Answer> answerArray, String idMCQ) {
        this.id = id;
        this.title = title;
        this.answerArray = answerArray;
        this.idMCQ = idMCQ;
    }

    public String getId(){ return this.id; }
    public String getTitle() { return this.title; }
    //public ArrayList<Answer> getAnswers(){ return this.answerArray; }
    public Answer getAnswer(int index){ return this.answerArray.get(index); }
    public int getNbAnswer(){ return this.answerArray.size(); }
    public String getIdMCQ(){ return this.idMCQ; }

    public void setTitle(String title) { this.title = title; }

    public void addAnswer(Answer answer){ this.answerArray.add(answer); }
    public void removeAnswer(int index){ this.answerArray.remove(index); }

    public void makeAnswerTrue(int index){ this.getAnswer(index).setRightAnswer(true); }



}
