package fr.iutmindfuck.qcmiutlyon1.views;

import android.view.View;
import android.widget.TextView;

import fr.iutmindfuck.qcmiutlyon1.R;

class TextItemViewHolder {
    TextView title;
    TextView description;
    TextView mark;
    TextView coefficient;

    TextItemViewHolder(View convertView) {
        title = convertView.findViewById(R.id.done_list_item_title);
        description = convertView.findViewById(R.id.done_list_item_description);
        mark = convertView.findViewById(R.id.done_list_item_mark);
        coefficient = convertView.findViewById(R.id.done_list_item_coefficient);
    }
}
