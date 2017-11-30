package fr.iutmindfuck.qcmiutlyon1.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import fr.iutmindfuck.qcmiutlyon1.handlers.MCQSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.handlers.UserSQLHandler;

public class SQLServices extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "IUT-BG-QCM";

    public SQLServices(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /* *********************** */
    /* DB creation/suppression */
    /* *********************** */

    @Override
    public void onCreate(SQLiteDatabase db) {
        createUserLinkedTables(db);
        createMCQLinkedTables(db);
    }
    private void createUserLinkedTables(SQLiteDatabase db) {
        db.execSQL(UserSQLHandler.getSQLForGroupTableCreation());
        db.execSQL(UserSQLHandler.getSQLForUserTableCreation());

        insertTestData(db);
    }
    private void createMCQLinkedTables(SQLiteDatabase db) {
        db.execSQL(MCQSQLHandler.getSQLForMCQTableCreation());

        insertTestData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserSQLHandler.getSQLForGroupTableSuppression());
        db.execSQL(UserSQLHandler.getSQLForUserTableSuppression());
        db.execSQL(MCQSQLHandler.getSQLForMCQTableSuppression());

        onCreate(db);
    }

    /* ***************** */
    /* Data manipulation */
    /* ***************** */

    public Cursor getData(String table, String select[], String where, String whereValues[]) {
        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(table, select,
                where, whereValues,
                null, null, null);
    }
    public boolean isResultsMatching(String table, String[] select,
                                     String where, String[] whereValues) {
        Cursor cursor = getData(table, select, where, whereValues);

        boolean isResult = cursor.moveToFirst();
        cursor.close();

        return isResult;
    }

    public void createOrReplaceData(String table, ContentValues contentValues) {
        SQLiteDatabase db = this.getReadableDatabase();

        db.insertWithOnConflict(table, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    /* ****************** */
    /* SQL DATA INSERTION */
    /* ****************** */

    private void insertTestData(SQLiteDatabase db) {
        // Insert Basic Group for test (will not exists in final version)

        ContentValues group[] = UserSQLHandler.getGroupsDBEntry();

        db.insertWithOnConflict("Group", null, group[0], SQLiteDatabase.CONFLICT_REPLACE);
        db.insertWithOnConflict("Group", null, group[1], SQLiteDatabase.CONFLICT_REPLACE);
        db.insertWithOnConflict("Group", null, group[2], SQLiteDatabase.CONFLICT_REPLACE);

        // Insert Basic ID for test (will not exists in final version)
        ContentValues user = UserSQLHandler.getUserDBEntry();
        ContentValues teacher = UserSQLHandler.getTeacherDBEntry();

        db.insertWithOnConflict("User", null, user, SQLiteDatabase.CONFLICT_REPLACE);
        db.insertWithOnConflict("User", null, teacher, SQLiteDatabase.CONFLICT_REPLACE);
    }
}
