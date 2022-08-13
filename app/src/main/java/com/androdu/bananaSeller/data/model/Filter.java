package com.androdu.bananaSeller.data.model;

public class Filter implements Cloneable {
    String name;
    boolean isChecked = false;
    int key;

    public Filter(String name, int key) {
        this.name = name;
        this.key = key;
    }

    public Filter(String name, boolean isChecked, int key) {
        this.name = name;
        this.isChecked = isChecked;
        this.key = key;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    // Overriding the inbuilt clone class
    @Override
    public Filter clone() {
        try {
            return (Filter) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return new Filter("default", 0);
    }
}
