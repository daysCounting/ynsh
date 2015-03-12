package com.bank.root.myapplication.ListView;

/**
 * Created by root on 15-3-10.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bank.root.myapplication.R;

import java.util.List;


public class CardsAdapter extends BaseAdapter {

    private List<String> items;
    private final OnClickListener itemButtonClickListener;
    private final Context context;

    public CardsAdapter(Context context, List<String> items,
                        OnClickListener itemButtonClickListener) {
        this.context = context;
        this.items = items;
        this.itemButtonClickListener = itemButtonClickListener;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public String getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.list_item_card, null);

            holder = new ViewHolder();
            holder.itemText = (TextView) convertView
                    .findViewById(R.id.list_item_card_text);
            holder.itemButton1 = (Button) convertView
                    .findViewById(R.id.list_item_card_button_1);
            holder.itemButton2 = (Button) convertView
                    .findViewById(R.id.list_item_card_button_2);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemText.setText(items.get(position));


        holder.itemButton1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        context,
                        "Clicked on Left Action Button of List Item "
                                + position, Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemButton2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        context,
                        "Clicked on right Action Button of List Item "
                                + position, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        private TextView itemText;
        private Button itemButton1;
        private Button itemButton2;
    }

}
