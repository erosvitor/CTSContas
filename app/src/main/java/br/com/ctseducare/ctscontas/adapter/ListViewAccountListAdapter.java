package br.com.ctseducare.ctscontas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Locale;

import br.com.ctseducare.ctscontas.R;
import br.com.ctseducare.ctscontas.enumerator.AccountTypeEnum;
import br.com.ctseducare.ctscontas.dto.AccountDTO;

public class ListViewAccountListAdapter extends ArrayAdapter<AccountDTO> {

    private Context context;
    private List<AccountDTO> dataSet;

    private class ViewHolder {
        TextView account;
        TextView lblDueDate;
        TextView dueDate;
        TextView lblDueValue;
        TextView dueValue;
        TextView paymentDate;
        TextView paymentValue;
        TextView description;
    }

    public ListViewAccountListAdapter(List<AccountDTO> dataSet, Context context) {
        super(context, R.layout.listview_account_list, dataSet);
        this.context = context;
        this.dataSet = dataSet;
    }

    @Override
    public @NonNull View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.listview_account_list, parent, false);
            viewHolder.account = convertView.findViewById(R.id.fldListViewAccountsAccount);
            viewHolder.lblDueDate = convertView.findViewById(R.id.lblListViewAccountsDueDate);
            viewHolder.dueDate = convertView.findViewById(R.id.fldListViewAccountsDueDate);
            viewHolder.lblDueValue = convertView.findViewById(R.id.lblListViewAccountsDueValue);
            viewHolder.dueValue = convertView.findViewById(R.id.fldListViewAccountsDueValue);
            viewHolder.paymentDate = convertView.findViewById(R.id.fldListViewAccountsPaymentDate);
            viewHolder.paymentValue = convertView.findViewById(R.id.fldListViewAccountsPaymentValue);
            viewHolder.description = convertView.findViewById(R.id.fldListViewAccountsDescription);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        if (dataSet != null) {
            AccountDTO accountVO = getItem(position);
            if (accountVO != null) {
                String account = accountVO.category + " - " + accountVO.subcategory;
                viewHolder.account.setText(account);
                if (accountVO.type.equals(AccountTypeEnum.FIX.getDescription())) {
                    viewHolder.dueDate.setText(accountVO.dueDate);
                    viewHolder.dueValue.setText(String.format(Locale.getDefault(), "%.2f", accountVO.dueValue));
                } else {
                    viewHolder.lblDueDate.setVisibility(View.GONE);
                    viewHolder.dueDate.setVisibility(View.GONE);
                    viewHolder.lblDueValue.setVisibility(View.GONE);
                    viewHolder.dueValue.setVisibility(View.GONE);
                }
                viewHolder.paymentDate.setText(accountVO.paymentDate);
                viewHolder.paymentValue.setText(String.format(Locale.getDefault(), "%.2f", accountVO.paymentValue));
                viewHolder.description.setText(accountVO.description);
            }
        }

        return convertView;
    }

}
