package br.com.ctseducare.ctscontas.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import br.com.ctseducare.ctscontas.R;
import br.com.ctseducare.ctscontas.model.Subcategory;

public class SpinnerSubcategoryAdapter extends ArrayAdapter<Subcategory> {

    private Context context;
    private List<Subcategory> dataSet;

    private class ViewHolder {
        TextView description;
    }

    public SpinnerSubcategoryAdapter(List<Subcategory> dataSet, Context context) {
        super(context, R.layout.spinner_subcategory, dataSet);
        this.context = context;
        this.dataSet = dataSet;
    }

    @Override
    public @NonNull
    View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SpinnerSubcategoryAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new SpinnerSubcategoryAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.spinner_subcategory, parent, false);
            viewHolder.description = convertView.findViewById(R.id.fldSpinnerSubcategoryDescription);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SpinnerSubcategoryAdapter.ViewHolder)convertView.getTag();
        }

        if (dataSet != null) {
            Subcategory subcategory = getItem(position);
            if (subcategory != null) {
                viewHolder.description.setText(subcategory.getDescription());
            }
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(dataSet.get(position).getDescription());
        return label;
    }

}
