package com.kevadiyakrunalk.myframework.other.adapter;

/**
 * Created by Krunal.Kevadiya on 06/12/16.
 */

public class Items {
    private String text;

    public Items(String msg) {
        text = msg;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Items{" +
                "text='" + text + '\'' +
                '}';
    }
}
