package fr.iutmindfuck.qcmiutlyon1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fr.iutmindfuck.qcmiutlyon1.data.MCQ;
import fr.iutmindfuck.qcmiutlyon1.handlers.MCQSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;

/**
 * Created by sntri on 04/12/2017.
 */

public class MCQListActivity extends AppCompatActivity {

    private ListView mListView;
    private MCQSQLHandler mcqsqlHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcq_list);
        mListView = (ListView) findViewById(R.id.mcq_list_view);
        mcqsqlHandler =new MCQSQLHandler(new SQLServices(MCQListActivity.this));
        mcqsqlHandler.createOrReplaceMCQ(new MCQ(0,"Loi Exponentielle", "C'est un test",false, 1.5f ));
        List<MCQ> mcqList = generateMCQ();

        MCQListAdapter adapter = new MCQListAdapter(MCQListActivity.this, mcqList);
        mListView.setAdapter(adapter);
    }

    private List<MCQ> generateMCQ(){
        List<MCQ> mcqList = this.mcqsqlHandler.getMCQs();
        return mcqList;
    }
}