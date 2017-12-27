package fr.iutmindfuck.qcmiutlyon1.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;


import java.util.ArrayList;

import fr.iutmindfuck.qcmiutlyon1.R;
import fr.iutmindfuck.qcmiutlyon1.data.Answer;
import fr.iutmindfuck.qcmiutlyon1.data.Question;
import fr.iutmindfuck.qcmiutlyon1.handlers.QuestionSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;

public class QuestionAnswerActivity extends AppCompatActivity{

    private static final String INVALID_SUBMISSION = "Votre question n'est pas valide.";

    QuestionSQLHandler questionSQLHandler;
    Question question;
    int idMCQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_question_answer);
        setSupportActionBar((Toolbar) findViewById(R.id.question_edition_toolbar));

        questionSQLHandler = new QuestionSQLHandler(new SQLServices(this));

        Bundle extra = getIntent().getExtras();
        if (extra == null) {
            question = (Question) extra.getSerializable("question");

            if (question == null) {
                initFormField();
            }

            idMCQ = extra.getInt("idMCQ");
        }
    }

    @Override
    public void setSupportActionBar(Toolbar toolbar) {
        super.setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        /*
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuestionListActivity.class);
                intent.putExtra("idMCQ", idMCQ);
                startActivity(intent);
            }
        });*/
    }

    public void initFormField(){
        if (question.getAnswers() != null)
            setAnswers(question.getAnswers());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.basic_menu, menu);
        return true;
    }

    @SuppressLint("InflateParams")
    public void setAnswers(ArrayList<Answer> answers) {
        LinearLayout parent = findViewById(R.id.question_edition_answer_container);

        for (Answer answer : answers) {
            View custom = getLayoutInflater().inflate(R.layout.answer_edition_section, null);
                ((CheckBox) custom.findViewById(R.id.answer_reply)).setChecked(false);

            parent.addView(custom);
        }
    }
}


