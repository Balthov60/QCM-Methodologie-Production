package fr.iutmindfuck.qcmiutlyon1.data;


import java.util.ArrayList;

import fr.iutmindfuck.qcmiutlyon1.handlers.MCQSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.handlers.MarkSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.handlers.QuestionSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;

public class MCQCorrectionReport {

    private MCQ mcq;
    private ArrayList<Question> questions;

    private ArrayList<ArrayList<Boolean>> userAnswers;

    private float markOutOf20;
    private float mark;
    private int maxMark;

    public MCQCorrectionReport(int idMCQ, SQLServices sqlServices) {
        MCQSQLHandler mcqsqlHandler = new MCQSQLHandler(sqlServices);
        mcq = mcqsqlHandler.getMCQ(idMCQ);

        QuestionSQLHandler questionSQLHandler = new QuestionSQLHandler(sqlServices);
        questions = questionSQLHandler.getQuestions(idMCQ);

        userAnswers = new ArrayList<>();
        for (Question question : questions) {
            userAnswers.add(SessionData.getAnswersStatus(idMCQ, question.getId()));
        }

        mark = 0;
        maxMark = questions.size();

        correct();
    }

    private void correct() {
        for (int i = 0; i < questions.size(); i++) {
            if (!userAnswersToQuestion(userAnswers.get(i)))
                continue;

            if (isUserRight(questions.get(i).getAnswers(), userAnswers.get(i)))
            {
                mark++;
            }
            else
            {
                mark -= (mcq.isPointNegative()) ? 1 : 0;
            }
        }
        markOutOf20 = mark / maxMark * 20;
    }

    /**
     * Check is user five answer for a specific question.
     *
     * @param userAnswers the user's answer for a specific question.
     * @return true if userAnswers is not null and not empty, false elsewhere.
     */
    private boolean userAnswersToQuestion(ArrayList<Boolean> userAnswers) {
        return (userAnswers != null && !userAnswers.isEmpty());
    }

    /**
     * Check if user choice match the question answers.
     *
     * @param answers all answers for a mcq's question.
     * @param userAnswer the user's answer for this question.
     * @return true if the userAnswer and questionAnswers match false else.
     */
    private boolean isUserRight(ArrayList<Answer> answers, ArrayList<Boolean> userAnswer) {
        for (int i = 0; i < answers.size(); i++) {
            if (!isUserAnswerRight(answers.get(i).isRight(), userAnswer.get(i)))
            {
                return false;
            }
        }
        return true;
    }
    /**
     * Check if user choice match the answer.
     *
     * @param isAnswerRight true if answer is right & false if not.
     * @param userAnswer true if user had select this answer & false if not.
     * @return boolean (True if param boolean match false else).
     */
    private boolean isUserAnswerRight(boolean isAnswerRight, boolean userAnswer) {
        return (isAnswerRight && userAnswer) || (!isAnswerRight && !userAnswer);
    }

    public void saveMark(SQLServices sqlServices) {
        MarkSQLHandler markSQLHandler = new MarkSQLHandler(sqlServices);

        markSQLHandler.addMark(new Mark(mcq.getId(),
                               SessionData.getInstance().getUserID(),
                               markOutOf20));
    }

    public void exportInJson() {
    }

    public void displayPopUp() {
    }
}
