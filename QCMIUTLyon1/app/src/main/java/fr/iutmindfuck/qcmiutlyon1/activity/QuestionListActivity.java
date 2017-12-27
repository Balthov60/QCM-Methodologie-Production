package fr.iutmindfuck.qcmiutlyon1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import fr.iutmindfuck.qcmiutlyon1.R;
import fr.iutmindfuck.qcmiutlyon1.data.Question;
import fr.iutmindfuck.qcmiutlyon1.handlers.QuestionSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;
import fr.iutmindfuck.qcmiutlyon1.views.QuestionListAdapter;

public class QuestionListActivity extends AppCompatActivity {

    private int idMCQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_list);

        QuestionSQLHandler questionSQLHandler = new QuestionSQLHandler(new SQLServices(this));
        ListView mcqListView = findViewById(R.id.default_list_view);

        setSupportActionBar((Toolbar) findViewById(R.id.default_list_toolbar));

        Bundle extra = getIntent().getExtras();
        if (extra == null) {
            startActivity(new Intent(this, MCQListActivity.class));
        }
        else {
            idMCQ = extra.getInt("idMCQ");
        }


        ArrayList<Question> questions = questionSQLHandler.getQuestions(idMCQ);
        if (questions == null)
            questions = new ArrayList<>();

        mcqListView.setAdapter(new QuestionListAdapter(this, idMCQ,
                                                        questions, questionSQLHandler));
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
                startActivity(new Intent(getApplicationContext(), MCQListActivity.class));
            }
        });
    }

    public void onListButtonClick(View v)
    {
        Intent intent = new Intent(QuestionListActivity.this,
                                                QuestionEditionActivity.class);
        intent.putExtra("idMCQ", idMCQ);

        startActivity(intent);
    }
}