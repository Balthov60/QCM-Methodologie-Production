package fr.iutmindfuck.qcmiutlyon1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import fr.iutmindfuck.qcmiutlyon1.handlers.MCQSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;


public class MCQListActivity extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcq_list);

        MCQSQLHandler mcqSQLHandler = new MCQSQLHandler(new SQLServices(this));
        mListView = findViewById(R.id.mcq_list_view);

        mListView.setAdapter(new MCQListAdapter(MCQListActivity.this,
                                                mcqSQLHandler.getMCQs()));
    }

    public void onModifyMCQButtonClick(View v)
    {

    }

    public void onRemoveMCQButtonClick(View v)
    {

    }
}