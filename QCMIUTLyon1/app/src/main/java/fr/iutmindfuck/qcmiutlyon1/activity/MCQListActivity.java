package fr.iutmindfuck.qcmiutlyon1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import fr.iutmindfuck.qcmiutlyon1.R;
import fr.iutmindfuck.qcmiutlyon1.data.MCQ;
import fr.iutmindfuck.qcmiutlyon1.data.SessionData;
import fr.iutmindfuck.qcmiutlyon1.handlers.MCQSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;
import fr.iutmindfuck.qcmiutlyon1.views.MCQStudentListAdapter;
import fr.iutmindfuck.qcmiutlyon1.views.MCQTeacherListAdapter;


public class MCQListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_default_list);
        setSupportActionBar((Toolbar) findViewById(R.id.default_list_toolbar));
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        MCQSQLHandler mcqSQLHandler = new MCQSQLHandler(new SQLServices(this));
        ListView mcqListView = findViewById(R.id.default_list_view);
        ArrayList<MCQ> mcqs = getMCQList(mcqSQLHandler);

        if (SessionData.getInstance().isTeacher())
        {
            mcqListView.setAdapter(new MCQTeacherListAdapter(MCQListActivity.this,
                                                                     mcqs, mcqSQLHandler));
        }
        else
        {
            mcqListView.setAdapter(new MCQStudentListAdapter(MCQListActivity.this, mcqs));
            findViewById(R.id.default_list_button).setVisibility(View.GONE);
        }
    }
    private ArrayList<MCQ> getMCQList(MCQSQLHandler mcqSQLHandler) {
        ArrayList<MCQ> mcqs = mcqSQLHandler.getMCQs();
        if (mcqs == null)
            mcqs = new ArrayList<>();

        return mcqs;
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
                if (SessionData.getInstance().isTeacher())
                {
                    startActivity(new Intent(getApplicationContext(), TeacherPanelActivity.class));
                }
                else
                {
                    startActivity(new Intent(getApplicationContext(), StudentPanelActivity.class));
                }
            }
        });
    }

    /**
     * When user click on "List" Button, launch the activity to create a new MCQ.
     *
     * @param view List Button displayed only if user is a teacher (provided on click).
     */
    public void onListButtonClick(View view) {
        startActivity(new Intent(MCQListActivity.this, MCQEditionActivity.class));
    }
}
