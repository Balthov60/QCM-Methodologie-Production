package fr.iutmindfuck.qcmiutlyon1.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import fr.iutmindfuck.qcmiutlyon1.R;

public class MarkTeacherListAdapter extends ArrayAdapter<MarkItem> {

    private class ItemViewHolder {
        TextView title;
        TextView description;
        TextView average;

        ItemViewHolder(View convertView) {
            title = convertView.findViewById(R.id.list_item_title);
            description = convertView.findViewById(R.id.list_item_description);
            average = convertView.findViewById(R.id.list_item_average);
        }
    }


    public MarkTeacherListAdapter(Context context, List<MarkItem> list) {
        super(context, 0, list);
    }


    @Override
    @NonNull
    public View getView(final int position, View convertView, @NonNull final ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.mark_teacher_list_item, parent, false);
        }

        initView(convertView, position);
        return convertView;
    }


    private void initView(View convertView, final int position) {
        ItemViewHolder viewHolder = (ItemViewHolder) convertView.getTag();

        if (viewHolder == null)
        {
            viewHolder = new ItemViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        fillInView(viewHolder, position);
    }

    private void fillInView(ItemViewHolder viewHolder, final int position) {
        final MarkItem markItem = getItem(position);

        viewHolder.title.setText(markItem != null ? markItem.getTitle() : null);
        viewHolder.description.setText(markItem != null ? markItem.getDescription() : null);
        viewHolder.average.setText(markItem != null && Float.parseFloat(markItem.getAverage()) >= 0.0f
                ? "Moyenne : " + markItem.getAverage()
                : "Moyenne : Non disponible");
    }

}
