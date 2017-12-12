package fr.iutmindfuck.qcmiutlyon1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import fr.iutmindfuck.qcmiutlyon1.data.MCQ;
import fr.iutmindfuck.qcmiutlyon1.data.Mark;
import fr.iutmindfuck.qcmiutlyon1.handlers.MarkSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.views.mcqlistview.MCQListAdapter;
import fr.iutmindfuck.qcmiutlyon1.R;
import fr.iutmindfuck.qcmiutlyon1.handlers.MCQSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;


public class MCQListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcq_list);

        MCQSQLHandler mcqSQLHandler = new MCQSQLHandler(new SQLServices(this));
        MarkSQLHandler markSQLHandler = new MarkSQLHandler(new SQLServices(this));
        markSQLHandler.createMark(new Mark(1,1,15));
        markSQLHandler.createMark(new Mark(1,2,16));
        markSQLHandler.createMark(new Mark(1,3,17));
        markSQLHandler.createMark(new Mark(1,4,18));

        ArrayList<Mark> marks =  markSQLHandler.getAllMarksForMCQ(1);

        for(int i=0; i<marks.size(); i++) {
            System.out.println(marks.get(i).getIdMCQ());
            System.out.println(marks.get(i).getIdStudent());
            System.out.println(marks.get(i).getValue());
            System.out.println("\n\n");
        }
        ListView mcqListView = findViewById(R.id.mcq_list_view);

        ArrayList<MCQ> mcqs = mcqSQLHandler.getMCQs();
        if (mcqs == null)
            mcqs = new ArrayList<>();

        mcqListView.setAdapter(new MCQListAdapter(MCQListActivity.this,
                                                         mcqs,
                                                         mcqSQLHandler));
    }

    public void createNewMCQ(View v)
    {
        startActivity(new Intent(MCQListActivity.this, MCQEditionActivity.class));
    }
}