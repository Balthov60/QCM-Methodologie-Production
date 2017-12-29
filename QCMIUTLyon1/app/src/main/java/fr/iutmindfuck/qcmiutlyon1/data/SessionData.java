package fr.iutmindfuck.qcmiutlyon1.data;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
    private HashMap<String, ArrayList<Boolean>> mcqUserAnswerSave;


    public static void createNewSession(boolean isTeacher) {
        ourInstance.isTeacher = isTeacher;
        ourInstance.mcqUserAnswerSave = new HashMap<>();
    }

    public static void saveAnswers(int idMCQ, int idQuestion,
                                   ArrayList<Integer> trueAnswersID, int answersQuantity) {
        if (!trueAnswersID.isEmpty())
        {
            ourInstance.mcqUserAnswerSave.put(idMCQ + "_" + idQuestion,
                                              formatAnswersID(trueAnswersID, answersQuantity));
        }
        else if (isQuestionAnswered(idMCQ, idQuestion))
        {
            ourInstance.mcqUserAnswerSave.remove(idMCQ + "_" + idQuestion);
        }
    }

    public static boolean isQuestionAnswered(int idMCQ, int idQuestion) {
        return ourInstance.mcqUserAnswerSave.containsKey(idMCQ + "_" + idQuestion);
    }
    public static ArrayList<Boolean> getAnswersStatus(int idMCQ, int idQuestion) {
        return ourInstance.mcqUserAnswerSave.get(idMCQ + "_" + idQuestion);
    }

    private static ArrayList<Boolean> formatAnswersID(ArrayList<Integer> trueAnswersID, int size) {
        ArrayList<Boolean> formattedAnswersID = new ArrayList<>();

        for (int i = 0; i < size; i++)
            formattedAnswersID.add(trueAnswersID.contains(i));

        return formattedAnswersID;
    }
}
