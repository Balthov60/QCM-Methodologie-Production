package fr.iutmindfuck.qcmiutlyon1.handlers;

import android.content.ContentValues;

import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;

public class UserSQLHandler {

    private static final String USER_TABLE = "User";
    private static final String USER_ID = "idUser";
    private static final String USER_LASTNAME = "lastName";
    private static final String USER_FIRSTNAME = "firstName";
    private static final String USER_EMAIL = "email";
    private static final String USER_PASSWORD = "password";
    private static final String USER_IS_TEACHER = "isTeacher";

    private SQLServices sqlServices;

    public UserSQLHandler(SQLServices sqlServices) {
        this.sqlServices = sqlServices;
    }

    public static String getSQLForTableCreation() {
        return "CREATE TABLE " + USER_TABLE + "(" +
                USER_ID + " int(8) PRIMARY KEY, " +
                USER_LASTNAME + " varchar(32), " +
                USER_FIRSTNAME + " varchar(32), " +
                USER_EMAIL + " varchar(256), " +
                USER_PASSWORD + " varchar(64), " +
                USER_IS_TEACHER + " bool)";

    }
    public static String getSQLForTableSupression() {
        return "DROP TABLE IF EXISTS " + USER_TABLE;
    }

    public boolean isPasswordCorrectFor(String id, String password) {
        return sqlServices.isResultsMatching(USER_TABLE, new String[]{USER_ID},
                USER_ID + " = ? AND " + USER_PASSWORD + " = ?",
                new String[]{id, password});
    }
    public boolean isTeacher(String id) {
        return sqlServices.isResultsMatching(USER_TABLE, new String[]{USER_ID},
                USER_ID + " = ? AND " + USER_IS_TEACHER + " = ?",
                new String[]{id, "1"});
    }

    // Test Methodes For DB Insertion (will not exist in final version)
    public static ContentValues getUserDBEntry() {
        ContentValues user = new ContentValues();
        user.put(USER_ID, "user");
        user.put(USER_LASTNAME, "lastname");
        user.put(USER_FIRSTNAME, "firstname");
        user.put(USER_EMAIL, "user@mail.com");
        user.put(USER_PASSWORD, "password");
        user.put(USER_IS_TEACHER, false);

        return user;
    }
    public static ContentValues getTeacherDBEntry() {
        ContentValues teacher = new ContentValues();
        teacher.put(USER_ID, "teacher");
        teacher.put(USER_LASTNAME, "lastname");
        teacher.put(USER_FIRSTNAME, "firstname");
        teacher.put(USER_EMAIL, "teacher@mail.com");
        teacher.put(USER_PASSWORD, "password");
        teacher.put(USER_IS_TEACHER, true);

        return teacher;
    }
}
