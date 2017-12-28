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
import fr.iutmindfuck.qcmiutlyon1.handlers.MCQSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;
import fr.iutmindfuck.qcmiutlyon1.views.MCQStudentListAdapter;
import fr.iutmindfuck.qcmiutlyon1.views.MCQTeacherListAdapter;


public class MCQListActivity extends AppCompatActivity {

    private boolean isTeacher = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init Main Components
        getExtra();
        setContentView(R.layout.activity_default_list);
        setSupportActionBar((Toolbar) findViewById(R.id.default_list_toolbar));

        MCQSQLHandler mcqSQLHandler = new MCQSQLHandler(new SQLServices(this));
        ListView mcqListView = findViewById(R.id.default_list_view);

        if (!isTeacher)
            findViewById(R.id.default_list_button).setVisibility(View.GONE);

        // Fill MCQ List
        ArrayList<MCQ> mcqs = mcqSQLHandler.getMCQs();
        if (mcqs == null)
            mcqs = new ArrayList<>();

        if (isTeacher)
            mcqListView.setAdapter(new MCQTeacherListAdapter(MCQListActivity.this,
                                                             mcqs, mcqSQLHandler));
        else
            mcqListView.setAdapter(new MCQStudentListAdapter(MCQListActivity.this, mcqs));
    }
    private void getExtra() {
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
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
                if (isTeacher)
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

    public void onListButtonClick(View v) {
        startActivity(new Intent(MCQListActivity.this, MCQEditionActivity.class));
    }
}
