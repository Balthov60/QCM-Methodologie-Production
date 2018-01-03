package fr.iutmindfuck.qcmiutlyon1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import fr.iutmindfuck.qcmiutlyon1.R;
import fr.iutmindfuck.qcmiutlyon1.data.MCQ;
import fr.iutmindfuck.qcmiutlyon1.data.SessionData;
import fr.iutmindfuck.qcmiutlyon1.handlers.MCQSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;
import fr.iutmindfuck.qcmiutlyon1.views.MCQStudentListAdapter;
import fr.iutmindfuck.qcmiutlyon1.views.MCQTeacherListAdapter;


public class MCQListActivity extends AppCompatActivity {

    private String mcqSelector = "";
    private MCQSQLHandler mcqSQLHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtra();
        mcqSQLHandler = new MCQSQLHandler(new SQLServices(this));

        setContentView(R.layout.activity_default_list);
        setSupportActionBar((Toolbar) findViewById(R.id.mark_teacher_toolbar));
    }
    private void getExtra() {
        Bundle extra = getIntent().getExtras();

        if (extra != null && extra.getString("Type") != null)
            mcqSelector = extra.getString("Type");
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        setDescription();
        setAdapter();
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

    /* ******************/
    /* Display Methods */
    /* *****************/

    private void setDescription() {
        TextView description = findViewById(R.id.default_list_description);

        if (SessionData.getInstance().isTeacher())
        {
            description.setText(R.string.default_list_description_teacher);
        }
        else
        {
            if (Objects.equals(mcqSelector, StudentPanelActivity.DONE_STUDENT_MOD))
            {
                description.setText(R.string.default_list_description_done);
            }
            else
            {
                description.setText(R.string.default_list_description_todo);
            }
        }
    }
    /**
     * Set List Adapter who handle interaction & list items creation.
     */
    private void setAdapter() {
        ListView mcqListView = findViewById(R.id.default_list_view);
        ArrayList<MCQ> mcqList = getMCQList(mcqSQLHandler);

        if (SessionData.getInstance().isTeacher())
        {
            mcqListView.setAdapter(
                    new MCQTeacherListAdapter(MCQListActivity.this, mcqList, mcqSQLHandler));
        }
        else
        {
            mcqListView.setAdapter(
                    new MCQStudentListAdapter(MCQListActivity.this, mcqList, mcqSelector));

            findViewById(R.id.default_list_button).setVisibility(View.GONE);
        }
    }

    /**
     * Get MCQ List Depending on user & option used to launch activity
     *
     * @param mcqSQLHandler handler to get MCQs
     * @return ArrayList of MCQ matching context.
     */
    private ArrayList<MCQ> getMCQList(MCQSQLHandler mcqSQLHandler) {
        ArrayList<MCQ> mcqList;

        if (SessionData.getInstance().isTeacher())
        {
            mcqList = mcqSQLHandler.getMCQs();
        }
        else
        {
            if (Objects.equals(mcqSelector, StudentPanelActivity.DONE_STUDENT_MOD))
            {
                mcqList = mcqSQLHandler.getDoneMCQ(SessionData.getInstance().getUserID());
            }
            else
            {
                mcqList = mcqSQLHandler.getTodoMCQ(SessionData.getInstance().getUserID());
            }
        }

        return ((mcqList == null) ? new ArrayList<MCQ>() : mcqList);
    }


    /* ***************************/
    /* User Interaction Handling */
    /* ***************************/


    /**
     * When user click on "List" Button, launch the activity to create a new MCQ.
     *
     * @param view List Button displayed only if user is a teacher (provided on click).
     */
    public void onListButtonClick(View view) {
        startActivity(new Intent(MCQListActivity.this, MCQEditionActivity.class));
    }
}
