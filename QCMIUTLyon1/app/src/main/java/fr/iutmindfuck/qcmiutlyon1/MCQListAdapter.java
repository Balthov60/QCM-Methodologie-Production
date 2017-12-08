package fr.iutmindfuck.qcmiutlyon1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.iutmindfuck.qcmiutlyon1.data.MCQ;

public class MCQListAdapter extends ArrayAdapter<MCQ> {

    MCQListAdapter(Context context, List<MCQ> mcqList) {
        super(context, 0, mcqList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.mcq_list_item,
                                                                    parent, false);
        }

        MCQViewHolder viewHolder = (MCQViewHolder) convertView.getTag();
        if(viewHolder == null) {
            viewHolder = new MCQViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        MCQ mcq = getItem(position);
        viewHolder.mcqNameView.setText(mcq != null ? mcq.getName() : null);
        viewHolder.mcqDescriptionView.setText(mcq != null ? mcq.getDescription() : null);

        return convertView;
    }

    class MCQViewHolder {
        TextView mcqNameView;
        TextView mcqDescriptionView;

        MCQViewHolder(View convertView) {
            mcqNameView = convertView.findViewById(R.id.mcq_list_item_name);
            mcqDescriptionView = convertView.findViewById(R.id.mcq_name);
        }
    }
}