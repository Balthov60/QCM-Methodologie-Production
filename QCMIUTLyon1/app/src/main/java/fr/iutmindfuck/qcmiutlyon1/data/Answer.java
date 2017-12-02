package fr.iutmindfuck.qcmiutlyon1.data;

//Classe de test, pour pouvoir utiliser la classe Question
public class Answer {

    private String id;
    private String title;
    private boolean isRightAnswer = false;
    private String idQuestion;

    public Answer(String id, String title, boolean isRightAnswer, String idQuestion) {
        this.id = id;
        this.title = title;
        this.isRightAnswer = isRightAnswer;
        this.idQuestion = idQuestion;
    }

    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public boolean isRight() {
        return isRightAnswer;
    }
    public String getIdQuestion() {
        return idQuestion;
    }

    public void setRightAnswer(boolean rightAnswer) {
        isRightAnswer = rightAnswer;
    }

}
