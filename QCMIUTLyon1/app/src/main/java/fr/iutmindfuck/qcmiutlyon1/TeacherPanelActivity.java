package fr.iutmindfuck.qcmiutlyon1;

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


public class TeacherPanelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_teacher_panel);

        setSupportActionBar((Toolbar) findViewById(R.id.teacher_panel_toolbar));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.basic_menu, menu);
        return true;
    }


    public void onClickMCQ(View view){
        Intent mcqEdition = new Intent(TeacherPanelActivity.this, MCQEditionActivity.class);
        startActivity(mcqEdition);
    }


    public void onClickMark(View view){
        Context context = getApplicationContext();
        CharSequence text = "La gestion des notes n'a pas été implémenté";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.show();
    }
}