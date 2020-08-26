package com.example.dontforgetit;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.List;
import java.util.Objects;

public class ItemsMenu extends AppCompatDialogFragment {
    ListAdapter adapter;
    List<Product> list;
    DBManager manager;
    Product product;
    EditText MENU_ITEM;
    EditText MENU_AMOUNT;
    Spinner MENU_UNIT;
    EditText MENU_STORE;

    public ItemsMenu(Product p, DBManager manager, List<Product> list, ListAdapter adapter) {
        this.product = p;
        this.manager = manager;
        this.list = list;
        this.adapter = adapter;
    }

    @NonNull
    @Override
    /**
     * Function to create a dialog, where the user updates/deletes clicked data
     *
     * @param  savedInstanceState   saved instance
     * @return updated dialog
     */
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.layout_items_menu_long, null);

        builder.setView(view).setTitle("Eintrag bearbeiten")
                .setPositiveButton("Fertig", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        buttonClickHandler(i);
                    }
                })
                .setNegativeButton("Eintrag löschen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        buttonClickHandler(i);
                    }
                });

        //BIND
        MENU_ITEM = view.findViewById(R.id.menu_item);
        MENU_AMOUNT = view.findViewById(R.id.menu_amount);
        MENU_UNIT = view.findViewById(R.id.menu_unit);
        MENU_STORE = view.findViewById(R.id.menu_store);


        MENU_ITEM.setText(product.getItem());
        MENU_AMOUNT.setText(product.getAmount()+" ");
        MENU_UNIT.getSelectedItem();
        MENU_STORE.setText(product.getStore());

        return builder.create();
    }

    /**
     * Handles the click event
     *
     * @param i id of selected button
     *          1=>positiv; 2=>negativ
     */
    private void buttonClickHandler(int i) {
        long id = product.getId();

        if (i == DialogInterface.BUTTON_POSITIVE) {
            //POSITIV
            Product updatedProduct = new Product(product.getId(),
                    MENU_ITEM.getText().toString(),
                    MENU_AMOUNT.getText().toString(),
                    MENU_UNIT.getSelectedItem().toString(),
                    MENU_STORE.getText().toString());

            manager.updateItem(updatedProduct);

            for (Product p : list) {
                if (p.getId() == id) {
                    p = updatedProduct;

                    product.setItem(p.getItem());
                    product.setAmount(p.getAmount());
                    product.setUnit(p.getUnit());
                    product.setStore(p.getStore());
                    adapter.notifyDataSetInvalidated();

                }
            }

            adapter.notifyDataSetInvalidated();
        } else if (i == DialogInterface.BUTTON_NEGATIVE) {
            //NEGATIV
            manager.deleteItem(product);

            for (Product p : list) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    if (Objects.equals(p.getId(), id)) {
                        list.remove(p);
                        ((MainActivity) getActivity()).updateList(list);
                        adapter.notifyDataSetInvalidated();
                        return;
                    }
                }
            }
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}



