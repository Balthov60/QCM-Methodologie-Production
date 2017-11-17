package fr.iutmindfuck.qcmiutlyon1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserSQLServices extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }


    public boolean canConnect(String id, String password) {
        if(userExist(id))
            return isPasswordCorrectFor(id, password);

        return false;
    }

    private boolean isPasswordCorrectFor(String id, String password) {
        return false;
    }

    public boolean isTeacher(String id) {
        if (!userExist(id))
            return false;

        return false;
    }

    private boolean userExist(String id) {
        return false;
    }

}
