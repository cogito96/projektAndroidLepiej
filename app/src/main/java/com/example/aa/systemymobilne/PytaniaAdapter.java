package com.example.aa.systemymobilne;

import android.app.Activity;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.aa.systemymobilne.model.PytaniaGra;

import java.util.List;


/**
 * Created by aa on 22.01.2018.
 */

public class PytaniaAdapter extends ArrayAdapter <PytaniaGra> {

    private Activity context;
    private List<PytaniaGra> tasks;

    public PytaniaAdapter(Activity context, List<PytaniaGra> pytania) {
        super(context, R.layout.pojedyncze_pytanie, pytania);
        this.context = context;
        this.tasks = pytania;
    }

    static class ViewHolder {
        public TextView tvTodoDescription;
        public TextView tvTodoDescription1;
        public TextView tvTodoDescription2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View rowView = convertView;
        if(rowView == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            rowView = layoutInflater.inflate(R.layout.pojedyncze_pytanie, null, true);
            viewHolder = new ViewHolder();
            viewHolder.tvTodoDescription = (TextView) rowView.findViewById(R.id.tvTodoDescription);
            viewHolder.tvTodoDescription1 = (TextView) rowView.findViewById(R.id.tvTodoDescription1);
            viewHolder.tvTodoDescription2 = (TextView) rowView.findViewById(R.id.tvTodoDescription2);


            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }
        PytaniaGra task = tasks.get(position);
        viewHolder.tvTodoDescription.setText(task.getPytanie());
        viewHolder.tvTodoDescription1.setText(task.getOdpowiedz());
        viewHolder.tvTodoDescription2.setText(task.getKategoria());

        return rowView;
    }

}
