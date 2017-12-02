package fr.iutmindfuck.qcmiutlyon1.handlers;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import fr.iutmindfuck.qcmiutlyon1.data.Answer;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;

public class AnswerSQLHandler {

    private static final String ANSWER_TABLE = "Answer";
    private static final String ANSWER_ID = "idAnswer";
    private static final String ANSWER_TITLE = "title";
    private static final String ANSWER_ISRIGHT = "Right/Wrong";
    private static final String ANSWER_IDQUESTION = "idQuestion";

    private SQLServices sqlServices;

    public AnswerSQLHandler(SQLServices sqlServices){
        this.sqlServices = sqlServices;
    }

    public static String getSQLForTableCreation() {
        return "CREATE TABLE " + ANSWER_TABLE + "(" +
                ANSWER_ID + " int(8) PRIMARY KEY, " +
                ANSWER_TITLE + " varchar(32), " +
                ANSWER_ISRIGHT + " bool " +
                ANSWER_IDQUESTION + " int(8))";
    }
    public static String getSQLForTableSupression() {
        return "DROP TABLE IF EXISTS " + ANSWER_TABLE;
    }

    //fonction qui recupere la liste de reponse ayant le idQuestion demander
    //et qui ajoute chacun de leurs id dans une arrayList
    public ArrayList<String> getIdAnswerForAQuestion(String idQuestion){
        ArrayList<String> listOfAnswer = new ArrayList();

        Cursor cursor = sqlServices.getData(ANSWER_TABLE, null,
                ANSWER_IDQUESTION + " = ?", new String[] {idQuestion});

        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        for(int i = 0; i < cursor.getCount(); i++){
            listOfAnswer.add(cursor.getString(cursor.getPosition()));
            cursor.moveToNext();
        }

        return listOfAnswer;
    }

    public Answer getAnswer(String idAnswer){
        Cursor cursor = sqlServices.getData(ANSWER_TABLE, null,
                ANSWER_ID + " = ?", new String[] {idAnswer});

        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        //Il n'exite pas de boolean avec SQLite, on est obligé de passer par un int
        boolean isRight = cursor.getInt(cursor.getColumnIndex(ANSWER_ISRIGHT)) == 1;

        Answer answer = new Answer(cursor.getString(cursor.getColumnIndex(ANSWER_ID)),
                        cursor.getString(cursor.getColumnIndex(ANSWER_TITLE)),
                        isRight,
                        cursor.getString(cursor.getColumnIndex(ANSWER_IDQUESTION)));

        cursor.close();
        return answer;
    }

    public void CreateOrReplaceAnswer(Answer answer){

        ContentValues contentValues = new ContentValues();

        contentValues.put(ANSWER_ID, answer.getId());
        contentValues.put(ANSWER_TITLE, answer.getTitle());
        contentValues.put(ANSWER_ISRIGHT, answer.isRight());
        contentValues.put(ANSWER_IDQUESTION, answer.getIdQuestion());

        sqlServices.createOrReplaceData(ANSWER_TABLE, contentValues);
    }


}
