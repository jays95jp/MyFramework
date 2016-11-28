package com.kevadiyakrunalk.myframework.other.adapter;

public class Header {
    private String text;

    public Header(String txt) {
        text = txt;
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
