package fr.iutmindfuck.qcmiutlyon1.handlers;

import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;

public class UserSQLHandler {

    private static final String USER_TABLE = "User";
    private static final String USER_ID = "idUser";
    private static final String USER_LASTNAME = "lastName";
    private static final String USER_FIRSTNAME = "firstName";
    private static final String USER_EMAIL = "email";
    private static final String USER_PASSWORD = "password";
    private static final String USER_IS_TEACHER = "isTeacher";
    private static final String USER_GROUP = "studentGroup";

    private static final String GROUP_TABLE = "StudentGroup";
    private static final String GROUP_NAME = "groupName";
    public static final String GROUP_1 = "bg_s3g1";
    public static final String GROUP_2 = "bg_s3g2";
    public static final String GROUP_3 = "bg_s3g3";

    private SQLServices sqlServices;

    public UserSQLHandler(SQLServices sqlServices) {
        this.sqlServices = sqlServices;
    }

    public static String getSQLForUserTableCreation() {
        return "CREATE TABLE " + USER_TABLE + "(" +
                USER_ID + " int(8) PRIMARY KEY, " +
                USER_LASTNAME + " varchar(32), " +
                USER_FIRSTNAME + " varchar(32), " +
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
