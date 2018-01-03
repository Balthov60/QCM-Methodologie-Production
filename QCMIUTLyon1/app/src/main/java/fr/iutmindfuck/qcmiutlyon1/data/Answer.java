package fr.iutmindfuck.qcmiutlyon1.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Answer implements Serializable {

    private final String title;
    private final boolean isRightAnswer;

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

    public static boolean checkAnswersValidity(ArrayList<Answer> answers) {
        if (answers.size() < 2)
            return false;

        boolean containRightAnswer = false;
        for (Answer answer : answers) {
            if (answer.isRight())
                containRightAnswer = true;
            if (answer.getTitle().isEmpty())
                return false;
        }
        return containRightAnswer;
    }
}
