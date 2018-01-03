package fr.iutmindfuck.qcmiutlyon1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import fr.iutmindfuck.qcmiutlyon1.R;


public class TeacherPanelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_teacher_panel);
        setSupportActionBar((Toolbar) findViewById(R.id.teacher_panel_toolbar));
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
     * While user click on "MCQ" Button, launch the activity that display all MCQ.
     *
     * @param view MCQ Button (provided on click).
     */
    public void onClickMCQ(View view){
        startActivity(new Intent(TeacherPanelActivity.this, MCQListActivity.class));
    }

    /**
     * While user click on "Mark" Button, launch the activity that display MCQ's marks.
     *
     * @param view Mark Button (provided on click).
     */
    public void onClickMark(View view){
        startActivity(new Intent(TeacherPanelActivity.this, MarkTeacherActivity.class));
    }
}