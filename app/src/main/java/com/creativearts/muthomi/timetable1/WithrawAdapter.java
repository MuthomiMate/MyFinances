package com.creativearts.muthomi.timetable1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Eduh_mik on 6/19/2017.
 */

public class WithrawAdapter extends ArrayAdapter<MainClass> {

    private ArrayAdapter<String> listAdapter;

    public WithrawAdapter(Context context) {
        super(context, 0);
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.withdraw, null);
        }
        TextView reg = (TextView) convertView.findViewById(R.id.datemff);
        TextView date = (TextView) convertView.findViewById(R.id.amountmww);
        TextView option = (TextView) convertView.findViewById(R.id.option);
        TextView desc=(TextView)convertView.findViewById(R.id.description);
//        TextView receipt = (TextView) convertView.findViewById(R.id.textReceiptno);
//        TextView amount = (TextView) convertView.findViewById(R.id.textTotals);
//        TextView pbefore = (TextView) convertView.findViewById(R.id.textPbefore);
//        TextView pafter = (TextView) convertView.findViewById(R.id.textAfter);

        reg.setText(getItem(position).getAmount_withdrawn());
        date.setText(getItem(position).getDate_of_withdraw());
        option.setText(getItem(position).getOption_withdraw());
        desc.setText("Description: "+getItem(position).getDescription());
//        receipt.setText("Receipt No: "+getItem(position).getReceiptno());
//        amount.setText("Amount used: "+getItem(position).getTotal());
//        pbefore.setText("Prepaid before: "+getItem(position).getPbefore());
//        pafter.setText("Prepaid after: "+getItem(position).getPafter());


        return convertView;
    }

}