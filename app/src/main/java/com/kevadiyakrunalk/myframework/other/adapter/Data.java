package com.kevadiyakrunalk.myframework.other.adapter;

import android.databinding.ObservableArrayList;

public class Data {
    private int index = 1;
    private ObservableArrayList<Object> items = new ObservableArrayList<>();

    public Data() {
        /*items.add(new Header("Header " + index));
        items.add(new Items("Item (" + index + "," + "1)"));
        index++;
        items.add(new Header("Header " + index));
        items.add(new Items("Item (" + index + "," + "1)"));
        items.add(new Items("Item (" + index + "," + "2)"));
        index++;
        items.add(new Header("Header " + index));
        items.add(new Items("Item (" + index + "," + "1)"));
        items.add(new Items("Item (" + index + "," + "2)"));
        items.add(new Items("Item (" + index + "," + "3)"));
        index++;
        items.add(new Header("Header " + index));
        items.add(new Items("Item (" + index + "," + "1)"));
        items.add(new Items("Item (" + index + "," + "1)"));
        items.add(new Items("Item (" + index + "," + "1)"));
        items.add(new Items("Item (" + index + "," + "1)"));
        index++;
        items.add(new Header("Header " + index));
        items.add(new Items("Item (" + index + "," + "1)"));
        items.add(new Items("Item (" + index + "," + "2)"));
        items.add(new Items("Item (" + index + "," + "3)"));
        items.add(new Items("Item (" + index + "," + "4)"));
        items.add(new Items("Item (" + index + "," + "5)"));*/
    }

    public ObservableArrayList<Object> getMoreData() {
        index++;
        ObservableArrayList<Object> list = new ObservableArrayList<>();
        /*list.add(new Header("Header " + index));
        list.add(new Items("Item (" + index + "," + "1)"));
        list.add(new Items("Item (" + index + "," + "2)"));
        list.add(new Items("Item (" + index + "," + "3)"));
        list.add(new Items("Item (" + index + "," + "4)"));
        list.add(new Items("Item (" + index + "," + "5)"));*/
        return list;
    }

    public ObservableArrayList<Object> getItems() {
        return items;
    }
}
