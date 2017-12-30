package fr.iutmindfuck.qcmiutlyon1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import fr.iutmindfuck.qcmiutlyon1.R;


public class StudentPanelActivity extends AppCompatActivity {

    public final static String TODO_STUDENT_MOD = "TODO";
    public final static String DONE_STUDENT_MOD = "DONE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student_panel);
        setSupportActionBar((Toolbar) findViewById(R.id.student_panel_toolbar));
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
                startActivity(new Intent(getApplicationContext(), AuthActivity.class));
            }
        });
    }

    /**
     * While user click on "MCQ To do" Button, launch the activity that display all uncompleted MCQ.
     *
     * @param view MCQ To do Button (provided on click).
     */
    public void onClickMCQTodo(View view) {
        Intent intent = new Intent(StudentPanelActivity.this, MCQListActivity.class);
        intent.putExtra("Type", TODO_STUDENT_MOD);

        startActivity(intent);
    }

    /**
     * While user click on "MCQ Done" Button, launch the activity that display all MCQ completed.
     *
     * @param view MCQ Done Button (provided on click).
     */
    public void onClickMCQDone(View view) {
        Intent intent = new Intent(StudentPanelActivity.this, MCQListActivity.class);
        intent.putExtra("Type", DONE_STUDENT_MOD);

        startActivity(intent);
    }
}
