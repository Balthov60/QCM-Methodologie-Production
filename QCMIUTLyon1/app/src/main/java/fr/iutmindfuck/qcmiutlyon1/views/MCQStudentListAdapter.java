package fr.iutmindfuck.qcmiutlyon1.views;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import fr.iutmindfuck.qcmiutlyon1.R;
import fr.iutmindfuck.qcmiutlyon1.activity.MCQEditionActivity;
import fr.iutmindfuck.qcmiutlyon1.activity.QuestionListActivity;
import fr.iutmindfuck.qcmiutlyon1.data.MCQ;
import fr.iutmindfuck.qcmiutlyon1.handlers.MCQSQLHandler;

public class MCQStudentListAdapter extends ArrayAdapter<MCQ> {

    private final List<MCQ> mcqList;
    private final MCQSQLHandler mcqsqlHandler;
    private final Context context;

    public MCQStudentListAdapter(Context context, List<MCQ> mcqList, MCQSQLHandler mcqsqlHandler) {
        super(context, 0, mcqList);
        this.mcqList = mcqList;
        this.mcqsqlHandler = mcqsqlHandler;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull final ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext())
                                        .inflate(R.layout.default_list_item, parent, false);
        }

        initView(convertView, position);
        setQuestionClickListener(convertView, position);

        return convertView;
    }

    private void initView(View convertView, final int position) {
        DefaultItemViewHolder viewHolder = (DefaultItemViewHolder) convertView.getTag();
        if(viewHolder == null) {
            viewHolder = new DefaultItemViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        final MCQ mcq = getItem(position);
        viewHolder.title.setText(mcq != null ? mcq.getName() : null);
        viewHolder.description.setText(mcq != null ? mcq.getDescription() : null);
        viewHolder.list_item_first_interaction.setVisibility(View.GONE);
        viewHolder.list_item_second_interaction.setVisibility(View.GONE);
    }

    private void setQuestionClickListener(View questionClickListener, final int position) {
        questionClickListener.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, QuestionListActivity.class);

                        intent.putExtra("idMCQ", mcqList.get(position).getId());
                        intent.putExtra("isTeacher", false);

                        context.startActivity(intent);
                    }
                }
        );
    }
}