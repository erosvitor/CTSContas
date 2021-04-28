package br.com.ctseducare.ctscontas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.NumberFormat;
import java.util.List;

import br.com.ctseducare.ctscontas.R;
import br.com.ctseducare.ctscontas.dto.PaymentCategoryDTO;

public class ListViewPaymentsPerCategoryAdapter extends ArrayAdapter<PaymentCategoryDTO> {

    private Context context;
    private List<PaymentCategoryDTO> dataSet;

    private class ViewHolder {
        TextView category;
        TextView value;
    }

    public ListViewPaymentsPerCategoryAdapter(List<PaymentCategoryDTO> dataSet, Context context) {
        super(context, R.layout.listview_payments_per_category, dataSet);
        this.context = context;
        this.dataSet = dataSet;
    }

    @Override
    public @NonNull
    View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ListViewPaymentsPerCategoryAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ListViewPaymentsPerCategoryAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.listview_payments_per_category, parent, false);
            viewHolder.category = convertView.findViewById(R.id.lblListViewPaymentsPerCategoryCategory);
            viewHolder.value = convertView.findViewById(R.id.lblListViewPaymentsPerCategoryValue);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ListViewPaymentsPerCategoryAdapter.ViewHolder)convertView.getTag();
        }

        if (dataSet != null) {
            PaymentCategoryDTO item = getItem(position);
            if (item != null) {
                viewHolder.category.setText(item.category);
                NumberFormat nf = NumberFormat.getCurrencyInstance();
                viewHolder.value.setText(nf.format(item.value));
            }
        }

        return convertView;
    }



}
