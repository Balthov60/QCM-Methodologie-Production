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

import java.util.ArrayList;

import fr.iutmindfuck.qcmiutlyon1.R;
import fr.iutmindfuck.qcmiutlyon1.data.Answer;
import fr.iutmindfuck.qcmiutlyon1.data.Question;
import fr.iutmindfuck.qcmiutlyon1.data.SessionData;

public class QuestionActivity extends AppCompatActivity {

    private Question question;
    private int idMCQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtra();

        setContentView(R.layout.activity_question);
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
                startActivity(intent);
            }
        });
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        displayQuestion();
    }

    private void displayQuestion() {
        ((TextView) findViewById(R.id.question_answer_title)).setText(question.getTitle());

        if (question.getAnswers() != null)
            displayAnswers();
    }
    @SuppressLint("InflateParams")
    private void displayAnswers() {
        LinearLayout parent = findViewById(R.id.question_answer_container);
        ArrayList<Boolean> answersMarkedAsTrue
                = SessionData.getInstance().getAnswersStatus(idMCQ, question.getId());

        for (int i = 0; i < question.getAnswersQty(); i++) {
            View custom = getLayoutInflater().inflate(R.layout.answer_reply_section, null);
            Answer answer = question.getAnswers().get(i);

            if (answersMarkedAsTrue != null && answersMarkedAsTrue.get(i))
                ((CheckBox) custom.findViewById(R.id.answer_reply)).setChecked(true);

            ((TextView) custom.findViewById(R.id.question_reply_answer)).setText(answer.getTitle());

            parent.addView(custom);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SessionData.getInstance().saveAnswers(idMCQ, question, getAnswersIndexMarkedAsTrue());
    }

    /**
     * Get Answers checked by the user.
     *
     * @return Array List of Answers Id checked by the user.
     */
    private ArrayList<Integer> getAnswersIndexMarkedAsTrue() {
        ArrayList<Integer> trueAnswersID = new ArrayList<>();
        LinearLayout answersContainer = findViewById(R.id.question_answer_container);

        for (int i = 0; i < answersContainer.getChildCount(); i++) {
            if (isAnswerCheckedAt(i, answersContainer))
                trueAnswersID.add(i);
        }

        return trueAnswersID;
    }
    private boolean isAnswerCheckedAt(int index, LinearLayout answersContainer) {
        LinearLayout answerSection = (LinearLayout) answersContainer.getChildAt(index);

        return ((CheckBox) answerSection.getChildAt(0)).isChecked();
    }
}


