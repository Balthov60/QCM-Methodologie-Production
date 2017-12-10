package fr.iutmindfuck.qcmiutlyon1.views.mcqlistview;

import android.view.View;
import android.widget.TextView;

import fr.iutmindfuck.qcmiutlyon1.R;

class MCQViewHolder {
    TextView mcqNameView;
    TextView mcqDescriptionView;

    MCQViewHolder(View convertView) {
        mcqNameView = convertView.findViewById(R.id.mcq_list_item_name);
        mcqDescriptionView = convertView.findViewById(R.id.mcq_name);
    }
}