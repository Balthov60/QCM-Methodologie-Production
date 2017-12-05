package fr.iutmindfuck.qcmiutlyon1;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import fr.iutmindfuck.qcmiutlyon1.data.Answer;
import fr.iutmindfuck.qcmiutlyon1.data.Question;
import fr.iutmindfuck.qcmiutlyon1.handlers.QuestionSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;

public class QuestionEditionActivity extends AppCompatActivity {

    private static final String INVALID_SUBMISSION = "Votre question n'est pas valide.";

    QuestionSQLHandler questionSQLHandler;
    int questionID;
    int mcqID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_question_edition);
        setSupportActionBar((Toolbar) findViewById(R.id.mcq_edition_toolbar));

        Bundle extra = getIntent().getExtras();
        if (extra == null) {
            // TODO send List question activity
            questionID = 0;
            mcqID = 0;
        }
        else {
            questionID = extra.getInt("questionID");
            mcqID = extra.getInt("mcqID");
        }

        questionSQLHandler = new QuestionSQLHandler(new SQLServices(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.basic_menu, menu);
        return true;
    }

    public void addAnswer(View view) {
        LinearLayout layout = findViewById(R.id.question_edition_answer_container);
        getLayoutInflater().inflate(R.layout.answer_edition_section, layout);
    }
    public void removeAnswer(View view) {
        LinearLayout layout = findViewById(R.id.question_edition_answer_container);
        layout.removeView((View) view.getParent());
    }
    public void submitQuestion(View view) {
        String title = getQuestionTitle();
        ArrayList<Answer> answers = getQuestionAnswers();

        if (title.isEmpty() || !Answer.checkAnswersValidity(answers)) {
            displayErrorToast();
            return;
        }

        questionSQLHandler.createOrReplaceQuestion(
                new Question(questionID, title, answers),
                mcqID
        );
    }

    /* Get Value from Question Form */
    public String getQuestionTitle() {
        return ((EditText) findViewById(R.id.question_edition_title_input)).getText().toString();
    }
    public ArrayList<Answer> getQuestionAnswers() {
        ArrayList<Answer> answers = new ArrayList<>();

        LinearLayout layout = findViewById(R.id.question_edition_answer_container);
        for (int i = 0; i < layout.getChildCount(); i++) {
            LinearLayout answerSection = (LinearLayout) layout.getChildAt(i);
            answers.add(new Answer(
                    ((EditText) answerSection.getChildAt(1)).getText().toString(),
                    ((CheckBox) answerSection.getChildAt(0)).isChecked())
            );
        }

        return answers;
    }

    /* Toast */
    public void displayErrorToast() {
        Context context = this.getApplicationContext();

        Toast toast = Toast.makeText(context, INVALID_SUBMISSION, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM,0,50);
        toast.show();
    }

}
