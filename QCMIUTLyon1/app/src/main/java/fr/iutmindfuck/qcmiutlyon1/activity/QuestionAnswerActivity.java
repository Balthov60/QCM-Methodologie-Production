package fr.iutmindfuck.qcmiutlyon1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.iutmindfuck.qcmiutlyon1.R;
import fr.iutmindfuck.qcmiutlyon1.data.Question;

public class QuestionAnswerActivity extends AppCompatActivity{

    protected String questionIndication = "1";
    protected Question question;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_answer);

        Bundle extra = getIntent().getExtras();
        if(extra == null){
            //TODO: Trigger an error
        }
        else {
            //TODO: Add to the indicationTextView the proper indication of the question selected
            addCheckboxView();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.basic_menu, menu);
        return true;
    }

    public void addCheckboxView(){
        for(int i = 0; i < question.getAnswers().size(); i++){
            TextView indication = (TextView) findViewById(R.id.question_selected_label);
            indication.setText(questionIndication);


        }
    }


}
