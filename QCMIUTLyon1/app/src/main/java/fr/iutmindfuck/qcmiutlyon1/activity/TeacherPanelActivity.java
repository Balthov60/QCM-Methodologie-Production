package fr.iutmindfuck.qcmiutlyon1.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import fr.iutmindfuck.qcmiutlyon1.R;


public class TeacherPanelActivity extends AppCompatActivity {

    private static final String UNIMPLEMENTED_MARK_MESSAGE
                                      = "La gestion des notes n'a pas été implémenté";

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
        Intent intent = new Intent(TeacherPanelActivity.this, MCQListActivity.class);
        intent.putExtra("isTeacher", true);

        startActivity(intent);
    }

    /**
     * While user click on "Mark" Button, launch the activity that display MCQ's marks.
     *
     * @param view Mark Button (provided on click).
     */
    public void onClickMark(View view){
        Context context = getApplicationContext();

        Toast toast = Toast.makeText(context, UNIMPLEMENTED_MARK_MESSAGE, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.show();
    }
}