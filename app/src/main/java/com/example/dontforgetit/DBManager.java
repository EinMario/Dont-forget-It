package com.example.dontforgetit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBManager extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String DBNAME = "lists.db";

    public static String TABLE_LIST = "list";
    public static String COLUMN_LIST_COUNTER = "id";
    public static String COLUMN_LIST_ITEM = "item";
    public static String COLUMN_LIST_AMOUNT = "amount";
    public static String COLUMN_LIST_UNIT = "unit";
    public static String COLUMN_LIST_STORE = "store";
    public static String COLUMN_LIST_STATUS = "status";


    public DBManager(Context context) {
        super(context, DBNAME, null, VERSION);
        Log.d("HSKL", "Erstellt");
    }

    /**
     * Generates table in database
     *
     * @param db database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_LIST + " (" +
                COLUMN_LIST_COUNTER + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_LIST_ITEM + " TEXT," +
                COLUMN_LIST_AMOUNT + " TEXT," +
                COLUMN_LIST_UNIT + " TEXT," +
                COLUMN_LIST_STORE + " TEXT," +
                COLUMN_LIST_STATUS + " INTEGER)"
        );

    }

    /**
     * Custom onUpgrade method
     *
     * @param db database
     * @param i  current version
     * @param i1 updated version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST);
        onCreate(db);
    }

    /**
     * Gathers all items from database and creates an Arraylist
     *
     * @return Arraylist of all Products
     */
    private final String getAllString = "SELECT * FROM " + TABLE_LIST;

    public ArrayList<Product> getAll() {
        ArrayList<Product> output = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(getAllString, null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            output.add(new Product(cursor.getInt(cursor.getColumnIndexOrThrow(DBManager.COLUMN_LIST_COUNTER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DBManager.COLUMN_LIST_ITEM)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DBManager.COLUMN_LIST_AMOUNT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DBManager.COLUMN_LIST_UNIT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DBManager.COLUMN_LIST_STORE))));
        }

        return output;
    }

    /**
     * inserts new product to database
     *
     * @param p new product
     * @return
     */
    public long insertListItem(Product p) {
        ContentValues row = new ContentValues();
        row.put(COLUMN_LIST_ITEM, p.getItem());
        row.put(COLUMN_LIST_AMOUNT, p.getAmount());
        row.put(COLUMN_LIST_UNIT, p.getUnit());
        row.put(COLUMN_LIST_STORE, p.getStore());

        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TABLE_LIST, null, row);
    }

    /**
     * updates selected product in database
     *
     * @param p product which will be changed
     */
    public void updateItem(Product p) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_LIST_ITEM, p.getItem());
        values.put(COLUMN_LIST_AMOUNT, p.getAmount());
        values.put(COLUMN_LIST_UNIT, p.getUnit());
        values.put(COLUMN_LIST_STORE, p.getStore());

        String where = COLUMN_LIST_COUNTER + "=?";
        String[] whereArgs = new String[]{Long.toString(p.getId())};

        db.update(TABLE_LIST, values, where, whereArgs);
    }

    /**
     * deletes specified data from database
     *
     * @param p product which will be deleted
     */
    public void deleteItem(Product p) {
        SQLiteDatabase db = this.getWritableDatabase();

        String where = COLUMN_LIST_COUNTER + "=?";
        String[] whereArgs = new String[]{Long.toString(p.getId())};

        db.delete(TABLE_LIST, where, whereArgs);
    }

    /**
     * changes the value of the status
     *
     * @param p selected product
     */
    public void changeStatus(Product p) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_LIST_STATUS, p.getStatus());

        String where = COLUMN_LIST_STATUS + "=?";
        String[] whereArgs ;
        if(p.getStatus()){
            whereArgs = new String[]{"1"};
        }else{
            whereArgs = new String[]{"0"};
        }

        db.update(TABLE_LIST, values, where, whereArgs);
    }
}
