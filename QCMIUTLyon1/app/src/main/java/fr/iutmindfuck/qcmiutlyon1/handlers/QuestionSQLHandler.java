package fr.iutmindfuck.qcmiutlyon1.handlers;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import fr.iutmindfuck.qcmiutlyon1.data.MCQ;
import fr.iutmindfuck.qcmiutlyon1.handlers.MCQSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.data.Answer;
import fr.iutmindfuck.qcmiutlyon1.handlers.AnswerSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.data.Question;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;


public class QuestionSQLHandler {

    private static final String QUESTION_TABLE = "Question";
    private static final String QUESTION_ID = "idQuestion";
    private static final String QUESTION_NAME = "name";
    private static final String QUESTION_ANSWERS = "Answers";
    private static final String QUESTION_IDMCQ = "idMCQ";

    private SQLServices sqlServices;
    private MCQSQLHandler mcqsqlHandler;
    private AnswerSQLHandler answerSQLHandler;

    public QuestionSQLHandler(SQLServices sqlServices){
        this.sqlServices = sqlServices;
        this.mcqsqlHandler = new MCQSQLHandler(sqlServices);
        this.answerSQLHandler = new AnswerSQLHandler(sqlServices);
    }

    public static String getSQLForTableCreation() {
        return "CREATE TABLE " + QUESTION_TABLE + "(" +
                QUESTION_ID + " int(8) PRIMARY KEY, " +
                QUESTION_NAME + " varchar(32), " +
                QUESTION_IDMCQ + " int(8)) ";
    }
    public static String getSQLForTableSupression() {
        return "DROP TABLE IF EXISTS " + QUESTION_TABLE;
    }

    public Question getQuestion(String idMCQ, String idQuestion){

        MCQ mcq = this.mcqsqlHandler.getMCQ(idMCQ);

        Cursor cursor = sqlServices.getData(QUESTION_TABLE, null,
                QUESTION_ID + " = ? AND " + QUESTION_IDMCQ +" = ? ", new String[] {idQuestion, idMCQ});

        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        //On recupere les id des reponses à la question
        ArrayList<String> idAnswers = new ArrayList();
        idAnswers = this.answerSQLHandler.getIdAnswerForAQuestion(idQuestion);

        //On recupere les objets Answer grace à leur id
        ArrayList<Answer> Answers = new ArrayList();

        for(int i =0; i < idAnswers.size(); i++) {
            Answers.add(this.answerSQLHandler.getAnswer(idAnswers.get(i)));
        }

        Question question = new Question(cursor.getString(cursor.getColumnIndex(QUESTION_ID)),
                            cursor.getString(cursor.getColumnIndex(QUESTION_NAME)),
                            Answers,
                            cursor.getString(cursor.getColumnIndex(QUESTION_IDMCQ)));

        cursor.close();
        return question;
    }

    public void createOrReplaceQuestion(Question question) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(QUESTION_ID, question.getId());
        contentValues.put(QUESTION_NAME, question.getTitle());

        for(int i = 0; i < question.getNbAnswer(); i++){
            contentValues.put(QUESTION_ANSWERS, question.getAnswer(i).getId());
        }

        contentValues.put(QUESTION_IDMCQ, question.getIdMCQ());

        sqlServices.createOrReplaceData(QUESTION_TABLE, contentValues);
    }


}
