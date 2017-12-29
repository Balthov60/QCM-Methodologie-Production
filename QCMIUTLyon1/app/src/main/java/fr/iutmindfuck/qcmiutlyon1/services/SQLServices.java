package fr.iutmindfuck.qcmiutlyon1.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import fr.iutmindfuck.qcmiutlyon1.handlers.AnswerSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.handlers.MCQSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.handlers.MarkSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.handlers.QuestionSQLHandler;
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

        insertTestData(db);
    }
    private void createUserLinkedTables(SQLiteDatabase db) {
        db.execSQL(UserSQLHandler.getSQLForGroupTableCreation());
        db.execSQL(UserSQLHandler.getSQLForUserTableCreation());
    }
    private void createMCQLinkedTables(SQLiteDatabase db) {
        db.execSQL(MCQSQLHandler.getSQLForTableCreation());
        db.execSQL(QuestionSQLHandler.getSQLForTableCreation());
        db.execSQL(AnswerSQLHandler.getSQLForTableCreation());
        db.execSQL(MarkSQLHandler.getSQLForTableCreation());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserSQLHandler.getSQLForGroupTableSuppression());
        db.execSQL(UserSQLHandler.getSQLForUserTableSuppression());
        db.execSQL(MCQSQLHandler.getSQLForTableSuppression());
        db.execSQL(MarkSQLHandler.getSQLForTableSuppression());
        db.execSQL(QuestionSQLHandler.getSQLForTableSuppression());
        db.execSQL(AnswerSQLHandler.getSQLForTableSuppression());

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
    public int getSizeOf(String table, String where, String whereValues[]) {
        Cursor cursor = getData(table, null, where, whereValues);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }
    public int getHighestID(String table, String idCol, String where, String whereValues[]) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(table, new String[]{idCol}, where, whereValues, null, null, idCol);

        int id;
        if (cursor.moveToLast()) {
            id = cursor.getInt(cursor.getColumnIndex(idCol)) + 1;
            Log.d("Log ", "" + id);
        }
        else {
            id = 0;
        }

        cursor.close();
        return id;
    }
    public void createOrReplaceData(String table, ContentValues contentValues) {
        SQLiteDatabase db = this.getReadableDatabase();

        db.insertWithOnConflict(table, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void removeEntries(String table, String where, String[] args) {
        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(table, where, args);
    }

    /* ****************** */
    /* SQL DATA INSERTION */
    /* ****************** */

    private void insertTestData(SQLiteDatabase db) {
        db.execSQL("INSERT INTO `User` (idUser,lastName,firstName,email,password,isTeacher,studentGroup) VALUES ('user','lastname','firstname','user@mail.com','password',0,'bg_s3g2');");
        db.execSQL("INSERT INTO `User` (idUser,lastName,firstName,email,password,isTeacher,studentGroup) VALUES ('teacher','lastname','firstname','teacher@mail.com','password',1,NULL);");
        db.execSQL("INSERT INTO `StudentGroup` (groupName) VALUES ('bg_s3g1');");
        db.execSQL("INSERT INTO `StudentGroup` (groupName) VALUES ('bg_s3g2');");
        db.execSQL("INSERT INTO `StudentGroup` (groupName) VALUES ('bg_s3g3');");
        db.execSQL("INSERT INTO `Question` (idMCQ,idQuestion,title) VALUES (1,1,'Lorsqu''un pancake tombe dans la neige avant le 31 decembre, on dit qu''il est : ');");
        db.execSQL("INSERT INTO `Question` (idMCQ,idQuestion,title) VALUES (1,2,'Lorsqu''un pancake prend l''avion à destination de Toronto et qu''il fait une escale technique à St Claude, on dit :');");
        db.execSQL("INSERT INTO `Question` (idMCQ,idQuestion,title) VALUES (1,3,'Lorsqu''un pancake est invité à une Barmitzva Les convives doivent :');");
        db.execSQL("INSERT INTO `Question` (idMCQ,idQuestion,title) VALUES (1,4,'Au cours de quel évènement historique fu créé le pancake:');");
        db.execSQL("INSERT INTO `Question` (idMCQ,idQuestion,title) VALUES (2,1,'Quelles sont les deux meilleurs matières ?');");
        db.execSQL("INSERT INTO `Question` (idMCQ,idQuestion,title) VALUES (2,2,'Qui ne trouve pas d''inspiration pour faire ce QCM de test');");
        db.execSQL("INSERT INTO `Question` (idMCQ,idQuestion,title) VALUES (2,3,'Vous voulez un whisky ?');");
        db.execSQL("INSERT INTO `Question` (idMCQ,idQuestion,title) VALUES (2,4,'Vous ne voulez pas un whisky avant ?');");
        db.execSQL("INSERT INTO `Question` (idMCQ,idQuestion,title) VALUES (2,5,'Quand je suis content je...');");
        db.execSQL("INSERT INTO `MCQ` (idMCQ,name,description,isNegative,coefficient) VALUES (1,'Qui veux gagner de l''argent en masse','D la réponse D',0,2.0);");
        db.execSQL("INSERT INTO `MCQ` (idMCQ,name,description,isNegative,coefficient) VALUES (2,'Mon super QCM','Ma fantastique description !',1,1.5);");
        db.execSQL("INSERT INTO `MCQ` (idMCQ,name,description,isNegative,coefficient) VALUES (3,'Quizz de test','pas de contenu',0,1.20000004768372);");
        db.execSQL("INSERT INTO `MCQ` (idMCQ,name,description,isNegative,coefficient) VALUES (4,'Quizz de test','test',0,1.20000004768372);");
        db.execSQL("INSERT INTO `MCQ` (idMCQ,name,description,isNegative,coefficient) VALUES (5,'Cras tempor mi ut tempus mollis.','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris fermentum fermentum enim fermentum pharetra. Nam facilisis luctus urna, quis scelerisque augue faucibus at. Phasellus odio velit, molestie et gravida ac, accumsan sed justo. Sed ornare justo gravida urna consequat, gravida mattis nunc rutrum. Nam sed auctor arcu, sed pharetra nibh. Nunc non consectetur diam.',0,1.20000004768372);");
        db.execSQL("INSERT INTO `Answer` VALUES (1,1,1,'Tombé dans la neige avant de 31 décembre',0);");
        db.execSQL("INSERT INTO `Answer` VALUES (1,1,2,'Un frizby comestible',0);");
        db.execSQL("INSERT INTO `Answer` VALUES (1,1,3,'Une kipa surgelée',1);");
        db.execSQL("INSERT INTO `Answer` VALUES (1,1,4,'D la réponse D',0);");
        db.execSQL("INSERT INTO `Answer` VALUES (1,2,1,'Qu''il n''est pas arrivé à Toronto',0);");
        db.execSQL("INSERT INTO `Answer` VALUES (1,2,2,'Qu''il était supposé arriver à Toronto...',0);");
        db.execSQL("INSERT INTO `Answer` VALUES (1,2,3,'Qu''est ce qu''il faut ce maudit pancake, tabernacle',1);");
        db.execSQL("INSERT INTO `Answer` VALUES (1,2,4,'D la réponse D',0);");
        db.execSQL("INSERT INTO `Answer` VALUES (1,3,1,'L''inciter à boire à l''Open Barmitzva',1);");
        db.execSQL("INSERT INTO `Answer` VALUES (1,3,2,'Lui présenter raymond Barmitzva',0);");
        db.execSQL("INSERT INTO `Answer` VALUES (1,3,3,'Lui offrir des Malabarmitzva',0);");
        db.execSQL("INSERT INTO `Answer` VALUES (1,3,4,'D la réponse D',0);");
        db.execSQL("INSERT INTO `Answer` VALUES (1,4,1,'En 1618, pendant la guerre des croissants au beurre',0);");
        db.execSQL("INSERT INTO `Answer` VALUES (1,4,2,'En 1702, pendant le massacre du Saint Panini',0);");
        db.execSQL("INSERT INTO `Answer` VALUES (1,4,3,'En 112, avant Céline Dion pendant la guerre de la brioche',1);");
        db.execSQL("INSERT INTO `Answer` VALUES (1,4,4,'D la réponse D',0);");
        db.execSQL("INSERT INTO `Answer` VALUES (2,1,1,'Methodologie de la production d''application',1);");
        db.execSQL("INSERT INTO `Answer` VALUES (2,1,2,'Analyse',0);");
        db.execSQL("INSERT INTO `Answer` VALUES (2,1,3,'UML',1);");
        db.execSQL("INSERT INTO `Answer` VALUES (2,1,4,'Android',0);");
        db.execSQL("INSERT INTO `Answer` VALUES (2,1,5,'Java',0);");
        db.execSQL("INSERT INTO `Answer` VALUES (2,1,6,'PHP',0);");
        db.execSQL("INSERT INTO `Answer` VALUES (2,2,1,'Mateo Rat',0);");
        db.execSQL("INSERT INTO `Answer` VALUES (2,2,2,'Lucas Secret',0);");
        db.execSQL("INSERT INTO `Answer` VALUES (2,2,3,'Balthazar Frolin',1);");
        db.execSQL("INSERT INTO `Answer` VALUES (2,2,4,'Martin Braun',0);");
        db.execSQL("INSERT INTO `Answer` VALUES (2,3,1,'Oh Juste un doigt',1);");
        db.execSQL("INSERT INTO `Answer` VALUES (2,3,2,'Avez plaisir',0);");
        db.execSQL("INSERT INTO `Answer` VALUES (2,3,3,'Non merci',0);");
        db.execSQL("INSERT INTO `Answer` VALUES (2,3,4,'Je préfère des gencives de porc',0);");
        db.execSQL("INSERT INTO `Answer` VALUES (2,4,1,'Je vous ai demandé des gencives de porc',1);");
        db.execSQL("INSERT INTO `Answer` VALUES (2,4,2,'Sais tu danser les Cariocca ?',1);");
        db.execSQL("INSERT INTO `Answer` VALUES (2,4,3,'Prenez un chewing gum emil',1);");
        db.execSQL("INSERT INTO `Answer` VALUES (2,5,1,'mange des gencives de porc',1);");
        db.execSQL("INSERT INTO `Answer` VALUES (2,5,2,'vomis',1);");
        db.execSQL("INSERT INTO `Answer` VALUES (2,5,3,'suis content',1);");
        db.execSQL("INSERT INTO `Answer` VALUES (2,5,4,'tousse',1);");
        db.execSQL("INSERT INTO `Answer` VALUES (2,5,5,'saute',1);");
        db.execSQL("INSERT INTO `Answer` VALUES (2,5,6,'souris',1);");
    }
}
