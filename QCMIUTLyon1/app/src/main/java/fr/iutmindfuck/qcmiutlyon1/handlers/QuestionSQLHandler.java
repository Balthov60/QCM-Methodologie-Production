package fr.iutmindfuck.qcmiutlyon1.handlers;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import fr.iutmindfuck.qcmiutlyon1.data.Question;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;


public class QuestionSQLHandler {

    private static final String QUESTION_TABLE = "Question";
    private static final String QUESTION_MCQ_ID = "idMCQ";
    private static final String QUESTION_ID = "idQuestion";
    private static final String QUESTION_TITLE = "title";

    private SQLServices sqlServices;
    private AnswerSQLHandler answerSQLHandler;

    public QuestionSQLHandler(SQLServices sqlServices){
        this.sqlServices = sqlServices;
        this.answerSQLHandler = new AnswerSQLHandler(sqlServices);
    }

    public static String getSQLForTableCreation() {
        return "CREATE TABLE " + QUESTION_TABLE + "(" +
                QUESTION_MCQ_ID + " INTEGER, " +
                QUESTION_ID + " INTEGER, " +
                QUESTION_TITLE + " varchar(32), " +
                "PRIMARY KEY (" + QUESTION_MCQ_ID + ", " + QUESTION_ID + "))";
    }
    public static String getSQLForTableSuppression() {
        return "DROP TABLE IF EXISTS " + QUESTION_TABLE;
    }

    /* Data Manipulation */

    public ArrayList<Question> getQuestions(int idMCQ) {
        Cursor cursor = sqlServices.getData(QUESTION_TABLE, null,
                QUESTION_MCQ_ID + " = ?", new String[] {String.valueOf(idMCQ)});

        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        ArrayList<Question> questions = new ArrayList<>();
        do {
            int id = cursor.getInt(cursor.getColumnIndex(QUESTION_ID));

            questions.add(new Question(id, cursor.getString(cursor.getColumnIndex(QUESTION_TITLE)),
                                       answerSQLHandler.getAnswers(idMCQ, id)));
        }
        while (cursor.moveToNext());

        cursor.close();
        return questions;
    }
    public Question getQuestion(int idMCQ, int idQuestion) {
        Cursor cursor = sqlServices.getData(QUESTION_TABLE, null,
                QUESTION_MCQ_ID + " = ? AND " + QUESTION_ID +" = ? ",
                      new String[] {String.valueOf(idMCQ), String.valueOf(idQuestion)});

        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        Question question = new Question(cursor.getInt(cursor.getColumnIndex(QUESTION_ID)),
                                         cursor.getString(cursor.getColumnIndex(QUESTION_TITLE)),
                                         answerSQLHandler.getAnswers(idMCQ, idQuestion));

        cursor.close();
        return question;
    }
    public void createOrReplaceQuestion(Question question, int idMCQ) {
        ContentValues contentValues = new ContentValues();
        Integer id = question.getId();

        contentValues.put(QUESTION_MCQ_ID, idMCQ);

        if (id == null) {
            id = sqlServices.getHighestID(QUESTION_TABLE, QUESTION_ID, QUESTION_MCQ_ID + " = ?",
                                          new String[] {String.valueOf(idMCQ)});
        }

        contentValues.put(QUESTION_ID, id);
        contentValues.put(QUESTION_TITLE, question.getTitle());

        sqlServices.createOrReplaceData(QUESTION_TABLE, contentValues);

        answerSQLHandler.removeAnswersFor(idMCQ, id);

        for(int i = 0; i < question.getAnswers().size(); i++)
            answerSQLHandler.createAnswer(question.getAnswers().get(i), idMCQ, id, i);
    }

    void removeQuestionsFor(int idMCQ) {
        answerSQLHandler.removeAnswersFor(idMCQ);

        sqlServices.removeEntries(QUESTION_TABLE, QUESTION_MCQ_ID + " = ?",
                                                      new String[] {String.valueOf(idMCQ)});
    }

    public void removeQuestion(int idMCQ, int idQuestion) {
        answerSQLHandler.removeAnswersFor(idMCQ, idQuestion);

        sqlServices.removeEntries(QUESTION_TABLE,
                          QUESTION_MCQ_ID + " = ? AND " + QUESTION_ID + " = ?",
                                new String[] {String.valueOf(idMCQ), String.valueOf(idQuestion)});
    }


}
