package fr.iutmindfuck.qcmiutlyon1.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import fr.iutmindfuck.qcmiutlyon1.R;


public class TeacherPanelActivity extends AppCompatActivity {

    private static final String UNIMPLEMENTED_MARK_MESSAGE
                                      = "La gestion des notes n'a pas été implémenté";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_teacher_panel);

        Toolbar toolbar = findViewById(R.id.teacher_panel_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.basic_menu, menu);
        return true;
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

    public void onClickMCQ(View view){
        startActivity(new Intent(TeacherPanelActivity.this, MCQListActivity.class));
    }


    public void onClickMark(View view){
        Context context = getApplicationContext();

        Toast toast = Toast.makeText(context, UNIMPLEMENTED_MARK_MESSAGE, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.show();
    }
}