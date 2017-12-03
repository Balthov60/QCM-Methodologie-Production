package fr.iutmindfuck.qcmiutlyon1.handlers;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import fr.iutmindfuck.qcmiutlyon1.data.Answer;
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

        contentValues.put(QUESTION_MCQ_ID, idMCQ);
        contentValues.put(QUESTION_ID, question.getId());
        contentValues.put(QUESTION_TITLE, question.getTitle());

        sqlServices.createOrReplaceData(QUESTION_TABLE, contentValues);

        for(Answer answer : question.getAnswers())
            answerSQLHandler.createOrReplaceAnswer(answer, idMCQ, question.getId());
    }


}
