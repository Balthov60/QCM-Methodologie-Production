package fr.iutmindfuck.qcmiutlyon1.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import fr.iutmindfuck.qcmiutlyon1.R;
import fr.iutmindfuck.qcmiutlyon1.data.MarkItem;


public class MarkTeacherListAdapter extends ArrayAdapter<MarkItem> {

    public MarkTeacherListAdapter(Context context, List<MarkItem> list) {
        super(context, 0, list);
    }

    @Override
    @NonNull
    public View getView(final int position, View convertView, @NonNull final ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.text_list_item, parent, false);
        }

        initView(convertView, position);
        return convertView;
    }

    private void initView(View convertView, final int position) {
        TextItemViewHolder viewHolder = (TextItemViewHolder) convertView.getTag();

        if (viewHolder == null)
        {
            viewHolder = new TextItemViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        final MarkItem markItem = getItem(position);

        if (markItem != null) {
            viewHolder.title.setText(markItem.getTitle());
            viewHolder.description.setText(markItem.getDescription());
            viewHolder.mark.setText(markItem.getAverage());
        }
    }

}
