package fr.iutmindfuck.qcmiutlyon1.data;

public class Answer {

    private String title;
    private boolean isRightAnswer;

    public Answer(String title, boolean isRightAnswer) {
        this.title = title;
        this.isRightAnswer = isRightAnswer;
    }

    public String getTitle() {
        return title;
    }
    public boolean isRight() {
        return isRightAnswer;
    }
}
