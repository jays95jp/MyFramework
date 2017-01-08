package com.kevadiyakrunalk.myframework.other.adapter;

import com.kevadiyakrunalk.recycleadapter.utility.ChildData;
import com.kevadiyakrunalk.recycleadapter.utility.DataUtils;

/**
 * Created by Krunal.Kevadiya on 06/12/16.
 */

public class Items extends ChildData{
    private long mId;
    private String text;
    private int mPinned;

    public Items(long id, String msg) {
        mId = id;
        text = msg;
        mPinned = DataUtils.SWAP_NONE;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public long getChildId() {
        return mId;
    }

    @Override
    public void setPinned(int pinned) {
        mPinned = pinned;
    }

    @Override
    public int isPinned() {
        return mPinned;
    }

    @Override
    public String toString() {
        return "Items{" +
                "mId=" + mId +
                ", text='" + text + '\'' +
                ", mPinned=" + mPinned +
                '}';
    }
}
