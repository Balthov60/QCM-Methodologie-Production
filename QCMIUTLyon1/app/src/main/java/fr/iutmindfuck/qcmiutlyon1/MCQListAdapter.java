package fr.iutmindfuck.qcmiutlyon1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.iutmindfuck.qcmiutlyon1.data.MCQ;

/**
 * Created by sntri on 04/12/2017.
 */
public class MCQListAdapter extends ArrayAdapter<MCQ> {

    public MCQListAdapter(Context context, List<MCQ> mcqList){
        super(context, 0, mcqList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_row_item_mcq,parent, false);
        }

        MCQViewHolder viewHolder = (MCQViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new MCQViewHolder();
            viewHolder.id = (TextView) convertView.findViewById(R.id.id_mcq);
            viewHolder.mcq_name = (TextView) convertView.findViewById(R.id.mcq_name);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        MCQ mcq = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.id.setText("QCM " + mcq.getName());
        viewHolder.mcq_name.setText(mcq.getDescription());

        return convertView;
    }

    private class MCQViewHolder{
        public TextView id;
        public TextView mcq_name;
    }
}