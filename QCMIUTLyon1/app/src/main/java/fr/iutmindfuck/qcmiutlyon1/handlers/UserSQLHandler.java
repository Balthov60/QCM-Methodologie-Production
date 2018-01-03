package fr.iutmindfuck.qcmiutlyon1.handlers;

import android.database.Cursor;

import java.util.ArrayList;

import fr.iutmindfuck.qcmiutlyon1.data.Student;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;

public class UserSQLHandler {

    private static final String USER_TABLE = "User";
    private static final String USER_ID = "idUser";
    private static final String USER_LAST_NAME = "lastName";
    private static final String USER_FIRST_NAME = "firstName";
    private static final String USER_EMAIL = "email";
    private static final String USER_PASSWORD = "password";
    private static final String USER_IS_TEACHER = "isTeacher";
    private static final String USER_GROUP = "studentGroup";

    private static final String GROUP_TABLE = "StudentGroup";
    private static final String GROUP_NAME = "groupName";

    private final SQLServices sqlServices;

    public UserSQLHandler(SQLServices sqlServices) {
        this.sqlServices = sqlServices;
    }

    /* ********************/
    /* Database LifeCycle */
    /* ********************/

    public static String getSQLForUserTableCreation() {
        return "CREATE TABLE " + USER_TABLE + "(" +
                USER_ID + " varchar(8) PRIMARY KEY, " +
                USER_LAST_NAME + " varchar(32), " +
                USER_FIRST_NAME + " varchar(32), " +
                USER_EMAIL + " varchar(256), " +
                USER_PASSWORD + " varchar(64), " +
                USER_IS_TEACHER + " bool, " +
                USER_GROUP + " varchar(32))";

    }
    public static String getSQLForGroupTableCreation() {
        return "CREATE TABLE " + GROUP_TABLE + "(" +
                GROUP_NAME + " varchar(32) PRIMARY KEY)";
    }
    public static String getSQLForUserTableSuppression() {
        return "DROP TABLE IF EXISTS " + USER_TABLE;
    }
    public static String getSQLForGroupTableSuppression() {
        return "DROP TABLE IF EXISTS " + GROUP_TABLE;
    }

  
    /* ***********************/
    /* Database Manipulation */
    /* ***********************/


    public ArrayList<Student> getAllStudents()
    {
        Cursor cursor = sqlServices.getData(USER_TABLE, null, USER_IS_TEACHER + " = ?",
                                                                    new String[]{"0"});
        return getStudentFromCursor(cursor);
    }

    private ArrayList<Student> getStudentFromCursor(Cursor cursor)
    {
        if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        ArrayList<Student> students = new ArrayList<>();
        do {
            students.add(new Student(cursor.getString(cursor.getColumnIndex(USER_ID)),
                                     cursor.getString(cursor.getColumnIndex(USER_FIRST_NAME)),
                                     cursor.getString(cursor.getColumnIndex(USER_LAST_NAME)),
                                     cursor.getString(cursor.getColumnIndex(USER_GROUP))));

        }
        while(cursor.moveToNext());
        cursor.close();

        return students;
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

}
