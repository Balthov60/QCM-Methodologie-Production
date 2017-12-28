package fr.iutmindfuck.qcmiutlyon1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import fr.iutmindfuck.qcmiutlyon1.R;
import fr.iutmindfuck.qcmiutlyon1.data.Question;
import fr.iutmindfuck.qcmiutlyon1.handlers.QuestionSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;
import fr.iutmindfuck.qcmiutlyon1.views.QuestionStudentListAdapter;
import fr.iutmindfuck.qcmiutlyon1.views.QuestionTeacherListAdapter;

public class QuestionListActivity extends AppCompatActivity {

    private int idMCQ;
    private boolean isTeacher = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init Main Component
        getExtra();
        setContentView(R.layout.activity_default_list);
        setSupportActionBar((Toolbar) findViewById(R.id.default_list_toolbar));

        QuestionSQLHandler questionSQLHandler = new QuestionSQLHandler(new SQLServices(this));
        ListView mcqListView = findViewById(R.id.default_list_view);

        if (!isTeacher)
            ((Button) findViewById(R.id.default_list_button)).setText(R.string.student_submit_mcq);

        // Fill Question List
        ArrayList<Question> questions = questionSQLHandler.getQuestions(idMCQ);
        if (questions == null)
            questions = new ArrayList<>();

        if (isTeacher)
            mcqListView.setAdapter(new QuestionTeacherListAdapter(this, idMCQ,
                                                                    questions, questionSQLHandler));
        else
            mcqListView.setAdapter(new QuestionStudentListAdapter(this, idMCQ, questions));
    }
    private void getExtra() {
        Bundle extra = getIntent().getExtras();
        if (extra == null) {
            startActivity(new Intent(this, AuthActivity.class));
        }
        else {
            idMCQ = extra.getInt("idMCQ");
            isTeacher = extra.getBoolean("isTeacher");
        }
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
                Intent intent = new Intent(getApplicationContext(), MCQListActivity.class);
                intent.putExtra("isTeacher", isTeacher);

                startActivity(intent);
            }
        });
    }

    public void onListButtonClick(View v)
    {
        if (isTeacher) {
            Intent intent = new Intent(this, QuestionEditionActivity.class);
            intent.putExtra("idMCQ", idMCQ);
            startActivity(intent);
        }
        else {
            // TODO implement correction it
            Log.i("Info","Not Implemented");
        }
    }
}