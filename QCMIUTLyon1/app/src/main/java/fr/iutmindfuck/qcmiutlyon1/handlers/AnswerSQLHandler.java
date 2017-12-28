package fr.iutmindfuck.qcmiutlyon1.handlers;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import fr.iutmindfuck.qcmiutlyon1.data.Answer;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;

public class AnswerSQLHandler {

    private static final String ANSWER_TABLE = "Answer";
    private static final String ANSWER_ID_MCQ = "idMCQ";
    private static final String ANSWER_ID_QUESTION = "idQuestion";
    private static final String ANSWER_TITLE = "title";
    private static final String ANSWER_IS_RIGHT = "isRight";

    private SQLServices sqlServices;

    protected AnswerSQLHandler(SQLServices sqlServices){
        this.sqlServices = sqlServices;
    }

    public static String getSQLForTableCreation() {
        return "CREATE TABLE " + ANSWER_TABLE + "(" +
                ANSWER_ID_MCQ + " INTEGER, " +
                ANSWER_ID_QUESTION + " INTEGER, " +
                ANSWER_TITLE + " varchar(128), " +
                ANSWER_IS_RIGHT + " bool, " +
                "PRIMARY KEY(" + ANSWER_ID_MCQ + ", " +
                                 ANSWER_ID_QUESTION + ", " +
                                 ANSWER_TITLE + ")" +
                ")";
    }
    public static String getSQLForTableSuppression() {
        return "DROP TABLE IF EXISTS " + ANSWER_TABLE;
    }

    ArrayList<Answer> getAnswers(int idMCQ, int idQuestion){
        Cursor cursor = sqlServices.getData(ANSWER_TABLE, null,
                ANSWER_ID_MCQ + " = ? AND " + ANSWER_ID_QUESTION + " = ?",
                      new String[] {String.valueOf(idMCQ), String.valueOf(idQuestion)});

        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        ArrayList<Answer> answers = new ArrayList<>();
        do {
            answers.add(new Answer(cursor.getString(cursor.getColumnIndex(ANSWER_TITLE)),
                    (cursor.getInt(cursor.getColumnIndex(ANSWER_IS_RIGHT)) == 1)));
        }
        while (cursor.moveToNext());

        cursor.close();
        return answers;
    }
    void createOrReplaceAnswer(Answer answer, int idMCQ, int idQuestion){
        ContentValues contentValues = new ContentValues();

        contentValues.put(ANSWER_ID_MCQ, idMCQ);
        contentValues.put(ANSWER_ID_QUESTION, idQuestion);
        contentValues.put(ANSWER_TITLE, answer.getTitle());
        contentValues.put(ANSWER_IS_RIGHT, answer.isRight());

        sqlServices.createOrReplaceData(ANSWER_TABLE, contentValues);
    }


    void removeAnswersFor(int idMCQ) {
        sqlServices.removeEntry(ANSWER_TABLE, ANSWER_ID_MCQ + " = ?",
                new String[] {String.valueOf(idMCQ)});
    }
}
