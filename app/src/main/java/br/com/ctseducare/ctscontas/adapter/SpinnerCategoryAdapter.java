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
import br.com.ctseducare.ctscontas.model.Category;

public class SpinnerCategoryAdapter extends ArrayAdapter<Category> {

    private Context context;
    private List<Category> dataSet;

    private class ViewHolder {
        TextView description;
    }

    public SpinnerCategoryAdapter(List<Category> dataSet, Context context) {
        super(context, R.layout.spinner_category, dataSet);
        this.context = context;
        this.dataSet = dataSet;
    }

    @Override
    public @NonNull View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new SpinnerCategoryAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.spinner_category, parent, false);
            viewHolder.description = convertView.findViewById(R.id.fldSpinnerCategoryDescription);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SpinnerCategoryAdapter.ViewHolder)convertView.getTag();
        }

        if (dataSet != null) {
            Category category = getItem(position);
            if (category != null) {
                viewHolder.description.setText(category.getDescription());
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
