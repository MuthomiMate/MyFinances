package com.creativearts.muthomi.timetable1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import static java.security.AccessController.getContext;

/**
 * Created by Eduh_mik on 6/18/2017.
 */

public class AccountsAdapter  extends ArrayAdapter<MainClass> {

    private ArrayAdapter<String> listAdapter;

    public AccountsAdapter(Context context) {
        super(context, 0);
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.deposit, null);
        }
        TextView reg = (TextView) convertView.findViewById(R.id.datemgy);
        TextView date = (TextView) convertView.findViewById(R.id.amountmnn);
        TextView option=(TextView) convertView.findViewById(R.id.optionmate) ;
//        TextView receipt = (TextView) convertView.findViewById(R.id.textReceiptno);
//        TextView amount = (TextView) convertView.findViewById(R.id.textTotals);
//        TextView pbefore = (TextView) convertView.findViewById(R.id.textPbefore);
//        TextView pafter = (TextView) convertView.findViewById(R.id.textAfter);

        reg.setText(getItem(position).getAmount_deposited());
        date.setText(getItem(position).getDate_of_deposit());
        option.setText(getItem(position).getOption_deposit());
//        receipt.setText("Receipt No: "+getItem(position).getReceiptno());
//        amount.setText("Amount used: "+getItem(position).getTotal());
//        pbefore.setText("Prepaid before: "+getItem(position).getPbefore());
//        pafter.setText("Prepaid after: "+getItem(position).getPafter());



        return convertView;
    }
}
