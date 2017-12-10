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

    public static String getSQLForTableCreation() {
        return "CREATE TABLE " + MCQ_TABLE + "(" +
                MCQ_ID + " INTEGER PRIMARY KEY, " +
                MCQ_NAME + " varchar(128), " +
                MCQ_DESCRIPTION + " varchar(256), " +
                MCQ_TYPE + " bool, " +
                MCQ_COEFFICIENT + " float)";
    }
    public static String getSQLForTableSuppression() {
        return "DROP TABLE IF EXISTS " + MCQ_TABLE;
    }

    public ArrayList<MCQ> getMCQs() {
        Cursor cursor = sqlServices.getData(MCQ_TABLE, null, null, null);

        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        ArrayList<MCQ> mcqs = new ArrayList<>();
        do {
            mcqs.add(new MCQ(cursor.getInt(cursor.getColumnIndex(MCQ_ID)),
                             cursor.getString(cursor.getColumnIndex(MCQ_NAME)),
                             cursor.getString(cursor.getColumnIndex(MCQ_DESCRIPTION)),
                            (cursor.getInt(cursor.getColumnIndex(MCQ_TYPE)) == 1),
                             cursor.getFloat(cursor.getColumnIndex(MCQ_COEFFICIENT))));
        }
        while(cursor.moveToNext());

        cursor.close();
        return mcqs;
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

    public void createOrReplaceMCQ(MCQ mcq) {
        ContentValues contentValues = new ContentValues();
        if (sqlServices.isResultsMatching(MCQ_TABLE, null,
                MCQ_ID + " = ?", new String[] {String.valueOf(mcq.getId())})) {
            contentValues.put(MCQ_ID, mcq.getId());
        }
        else {
            contentValues.put(MCQ_ID, sqlServices.getSizeOf(MCQ_TABLE) + 1);
        }

        contentValues.put(MCQ_NAME, mcq.getName());
        contentValues.put(MCQ_DESCRIPTION, mcq.getDescription());
        contentValues.put(MCQ_TYPE, mcq.getPointNegative());
        contentValues.put(MCQ_COEFFICIENT, mcq.getCoefficient());

        sqlServices.createOrReplaceData(MCQ_TABLE, contentValues);
    }

    public void removeMCQ(int idMCQ) {
        new QuestionSQLHandler(sqlServices).removeQuestionsFor(idMCQ);

        sqlServices.removeEntry(MCQ_TABLE, MCQ_ID + " = ?",
                new String[] {String.valueOf(idMCQ)});
    }
}
