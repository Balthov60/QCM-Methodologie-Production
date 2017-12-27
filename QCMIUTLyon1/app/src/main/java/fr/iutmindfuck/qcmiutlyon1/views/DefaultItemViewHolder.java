package fr.iutmindfuck.qcmiutlyon1.views;

import android.view.View;
import android.widget.TextView;

import fr.iutmindfuck.qcmiutlyon1.R;


class DefaultItemViewHolder {
    TextView title;
    TextView description;

    DefaultItemViewHolder(View convertView) {
        title = convertView.findViewById(R.id.list_item_title);
        description = convertView.findViewById(R.id.list_item_description);
    }
}