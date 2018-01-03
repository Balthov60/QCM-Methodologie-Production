package fr.iutmindfuck.qcmiutlyon1.handlers;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import fr.iutmindfuck.qcmiutlyon1.data.Mark;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;

public class MarkSQLHandler {

    private static final String MARK_TABLE = "Mark";
    private static final String MARK_ID_MCQ = "idMCQ";
    private static final String MARK_ID_STUDENT = "idStudent";
    private static final String MARK_VALUE = "value";

    private SQLServices sqlServices;

    public MarkSQLHandler(SQLServices sqlServices){ this.sqlServices = sqlServices; }

    /* *******************/
    /* Data Manipulation */
    /*********************/

    public static String getSQLForTableCreation() {
        return "CREATE TABLE " + MARK_TABLE + " (" +
                MARK_ID_MCQ + " INTEGER, " +
                MARK_ID_STUDENT + " INTEGER, " +
                MARK_VALUE + " FLOAT, " +
                "CONSTRAINT PK_Mark PRIMARY KEY (" + MARK_ID_MCQ + ", " + MARK_ID_STUDENT + "));";
    }
    public static String getSQLForTableSuppression() {
        return "DROP TABLE IF EXISTS " + MARK_TABLE;
    }

    /* *******************/
    /* Data Manipulation */
    /*********************/

    public Mark getMark(int idMCQ, String idStudent) {
        Cursor cursor = sqlServices.getData(MARK_TABLE, null,
                MARK_ID_STUDENT + " = ? AND " + MARK_ID_MCQ + " = ?",
                new String[] {idStudent, String.valueOf(idMCQ)});
        Mark mark = null;

        if (cursor.moveToFirst())
            mark = new Mark(cursor.getInt(cursor.getColumnIndex(MARK_ID_MCQ)),
                    cursor.getString(cursor.getColumnIndex(MARK_ID_STUDENT)),
                    cursor.getFloat(cursor.getColumnIndex(MARK_VALUE)));

        cursor.close();
        return mark;
    }
    private ArrayList<Mark> getAllMarksForMCQ(int idMCQ) {
        Cursor cursor = sqlServices.getData(MARK_TABLE, null,
                                      MARK_ID_MCQ + " = ?",
                                            new String[] {String.valueOf(idMCQ)});

        return getMarksFromCursor(cursor);
    }
    public float getAverageForMCQ(int idMCQ) {
        ArrayList<Mark> list = getAllMarksForMCQ(idMCQ);
        if(list == null)
            return (-1);

        float average = 0.0f;

        for(Mark mark : list)
            average += mark.getValue();

        return average / list.size();
    }


    public float getAverageForStudent(String idStudent) {
        ArrayList<Mark> marks = getAllMarksForStudent(idStudent);
        MCQSQLHandler mcqsqlHandler = new MCQSQLHandler(sqlServices);
        float totalCoeff = 0;
        if(marks == null)
            return (-1);

        float average = 0.0f;

        for(Mark mark : marks) {
            average += mark.getValue();
            totalCoeff += mcqsqlHandler.getMCQ(mark.getIdMCQ()).getCoefficient();
        }
        return average / totalCoeff;
    }
    private ArrayList<Mark> getAllMarksForStudent(String idStudent) {
        Cursor cursor = sqlServices.getData(MARK_TABLE, null,
                                      MARK_ID_STUDENT + " = ?",
                                            new String[] {idStudent});
        return getMarksFromCursor(cursor);
    }

    private ArrayList<Mark> getMarksFromCursor(Cursor cursor) {
        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        ArrayList<Mark> marks = new ArrayList<>();
        do {
            marks.add(new Mark(cursor.getInt(cursor.getColumnIndex(MARK_ID_MCQ)),
                    cursor.getString(cursor.getColumnIndex(MARK_ID_STUDENT)),
                    cursor.getFloat(cursor.getColumnIndex(MARK_VALUE))));

        }
        while(cursor.moveToNext());

        cursor.close();
        return marks;
    }

    public void addMark(Mark mark) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MARK_ID_MCQ, mark.getIdMCQ());
        contentValues.put(MARK_ID_STUDENT, mark.getUsername());
        contentValues.put(MARK_VALUE, mark.getValue());

        sqlServices.createOrReplaceData(MARK_TABLE, contentValues);
    }
}