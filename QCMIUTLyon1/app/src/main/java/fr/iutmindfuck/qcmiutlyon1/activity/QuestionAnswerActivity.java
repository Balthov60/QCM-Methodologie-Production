package fr.iutmindfuck.qcmiutlyon1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import fr.iutmindfuck.qcmiutlyon1.R;
import fr.iutmindfuck.qcmiutlyon1.data.Question;

public class QuestionAnswerActivity extends AppCompatActivity{

    protected String questionIndication = "1";
    protected Question question;
    protected final RelativeLayout relativeLayout = new RelativeLayout(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_answer);

        Bundle extra = getIntent().getExtras();
        if(extra == null){
            //TODO: Trigger an error
            addCheckboxView();

        }
        else {
            //TODO: Add to the indicationTextView, the proper indication of the question selected

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.basic_menu, menu);
        return true;
    }

    private void addCheckboxView(){
        String questionTitle = "Quelle est la capitale de la France";
        String indice = "1";

        TextView indication = (TextView) findViewById(R.id.question_selected_label);
        indication.setText(questionTitle);

        //String indice = String.valueOf(i);
        //indication.setText("Question n°"+indice+question.getTitle());
        for(int i = 0; i < 2; i++){
            CheckBox c = new CheckBox(getApplicationContext());
            c.setText("Dynamic Checkbox "+i);
            relativeLayout.addView(c);
        }
        this.setContentView(relativeLayout);

    }


}
