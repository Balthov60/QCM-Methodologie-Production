package fr.iutmindfuck.qcmiutlyon1.handlers;

import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;

public class QuestionSQLHandler {

    private static final String QUESTION_TABLE = "Question";
    private static final String QUESTION_ID = "idQuestion";
    private static final String QUESTION_NAME = "name";
    private static final String QUESTION_IDMCQ = "idMCQ";

    private SQLServices sqlServices;

    public QuestionSQLHandler(SQLServices sqlServices){
        this.sqlServices = sqlServices;
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
/*
    public Question getQuestion(int idMCQ, int idQuestion);

    public boolean setQuestion(String name, int idMCQ);
*/

}
