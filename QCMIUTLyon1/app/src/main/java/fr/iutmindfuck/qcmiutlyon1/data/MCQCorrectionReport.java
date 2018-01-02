package fr.iutmindfuck.qcmiutlyon1.data;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.iutmindfuck.qcmiutlyon1.R;
import fr.iutmindfuck.qcmiutlyon1.activity.MCQListActivity;
import fr.iutmindfuck.qcmiutlyon1.activity.StudentPanelActivity;
import fr.iutmindfuck.qcmiutlyon1.handlers.MCQSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.handlers.MarkSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.handlers.QuestionSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.services.FileServices;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;

public class MCQCorrectionReport {

    private MCQ mcq;
    private ArrayList<Question> questions;

    private ArrayList<ArrayList<Boolean>> userAnswers;

    private float markOutOf20;
    private int mark;
    private int maxMark;

    public MCQCorrectionReport(int idMCQ, SQLServices sqlServices) {
        MCQSQLHandler mcqsqlHandler = new MCQSQLHandler(sqlServices);
        mcq = mcqsqlHandler.getMCQ(idMCQ);

        QuestionSQLHandler questionSQLHandler = new QuestionSQLHandler(sqlServices);
        questions = questionSQLHandler.getQuestions(idMCQ);
        if (questions == null)
            questions = new ArrayList<>();

        userAnswers = new ArrayList<>();
        for (Question question : questions) {
            userAnswers.add(SessionData.getInstance().getAnswersStatus(idMCQ, question.getId()));
        }

        mark = 0;
        maxMark = questions.size();

        correct();
    }

    /* *********************/
    /* Correction Handling */
    /* *********************/

    /**
     * Correct MCQ & update class Fields.
     */
    private void correct() {
        for (int i = 0; i < questions.size(); i++) {
            if (!userAnswersToQuestion(userAnswers.get(i)))
                continue;

            if (isUserRight(questions.get(i).getAnswers(), userAnswers.get(i)))
            {
                mark++;
            }
            else
            {
                mark -= (mcq.isPointNegative()) ? 1 : 0;
            }
        }
        markOutOf20 = (mark > 0) ? ((float)mark / maxMark * 20) : 0;

        if (questions.isEmpty())
            markOutOf20 = 20;
    }

    /**
     * Check is user five answer for a specific question.
     *
     * @param userAnswers the user's answer for a specific question.
     * @return true if userAnswers is not null and not empty, false elsewhere.
     */
    private boolean userAnswersToQuestion(ArrayList<Boolean> userAnswers) {
        return (userAnswers != null && !userAnswers.isEmpty());
    }

    /**
     * Check if user choice match the question answers.
     *
     * @param answers all answers for a mcq's question.
     * @param userAnswer the user's answer for this question.
     * @return true if the userAnswer and questionAnswers match false else.
     */
    private boolean isUserRight(ArrayList<Answer> answers, ArrayList<Boolean> userAnswer) {
        for (int i = 0; i < answers.size(); i++) {
            if (!isUserAnswerRight(answers.get(i).isRight(), userAnswer.get(i)))
            {
                return false;
            }
        }
        return true;
    }
    /**
     * Check if user choice match the answer.
     *
     * @param isAnswerRight true if answer is right & false if not.
     * @param userAnswer true if user had select this answer & false if not.
     * @return boolean (True if param boolean match false else).
     */
    private boolean isUserAnswerRight(boolean isAnswerRight, boolean userAnswer) {
        return (isAnswerRight && userAnswer) || (!isAnswerRight && !userAnswer);
    }


    /* *********************/
    /* Export/Save Methods */
    /* *********************/


    public void saveMark(SQLServices sqlServices) {
        MarkSQLHandler markSQLHandler = new MarkSQLHandler(sqlServices);

        markSQLHandler.addMark(new Mark(mcq.getId(),
                               SessionData.getInstance().getUserID(),
                               markOutOf20));
    }

    public void exportInJson(Context context) {
        FileServices fileServices = new FileServices(context);

        String fileName = SessionData.getInstance().getUserID() + "_" + mcq.getName();
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("mcq_id", mcq.getId());
            jsonObject.put("mark", markOutOf20);
            jsonObject.put("user_answers", getUserAnswersAsArray());
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        fileServices.saveFile(jsonObject.toString(), fileName);
    }
    private JSONArray getUserAnswersAsArray() throws JSONException {
        JSONArray jsonQuestionsArray = new JSONArray();
        JSONObject question;

        for (int i = 0; i < questions.size(); i++) {
            question = new JSONObject();
            question.put("question_id", questions.get(i).getId());
            question.put("question_user_answers", userAnswers.get(i));
            jsonQuestionsArray.put(question);
        }

        return jsonQuestionsArray;
    }


    /* ***************/
    /* PopUp Methods */
    /* ***************/


    public void displayPopUp(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(R.layout.dialog_correction_report_layout);

        AlertDialog dialog = builder.create();
        dialog.show();
        setDialogContent(dialog, context);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Intent intent = new Intent(context, MCQListActivity.class);
                intent.putExtra("Type", StudentPanelActivity.TODO_STUDENT_MOD);
                context.startActivity(intent);
            }
        });
    }

    private void setDialogContent(AlertDialog dialog, Context context) {
        ((TextView) dialog.findViewById(R.id.dialog_correction_report_title))
                .setText(mcq.getName());
        ((TextView) dialog.findViewById(R.id.dialog_correction_report_description))
                .setText(mcq.getDescription());
        ((TextView) dialog.findViewById(R.id.dialog_correction_report_mark))
                .setText(formatMarkForAlertDialog(context));
        ((TextView) dialog.findViewById(R.id.dialog_correction_report_coefficient))
                .setText(formatCoefficientForAlertDialog(context));

        if (mcq.isPointNegative())
        {
            ((TextView) dialog.findViewById(R.id.dialog_correction_report_type))
                    .setText(context.getString(R.string.mcq_edition_type_negative));
        }
        else
        {
            ((TextView) dialog.findViewById(R.id.dialog_correction_report_type))
                    .setText(context.getString(R.string.mcq_edition_type_classic));
        }
    }
    private String formatMarkForAlertDialog(Context context) {
        String raw = context.getString(R.string.dialog_correction_report_mark_text);
        return String.format(raw, markOutOf20, mark, maxMark);

    }
    private String formatCoefficientForAlertDialog(Context context) {
        String raw = context.getString(R.string.dialog_correction_report_coefficient_text);
        return String.format(raw, mcq.getCoefficient());
    }
}
