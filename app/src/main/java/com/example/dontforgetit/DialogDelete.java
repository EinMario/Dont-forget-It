package com.example.dontforgetit;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class DialogDelete extends AppCompatDialogFragment  {
    ArrayList<Product> list;
    private DBManager manager;

    ListAdapter adapter;

    public DialogDelete(DBManager manager, ArrayList<Product> list, ListAdapter adapter) {
        this.manager = manager;
        this.list = list;
        this.adapter = adapter;

    }

    /**
     * Function to create a dialog, where the user deletes all data
     *
     * @param savedInstanceState saved instance
     * @return updated dialog
     */
    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_dialog_delete, null);

        builder.setView(view)
                .setTitle("Alle Einträge löschen")
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(Product p : list){
                            manager.deleteItem(p);
                        }
                        list.clear();
                        ((MainActivity) getActivity()).updateList(list);
                        adapter.notifyDataSetInvalidated();
                    }
                })
                .setNegativeButton("Nein",  new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               return;
                            }
                        }
                );

        return builder.create();

    }

    /**
     * Handles onclick and deletes all items one by one
     *
     * @param view current view
     */


    /**
     * Changed onPause function
     */
    @Override
    public void onPause() {
        super.onPause();
    }


}


