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
import java.util.List;

import fr.iutmindfuck.qcmiutlyon1.R;
import fr.iutmindfuck.qcmiutlyon1.data.MCQ;
import fr.iutmindfuck.qcmiutlyon1.views.MarkItem;
import fr.iutmindfuck.qcmiutlyon1.data.SessionData;
import fr.iutmindfuck.qcmiutlyon1.data.Student;
import fr.iutmindfuck.qcmiutlyon1.handlers.MCQSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.handlers.MarkSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.handlers.UserSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;
import fr.iutmindfuck.qcmiutlyon1.views.MarkTeacherListAdapter;

public class MarkTeacherActivity extends AppCompatActivity
{

    private enum OrderBy
    {
        MCQ,
        STUDENT
    }

    private MCQSQLHandler mcqSqlHandler;
    private UserSQLHandler userSQLHandler;
    private ListView listView;
    private MarkTeacherListAdapter markListAdapter;
    private List<MarkItem> markItemsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mark_teacher);
        setSupportActionBar((Toolbar) findViewById(R.id.default_list_toolbar));
    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        mcqSqlHandler = new MCQSQLHandler(new SQLServices(this));
        userSQLHandler = new UserSQLHandler(new SQLServices(this));

        listView = findViewById(R.id.default_list_view);

        markItemsList = formateMCQListForMarkItems(mcqSqlHandler.getMCQs());
        markListAdapter = new MarkTeacherListAdapter(MarkTeacherActivity.this,
                                                     markItemsList);


        listView.setAdapter(markListAdapter);

        initSpinner();
    }

    private void initSpinner() {
        Spinner choices_spinner = findViewById(R.id.mark_sorting_spinner);
        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this,
                                        R.array.mark_sorting_choices,
                                        android.R.layout.simple_spinner_item);

        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choices_spinner.setAdapter(spinner_adapter);

        setSpinnerListener(choices_spinner);
    }

    private void setSpinnerListener(Spinner spinner)
    {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                markItemsList.clear();
                if (OrderBy.valueOf("STUDENT").ordinal() == id) {
                    markItemsList.addAll(formateStudentListForMarkItems(userSQLHandler.getAllStudents()));
                    for(MarkItem i : markItemsList)
                        System.out.println(i);
                }

                else {
                    markItemsList.addAll(formateMCQListForMarkItems(mcqSqlHandler.getMCQs()));
                    for(MarkItem i : markItemsList)
                        System.out.println(i);
                }

                markListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
    
    
    private List<MarkItem> formateMCQListForMarkItems(ArrayList<MCQ> mcqArrayList)
    {
        List<MarkItem> marksList = new ArrayList<>();
        MarkSQLHandler markSQLHandler = new MarkSQLHandler(new SQLServices(this));
        for(MCQ mcq : mcqArrayList)
            marksList.add(new MarkItem(mcq.getName(),
                                mcq.getDescription(),
                                Float.toString(markSQLHandler.getAverageForMCQ(mcq.getId()))));

        return marksList;
    }


    private List<MarkItem> formateStudentListForMarkItems(ArrayList<Student> studentArrayList)
    {
        List<MarkItem> marksList = new ArrayList<>();
        MarkSQLHandler markSQLHandler = new MarkSQLHandler(new SQLServices(this));
        for(Student student : studentArrayList)
            marksList.add(new MarkItem(student.getFirstname()
                    + " " + student.getLastname().toUpperCase(),
                    student.getGroup(),
                    Float.toString(markSQLHandler.getAverageForStudent(student.getId()))));


        return marksList;
    }
}
