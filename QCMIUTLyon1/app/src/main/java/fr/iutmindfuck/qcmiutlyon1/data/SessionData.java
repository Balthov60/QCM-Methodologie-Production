package fr.iutmindfuck.qcmiutlyon1.data;

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
    private String userID;

    /**
     * Used to save the current answer of a user for a specific question
     *
     * Format :
     *   Key   : <MCQ_ID>_<QUESTION_ID>
     *   Value : List of answer index that have been marked as true (with '_' separator)
     */
    private HashMap<String, ArrayList<Boolean>> mcqUserAnswerSave = new HashMap<>();

    public boolean isTeacher() {
        return isTeacher;
    }
    public String getUserID() {
        return userID;
    }


    public static void createNewSession(String userID, boolean isTeacher) {
        ourInstance.userID = userID;
        ourInstance.isTeacher = isTeacher;
        //ourInstance.mcqUserAnswerSave = new HashMap<>();
    }

    public void saveAnswers(int idMCQ, int idQuestion,
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

    public boolean isQuestionAnswered(int idMCQ, int idQuestion) {
        return ourInstance.mcqUserAnswerSave.containsKey(idMCQ + "_" + idQuestion);
    }
    public ArrayList<Boolean> getAnswersStatus(int idMCQ, int idQuestion) {
        return ourInstance.mcqUserAnswerSave.get(idMCQ + "_" + idQuestion);
    }

    private ArrayList<Boolean> formatAnswersID(ArrayList<Integer> trueAnswersID, int size) {
        ArrayList<Boolean> formattedAnswersID = new ArrayList<>();

        for (int i = 0; i < size; i++)
            formattedAnswersID.add(trueAnswersID.contains(i));

        return formattedAnswersID;
    }
}
