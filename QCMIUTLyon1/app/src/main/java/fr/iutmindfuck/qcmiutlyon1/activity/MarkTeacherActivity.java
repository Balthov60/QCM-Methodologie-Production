package fr.iutmindfuck.qcmiutlyon1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

import fr.iutmindfuck.qcmiutlyon1.R;
import fr.iutmindfuck.qcmiutlyon1.data.MCQ;
import fr.iutmindfuck.qcmiutlyon1.data.SessionData;
import fr.iutmindfuck.qcmiutlyon1.handlers.MCQSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;
import fr.iutmindfuck.qcmiutlyon1.views.MCQStudentListAdapter;
import fr.iutmindfuck.qcmiutlyon1.views.MCQTeacherListAdapter;

public class MarkTeacherActivity extends AppCompatActivity
{
    Spinner choices_spinner;
    ArrayAdapter<CharSequence> spinner_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mark_teacher);
        setSupportActionBar((Toolbar) findViewById(R.id.default_list_toolbar));



    }

    private void initSpinner()
    {
        choices_spinner = (Spinner) findViewById(R.id.mark_sorting_spinner);
        spinner_adapter = ArrayAdapter.createFromResource(this, R.array.mark_sorting_choices, android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choices_spinner.setAdapter(spinner_adapter);

        initSpinnerInteraction();
    }


    private void initSpinnerInteraction()
    {
        choices_spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
            }
        });
    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        MCQSQLHandler mcqSQLHandler = new MCQSQLHandler(new SQLServices(this));
        ListView mcqListView = findViewById(R.id.default_list_view);
        ArrayList<MCQ> mcqs = getMCQList(mcqSQLHandler);

        if (SessionData.getInstance().isTeacher())
        {
            //mcqListView.setAdapter(new MCQTeacherListAdapter(MCQListActivity.this,
                    //mcqs, mcqSQLHandler));
        }
        else
        {
            //mcqListView.setAdapter(new MCQStudentListAdapter(MCQListActivity.this, mcqs));
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
}
