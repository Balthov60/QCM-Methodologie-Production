package fr.iutmindfuck.qcmiutlyon1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserSQLServices extends SQLiteOpenHelper {

    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "IUT-BG-QCM";

    private static final String USER_TABLE = "User";
    private static final String USER_ID = "id";
    private static final String USER_LASTNAME = "lastName";
    private static final String USER_FIRSTNAME = "firstName";
    private static final String USER_EMAIL = "email";
    private static final String USER_PASSWORD = "password";
    private static final String USER_IS_TEACHER = "isTeacher";

    public UserSQLServices(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + USER_TABLE + "(" +
                USER_ID + " int(8) PRIMARY KEY, " +
                USER_LASTNAME + " varchar(32), " +
                USER_FIRSTNAME + " varchar(32), " +
                USER_EMAIL + " varchar(256), " +
                USER_PASSWORD + " varchar(64), " +
                USER_IS_TEACHER + " bool)"
        );

        // Insert Basic ID for test

        ContentValues user = new ContentValues();
        user.put(USER_ID, "user");
        user.put(USER_LASTNAME, "lastname");
        user.put(USER_FIRSTNAME, "firstname");
        user.put(USER_EMAIL, "user@mail.com");
        user.put(USER_PASSWORD, "password");
        user.put(USER_IS_TEACHER, false);

        ContentValues teacher = new ContentValues();
        teacher.put(USER_ID, "teacher");
        teacher.put(USER_LASTNAME, "lastname");
        teacher.put(USER_FIRSTNAME, "firstname");
        teacher.put(USER_EMAIL, "teacher@mail.com");
        teacher.put(USER_PASSWORD, "password");
        teacher.put(USER_IS_TEACHER, true);

        db.insertWithOnConflict(USER_TABLE, null, user, SQLiteDatabase.CONFLICT_REPLACE);
        db.insertWithOnConflict(USER_TABLE, null, teacher, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }


    public boolean isPasswordCorrectFor(String id, String password) {
        return isResultsMatching(USER_TABLE, new String[]{USER_ID},
                USER_ID + " = ? AND " + USER_PASSWORD + " = ?",
                new String[]{id, password});
    }
    public boolean isTeacher(String id) {
        return isResultsMatching(USER_TABLE, new String[]{USER_ID},
                                  USER_ID + " = ? AND " + USER_IS_TEACHER + " = ?",
                                  new String[]{id, "1"});
    }


    private boolean isResultsMatching(String table, String[] select,
                                      String where, String[] whereValues) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(table, select,
                where, whereValues,
                null, null, null);

        cursor.moveToFirst();
        boolean isResult = ! cursor.isAfterLast();
        cursor.close();

        return isResult;
    }

}
