package fr.iutmindfuck.qcmiutlyon1.handlers;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import fr.iutmindfuck.qcmiutlyon1.data.MCQ;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;

public class MCQSQLHandler {

    private static final String MCQ_TABLE = "MCQ";
    private static final String MCQ_ID = "idMCQ";
    private static final String MCQ_NAME = "name";
    private static final String MCQ_DESCRIPTION = "description";
    private static final String MCQ_TYPE = "isNegative";
    private static final String MCQ_COEFFICIENT = "coefficient";

    private SQLServices sqlServices;

    public MCQSQLHandler(SQLServices sqlServices) {
        this.sqlServices = sqlServices;
    }

    /* ********************/
    /* Database LifeCycle */
    /* ********************/


    public static String getSQLForTableCreation() {
        return "CREATE TABLE " + MCQ_TABLE + "(" +
                MCQ_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MCQ_NAME + " varchar(128), " +
                MCQ_DESCRIPTION + " varchar(256), " +
                MCQ_TYPE + " bool, " +
                MCQ_COEFFICIENT + " float)";
    }
    public static String getSQLForTableSuppression() {
        return "DROP TABLE IF EXISTS " + MCQ_TABLE;
    }


    /* ***********************/
    /* Database Manipulation */
    /* ***********************/


    public ArrayList<MCQ> getMCQs() {
        Cursor cursor = sqlServices.getData(MCQ_TABLE, null, null, null);

        return getMCQListFromCursor(cursor);
    }
    public MCQ getMCQ(int idMCQ) {
        Cursor cursor = sqlServices.getData(MCQ_TABLE, null,
                                      MCQ_ID + " = ?", new String[] {String.valueOf(idMCQ)});

        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        MCQ mcq = new MCQ(cursor.getInt(cursor.getColumnIndex(MCQ_ID)),
                          cursor.getString(cursor.getColumnIndex(MCQ_NAME)),
                          cursor.getString(cursor.getColumnIndex(MCQ_DESCRIPTION)),
                         (cursor.getInt(cursor.getColumnIndex(MCQ_TYPE)) == 1),
                          cursor.getFloat(cursor.getColumnIndex(MCQ_COEFFICIENT)));

        cursor.close();
        return mcq;
    }
    public ArrayList<MCQ> getTodoMCQ(String idStudent) {
        Cursor cursor = sqlServices.getData("SELECT * " +
                                                     "FROM MCQ m " +
                                                     "WHERE m.idMCQ NOT IN (" +
                                                        "SELECT idMCQ " +
                                                        "FROM Mark u " +
                                                        "WHERE u.idStudent = '" + idStudent + "');");
        return getMCQListFromCursor(cursor);
    }
    public ArrayList<MCQ> getDoneMCQ(String idStudent) {
        Cursor cursor = sqlServices.getData("SELECT * " +
                                                    "FROM MCQ m " +
                                                    "WHERE m.idMCQ IN (" +
                                                        "SELECT idMCQ " +
                                                        "FROM Mark u " +
                                                        "WHERE u.idStudent = '" + idStudent + "');");
        return getMCQListFromCursor(cursor);
    }
    private ArrayList<MCQ> getMCQListFromCursor(Cursor cursor) {
        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        ArrayList<MCQ> mcqList = new ArrayList<>();
        do {
            mcqList.add(new MCQ(cursor.getInt(cursor.getColumnIndex(MCQ_ID)),
                    cursor.getString(cursor.getColumnIndex(MCQ_NAME)),
                    cursor.getString(cursor.getColumnIndex(MCQ_DESCRIPTION)),
                    (cursor.getInt(cursor.getColumnIndex(MCQ_TYPE)) == 1),
                    cursor.getFloat(cursor.getColumnIndex(MCQ_COEFFICIENT))));
        }
        while(cursor.moveToNext());

        cursor.close();
        return mcqList;
    }

    public void createOrReplaceMCQ(MCQ mcq) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MCQ_ID, mcq.getId());
        contentValues.put(MCQ_NAME, mcq.getName());
        contentValues.put(MCQ_DESCRIPTION, mcq.getDescription());
        contentValues.put(MCQ_TYPE, mcq.isPointNegative());
        contentValues.put(MCQ_COEFFICIENT, mcq.getCoefficient());

        sqlServices.createOrReplaceData(MCQ_TABLE, contentValues);
    }
    public void removeMCQ(int idMCQ) {
        new QuestionSQLHandler(sqlServices).removeQuestionsFor(idMCQ);

        sqlServices.removeEntries(MCQ_TABLE, MCQ_ID + " = ?",
                new String[] {String.valueOf(idMCQ)});
    }
}
