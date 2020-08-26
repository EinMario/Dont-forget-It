package com.example.dontforgetit;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;

public class Dialog extends AppCompatDialogFragment implements View.OnClickListener {
    ArrayList<Product> list;
    private EditText EDIT_PRODUCT, EDIT_AMOUNT, EDIT_STORE;
    private Spinner EDIT_UNIT;
    private Button BTN_EDIT_ADD;
    private DBManager manager;
    ListAdapter adapter;

    public Dialog(DBManager manager, ArrayList<Product> list, ListAdapter adapter) {
        this.manager = manager;
        this.list = list;
        this.adapter = adapter;
    }

    /**
     * Function to create a dialog, where the user generates data
     *
     * @param savedInstanceState saved instance
     * @return updated dialog
     */
    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.layout_additems, null);

        builder.setView(view)
                .setTitle("Liste erweitern")
                .setPositiveButton("Fertig", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!EDIT_PRODUCT.getText().toString().contentEquals("")) {
                            Dialog.this.onClick(view);
                        }
                    }
                });

        EDIT_PRODUCT = view.findViewById(R.id.edit_Product);
        EDIT_AMOUNT = view.findViewById(R.id.edit_Amount);
        EDIT_STORE = view.findViewById(R.id.edit_Store);
        EDIT_UNIT = view.findViewById(R.id.edit_unit);
        BTN_EDIT_ADD = view.findViewById(R.id.button_add);

        BTN_EDIT_ADD.setOnClickListener(this);


        return builder.create();
    }

    /**
     * Handles onclick and inserts items
     *
     * @param view current view
     */
    @Override
    public void onClick(View view) {
        if (!EDIT_PRODUCT.getText().toString().contentEquals("")) {
            Product p = new Product(
                    EDIT_PRODUCT.getText().toString(),
                    EDIT_AMOUNT.getText().toString(),
                    EDIT_UNIT.getSelectedItem(),
                    EDIT_STORE.getText().toString());

            long id = manager.insertListItem(p);
            p.setId(id);

            list.add(p);

            adapter.notifyDataSetInvalidated();
            clear();
        }
    }

    /**
     * Function to clear data after successfull insert
     */
    private void clear() {
        EDIT_PRODUCT.setText("");
        EDIT_AMOUNT.setText("");
        EDIT_UNIT.setSelection(0);
        EDIT_STORE.setText("");
    }

    /**
     * Changed onPause function
     */
    @Override
    public void onPause() {
        super.onPause();
        onClick(this.getView());
    }


}
