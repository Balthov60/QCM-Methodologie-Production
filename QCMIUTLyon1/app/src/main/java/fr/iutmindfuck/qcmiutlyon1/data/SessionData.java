package fr.iutmindfuck.qcmiutlyon1.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Singleton class to keep session data and share it on all activities.
 */
public class SessionData {

    /* **********************/
    /* Singleton Attributes */
    /************************/

    private static final SessionData ourInstance = new SessionData();
    public static SessionData getInstance() {
        return ourInstance;
    }

    private SessionData() {}


    /* ************************/
    /* Basic class Attributes */
    /**************************/

    private boolean isTeacher;

    /**
     * Used to save the current answer of a user for a specific question
     *
     * Format :
     *   Key   : <MCQ_ID>_<QUESTION_ID>
     *   Value : List of answer index that have been marked as true (with '_' separator)
     */
    private HashMap<String, String> mcqUserAnswerSave;


    public static void createNewSession(boolean isTeacher) {
        ourInstance.isTeacher = isTeacher;
        ourInstance.mcqUserAnswerSave = new HashMap<>();
    }

    public static void saveAnswers(int idMCQ, int idQuestion, ArrayList<Integer> trueAnswersID) {
        if (!trueAnswersID.isEmpty())
            ourInstance.mcqUserAnswerSave.put(idMCQ + "_" + idQuestion, formatAnswersID(trueAnswersID));
    }
    private static String formatAnswersID(ArrayList<Integer> trueAnswersID) {
        String formattedAnswersID = "";

        for (Integer id : trueAnswersID)
            formattedAnswersID = formattedAnswersID.concat(id + "_");

        return formattedAnswersID.substring(0, formattedAnswersID.length() - 1);
    }
}
