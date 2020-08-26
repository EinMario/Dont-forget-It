package com.example.dontforgetit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DBManager manager;
    ListView VIEW;
    public ArrayList<Product> list;
    ListAdapter adapter;
    public ArrayList<Product> toDelete = new ArrayList<>();
    /**
     * Main method which maps click events and connects everything
     *
     * @param  savedInstanceState   saved instance
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VIEW = findViewById(R.id.view_list);

        VIEW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView item = view.findViewById(R.id.layout_item);
                TextView amount = view.findViewById(R.id.layout_amount);
                TextView unit = view.findViewById(R.id.layout_unit);
                TextView store = view.findViewById(R.id.layout_Store);

                Product p = list.get(position);

                if (item.isEnabled()) {
                    item.setEnabled(false);
                    amount.setEnabled(false);
                    unit.setEnabled(false);
                    store.setEnabled(false);

                    p.setStatus(true);
                } else {
                    item.setEnabled(true);
                    amount.setEnabled(true);
                    unit.setEnabled(true);
                    store.setEnabled(true);

                    p.setStatus(false);
                }

                manager.changeStatus(p);
            }
        });


        VIEW.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ItemsMenu menu = new ItemsMenu(list.get(i), manager, list,adapter);
                menu.show(getSupportFragmentManager(), null);

                return true;
            }
        });


        manager = new DBManager(this);


        list = manager.getAll();
        adapter = new ListAdapter(this, R.layout.layout_for_items, list);

        VIEW.setAdapter(adapter);

        //FAB
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        FloatingActionButton fab_delete = findViewById(R.id.fab2);
        fab_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogDelete();
            }
        });

    }

    public void updateList(List<Product> list){
        this.list = (ArrayList<Product>) list;
    }


    /**
     * Function to open a dialog
     *
     */
    public void openDialogDelete() {
        DialogDelete dialog = new DialogDelete(manager,list,adapter);
        dialog.show(getSupportFragmentManager(),"delete");
    }

    /**
     * Function to open a dialog
     *
     */
    public void openDialog() {
        Dialog dialog = new Dialog(manager,list,adapter);
        dialog.show(getSupportFragmentManager(), "Test");
    }
    /**
     * Overwritten method to close DBManager before destroying the app
     *
     */
    @Override
    protected void onDestroy() {
        manager.close();
        super.onDestroy();
    }
    /**
     * Overwritten method to close DBManager before pausing the app
     *
     */
    @Override
    protected void onPause() {
        manager.close();
        super.onPause();
    }

}