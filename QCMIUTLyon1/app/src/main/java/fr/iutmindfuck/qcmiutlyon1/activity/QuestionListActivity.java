package fr.iutmindfuck.qcmiutlyon1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import fr.iutmindfuck.qcmiutlyon1.R;
import fr.iutmindfuck.qcmiutlyon1.data.MCQCorrectionReport;
import fr.iutmindfuck.qcmiutlyon1.data.Question;
import fr.iutmindfuck.qcmiutlyon1.data.SessionData;
import fr.iutmindfuck.qcmiutlyon1.handlers.QuestionSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;
import fr.iutmindfuck.qcmiutlyon1.views.QuestionStudentListAdapter;
import fr.iutmindfuck.qcmiutlyon1.views.QuestionTeacherListAdapter;

public class QuestionListActivity extends AppCompatActivity {

    private int idMCQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getExtra();
        setContentView(R.layout.activity_default_list);
        setSupportActionBar((Toolbar) findViewById(R.id.default_list_toolbar));
    }
    private void getExtra() {
        Bundle extra = getIntent().getExtras();

        if (extra == null)
        {
            startActivity(new Intent(this, AuthActivity.class));
        }
        else
        {
            idMCQ = extra.getInt("idMCQ");
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        QuestionSQLHandler questionSQLHandler = new QuestionSQLHandler(new SQLServices(this));
        ListView mcqListView = findViewById(R.id.default_list_view);
        ArrayList<Question> questions = getQuestions(questionSQLHandler);

        if (SessionData.getInstance().isTeacher())
        {
            mcqListView.setAdapter(new QuestionTeacherListAdapter(this, idMCQ,
                                                                  questions, questionSQLHandler));
        }
        else
        {
            mcqListView.setAdapter(new QuestionStudentListAdapter(this, idMCQ, questions));

            ((Button) findViewById(R.id.default_list_button)).setText(R.string.student_submit_mcq);
        }
    }
    private ArrayList<Question> getQuestions(QuestionSQLHandler questionSQLHandler) {
        ArrayList<Question> questions = questionSQLHandler.getQuestions(idMCQ);
        if (questions == null)
            questions = new ArrayList<>();

        return questions;
    }

    @Override
    public void setSupportActionBar(Toolbar toolbar) {
        super.setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MCQListActivity.class));
            }
        });
    }

    /**
     * When user click on "List" Button :
     *  If user is a teacher display de question creation activity
     *  Else correct the current mcq
     *
     * @param view List Button displayed (provided on click).
     */
    public void onListButtonClick(View view) {
        if (SessionData.getInstance().isTeacher())
        {
            Intent intent = new Intent(this, QuestionEditionActivity.class);
            intent.putExtra("idMCQ", idMCQ);
            startActivity(intent);
        }
        else
        {
            SQLServices sqlServices = new SQLServices(this);
            MCQCorrectionReport correctionReport = new MCQCorrectionReport(idMCQ, sqlServices);

            correctionReport.saveMark(sqlServices);
            correctionReport.exportInJson(QuestionListActivity.this);
            correctionReport.displayPopUp(QuestionListActivity.this);
        }
    }
}