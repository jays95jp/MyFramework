package com.kevadiyakrunalk.myframework.other.adapter;

import android.databinding.ObservableArrayList;

public class Data {
    private ObservableArrayList<Object> items = new ObservableArrayList<>();

    public Data() {
        items.add(new Header("Header 1"));
        items.add(new Point(1, 1));
        items.add(new Header("Header 2"));
        items.add(new Point(2, 1));
        items.add(new Point(2, 2));
        items.add(new Header("Header 3"));
        items.add(new Point(3, 1));
        items.add(new Point(3, 2));
        items.add(new Point(3, 3));
        items.add(new Header("Header 4"));
        items.add(new Point(4, 1));
        items.add(new Point(4, 2));
        items.add(new Point(4, 3));
        items.add(new Point(4, 4));
        items.add(new Header("Header 5"));
        items.add(new Point(5, 1));
        items.add(new Point(5, 2));
        items.add(new Point(5, 3));
        items.add(new Point(5, 4));
        items.add(new Point(5, 5));
    }

    public void setMoreData() {
        items.add(new Header("Header 6"));
        items.add(new Point(6, 1));
        items.add(new Point(6, 2));
        items.add(new Point(6, 3));
        items.add(new Point(6, 4));
        items.add(new Point(6, 5));
    }

    public ObservableArrayList<Object> getItems() {
        return items;
    }
}
