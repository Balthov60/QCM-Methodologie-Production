package fr.iutmindfuck.qcmiutlyon1.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import fr.iutmindfuck.qcmiutlyon1.R;
import fr.iutmindfuck.qcmiutlyon1.data.Answer;
import fr.iutmindfuck.qcmiutlyon1.data.Question;

public class QuestionActivity extends AppCompatActivity{

    private Question question;
    private int idMCQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtra();

        setContentView(R.layout.activity_question_answer);
        setSupportActionBar((Toolbar) findViewById(R.id.question_answer_toolbar));
    }
    private void getExtra() {
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            question = (Question) extra.getSerializable("question");
            idMCQ = extra.getInt("idMCQ");
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        if (question != null) {
            initQuestionField();
        }
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
                Intent intent = new Intent(getApplicationContext(), QuestionListActivity.class);
                intent.putExtra("idMCQ", idMCQ);
                intent.putExtra("isTeacher", false);
                startActivity(intent);
            }
        });
    }


    public void initQuestionField(){
        ((TextView) findViewById(R.id.question_answer_title)).setText(question.getTitle());

        if(question.getAnswers() != null)
            displayAnswers();
    }
    @SuppressLint("InflateParams")
    public void displayAnswers() {
        LinearLayout parent = findViewById(R.id.question_answer_container);

        for (Answer answer : question.getAnswers()) {
            View custom = getLayoutInflater().inflate(R.layout.answer_reply_section, null);

            ((CheckBox) custom.findViewById(R.id.answer_reply)).setChecked(false);
            ((TextView) custom.findViewById(R.id.question_reply_answer)).setText(answer.getTitle());

            parent.addView(custom);
        }
    }


    public void nextQuestion(View view) {
        // TODO
    }
    public void previousQuestion(View view) {
        // TODO
    }
}


