package com.example.dontforgetit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Product> {

    private  Context ctx;
    private int resource;


    public ListAdapter(Context ctx, int resource, ArrayList<Product> products) {
        super(ctx,resource,products);
        this.ctx=ctx;
        this.resource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String item = getItem(position).getItem();
        String amount = getItem(position).getAmount();
        String unit = getItem(position).getUnit();
        String store = getItem(position).getStore();
        boolean status = getItem(position).getStatus();

        LayoutInflater inflater = LayoutInflater.from(ctx);
        convertView = inflater.inflate(resource,parent,false);

        TextView tvItem = convertView.findViewById(R.id.layout_item);
        TextView tvAmount = convertView.findViewById(R.id.layout_amount);
        TextView tvUnit = convertView.findViewById(R.id.layout_unit);
        TextView tvStore = convertView.findViewById(R.id.layout_Store);

        tvItem.setText(item);
        tvAmount.setText(amount);
        tvUnit.setText(unit);
        tvStore.setText(store);

        if (status) {
            tvItem.setEnabled(false);
            tvAmount.setEnabled(false);
            tvUnit.setEnabled(false);
            tvStore.setEnabled(false);


        } else {
            tvItem.setEnabled(true);
            tvAmount.setEnabled(true);
            tvUnit.setEnabled(true);
            tvStore.setEnabled(true);


        }


        return convertView;

    }


}
