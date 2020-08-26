package com.example.dontforgetit;

public class Product {
    private long id;
    private String item;
    private String amount;
    private String unit;
    private String store;
    private boolean status;

    public Product(String item, String amount, Object unit, String store) {
        this.item = item;
        this.amount = amount;
        this.unit = unit.toString();
        this.store = store;
        status = false;
    }

    public Product(String item, String amount, String unit, String store) {
        this.item = item;
        this.amount = amount;
        this.unit = unit;
        this.store = store;
        status = false;
    }

    public Product(int id, String item, String amount, String unit, String store) {
        this.id = id;
        this.item = item;
        this.amount = amount;
        this.unit = unit;
        this.store = store;
        status = false;
    }

    public Product(long id, String item, String amount, String unit, String store) {
        this.id = id;
        this.item = item;
        this.amount = amount;
        this.unit = unit;
        this.store = store;
        status = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
