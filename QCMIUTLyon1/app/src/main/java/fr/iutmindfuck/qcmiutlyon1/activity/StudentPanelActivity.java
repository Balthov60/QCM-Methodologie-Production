package fr.iutmindfuck.qcmiutlyon1.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import fr.iutmindfuck.qcmiutlyon1.R;

/**
 * Created by mateo on 26/12/2017.
 */

public class StudentPanelActivity extends AppCompatActivity{


    private final String UNIMPLEMENTED_MCQ_DONE_MESSAGE = "Affichage des QCM faits";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_student_panel);

        setSupportActionBar((Toolbar) findViewById(R.id.student_panel_toolbar));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.basic_menu, menu);
        return true;
    }

    public void onClickMCQTodo(View view){

        Intent intent = new Intent(StudentPanelActivity.this, MCQListActivity.class);

        intent.putExtra("Type","TODO");

        startActivity(intent);

    }


    public void onClickMCQDone(View view){

        Intent intent = new Intent(StudentPanelActivity.this, MCQListActivity.class);

        intent.putExtra("Type","DONE");

        startActivity(intent);
    }
}