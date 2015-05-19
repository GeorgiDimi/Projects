package com.example.georgidimitrov.wallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by georgidimitrov on 15-05-12.
 */
public class CustomListAdapter extends BaseAdapter {

    private ArrayList<CardItem> listData;
    private LayoutInflater layoutInflater;

    public CustomListAdapter(Context context, ArrayList<CardItem> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.card_layout, null);
            holder = new ViewHolder();
            holder.cardName = (TextView) convertView.findViewById(R.id.cardName);
            holder.cardE1 = (TextView) convertView.findViewById(R.id.cardE1);
            holder.cardE1_1 = (TextView) convertView.findViewById(R.id.cardE1_1);
            holder.cardE2 = (TextView) convertView.findViewById(R.id.cardE2);
            holder.cardE2_1 = (TextView) convertView.findViewById(R.id.cardE2_1);
            holder.cardNumber = (TextView) convertView.findViewById(R.id.cardNumber);
            holder.cardImage1 = (ImageView) convertView.findViewById(R.id.cardImage1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CardItem ci = listData.get(position);
        holder.cardName.setText(ci.getName());
        holder.cardE1.setText(ci.getExtra1());
        holder.cardE1_1.setText(ci.getExtra1_1());
        holder.cardE2.setText(ci.getExtra2());
        holder.cardE2_1.setText(ci.getExtra2_1());
        holder.cardNumber.setText(ci.getNumber());
        holder.cardImage1.setImageResource(R.mipmap.ic_launcher);

        return convertView;
    }

    static class ViewHolder {
        TextView cardName;
        TextView cardE1;
        TextView cardE1_1;
        TextView cardE2;
        TextView cardE2_1;
        TextView cardNumber;
        ImageView cardImage1;
    }
}
