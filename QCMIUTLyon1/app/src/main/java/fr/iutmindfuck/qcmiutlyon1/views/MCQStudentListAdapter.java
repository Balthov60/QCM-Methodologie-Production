package fr.iutmindfuck.qcmiutlyon1.views;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import fr.iutmindfuck.qcmiutlyon1.R;
import fr.iutmindfuck.qcmiutlyon1.activity.QuestionListActivity;
import fr.iutmindfuck.qcmiutlyon1.activity.StudentPanelActivity;
import fr.iutmindfuck.qcmiutlyon1.data.MCQ;
import fr.iutmindfuck.qcmiutlyon1.data.Mark;
import fr.iutmindfuck.qcmiutlyon1.data.SessionData;
import fr.iutmindfuck.qcmiutlyon1.handlers.MarkSQLHandler;
import fr.iutmindfuck.qcmiutlyon1.services.SQLServices;

public class MCQStudentListAdapter extends ArrayAdapter<MCQ> {

    private final List<MCQ> mcqList;
    private final Context context;
    private String type;

    public MCQStudentListAdapter(Context context, List<MCQ> mcqList, String type) {
        super(context, 0, mcqList);
        this.mcqList = mcqList;
        this.context = context;
        this.type = type;
    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull final ViewGroup parent) {
        if (Objects.equals(type, StudentPanelActivity.DONE_STUDENT_MOD))
        {
            if(convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.text_list_item, parent, false);
            }
            initDoneView(convertView, position);
        }
        else
        {
            if(convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.default_list_item, parent, false);
            }
            initTodoView(convertView, position);
            setQuestionClickListener(convertView, position);
        }

        return convertView;
    }

    private void initDoneView(View convertView, final int position) {
        TextItemViewHolder viewHolder = (TextItemViewHolder) convertView.getTag();
        if(viewHolder == null) {
            viewHolder = new TextItemViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        final MCQ mcq = getItem(position);
        if (mcq != null) {
            MarkSQLHandler markSQLHandler = new MarkSQLHandler(new SQLServices(context));
            Mark mark = markSQLHandler.getMark(mcq.getId(), SessionData.getInstance().getUserID());

            viewHolder.title.setText(mcq.getName());
            viewHolder.description.setText(mcq.getDescription());

            viewHolder.mark.setText(String.format(Locale.getDefault(),
                                            "%.2f/20", mark.getValue()));
            viewHolder.coefficient.setText(String.format(Locale.getDefault(),
                                            "coef %.2f", mcq.getCoefficient()));
        }
    }
    private void initTodoView(View convertView, final int position) {
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