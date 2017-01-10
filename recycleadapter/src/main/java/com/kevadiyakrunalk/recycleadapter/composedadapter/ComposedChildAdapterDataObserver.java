package com.kevadiyakrunalk.recycleadapter.composedadapter;

import android.support.v7.widget.RecyclerView;

import com.kevadiyakrunalk.recycleadapter.adapter.BridgeAdapterDataObserver;

import java.util.ArrayList;
import java.util.List;

class ComposedChildAdapterDataObserver extends BridgeAdapterDataObserver {
    public ComposedChildAdapterDataObserver(Subscriber subscriber, RecyclerView.Adapter sourceAdapter) {
        super(subscriber, sourceAdapter, new ArrayList<ComposedChildAdapterTag>());
    }

    @SuppressWarnings("unchecked")
    private List<ComposedChildAdapterTag> getChildAdapterTags() {
        return (List<ComposedChildAdapterTag>) getTag();
    }

    public void registerChildAdapterTag(ComposedChildAdapterTag tag) {
        getChildAdapterTags().add(tag);
    }

    public void unregisterChildAdapterTag(ComposedChildAdapterTag tag) {
        getChildAdapterTags().remove(tag);
    }

    public boolean hasChildAdapters() {
        return !getChildAdapterTags().isEmpty();
    }

    public void release() {
        getChildAdapterTags().clear();
    }
}
