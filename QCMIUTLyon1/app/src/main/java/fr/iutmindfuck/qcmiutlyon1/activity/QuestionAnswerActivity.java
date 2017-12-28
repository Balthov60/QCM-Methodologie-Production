package fr.iutmindfuck.qcmiutlyon1.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;

import fr.iutmindfuck.qcmiutlyon1.R;
import fr.iutmindfuck.qcmiutlyon1.data.Answer;
import fr.iutmindfuck.qcmiutlyon1.data.Question;
import fr.iutmindfuck.qcmiutlyon1.handlers.QuestionSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;

public class QuestionAnswerActivity extends AppCompatActivity{


    QuestionSQLHandler questionSQLHandler;
    Question question;
    ArrayList<Answer> answers = new ArrayList<>();

    int idMCQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_question_answer);
        setSupportActionBar((Toolbar) findViewById(R.id.question_answer_toolbar));

        /* Test A supprimer pour la version final */

        answers.add(new Answer("bleu", true));
        answers.add(new Answer("pas de couleur", true));
        answers.add(new Answer("blanc", true));
        answers.add(new Answer("jaunatre", true));
        answers.add(new Answer("vert", true));
        answers.add(new Answer("Balthov60 sûr et certain", true));
        answers.add(new Answer("blouje", true));
        answers.add(new Answer("violose", true));

        question = new Question(1,"Quelle est la couleur du cheval blanc d'Henri IV",answers);

        questionSQLHandler = new QuestionSQLHandler(new SQLServices(this));
        questionSQLHandler.createOrReplaceQuestion(question, 1);

        Bundle extra = getIntent().getExtras();
        if (extra != null){
            question = (Question) extra.getSerializable("question");
            if(question !=null){
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
        displayQuestionTitle(question.getTitle());
        if(question.getAnswers() != null)
            displayAnswers(question.getAnswers());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.basic_menu, menu);
        return true;
    }


    @SuppressLint("InflateParams")
    public void displayAnswers(ArrayList<Answer> answers) {
        LinearLayout parent = findViewById(R.id.question_answer_container);

        for (Answer answer : answers) {
            View custom = getLayoutInflater().inflate(R.layout.answer_reply_section, null);
            ((CheckBox) custom.findViewById(R.id.answer_reply)).setChecked(false);
            ((TextView) custom.findViewById(R.id.question_reply_answer)).setText(answer.getTitle());

            parent.addView(custom);
        }
    }

    public void displayQuestionTitle(String questionTitle) {
        ((TextView) findViewById(R.id.question_answer_title)).setText(questionTitle);
    }

    public void nextQuestion(View view) {

    }

    public void previousQuestion(View view) {
    }
}


