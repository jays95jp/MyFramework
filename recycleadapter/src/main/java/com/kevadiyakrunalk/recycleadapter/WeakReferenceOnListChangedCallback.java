package com.kevadiyakrunalk.recycleadapter;
import android.databinding.ObservableList;
import android.os.Handler;
import android.os.Looper;

import java.lang.ref.WeakReference;

public class WeakReferenceOnListChangedCallback extends ObservableList.OnListChangedCallback<ObservableList<Object>> {
    private WeakReference<CustomBindAdapter> reference;

    public WeakReferenceOnListChangedCallback(CustomBindAdapter adapter) {
        reference = new WeakReference<>(adapter);
    }

    private CustomBindAdapter get() {
        if (Thread.currentThread() == Looper.getMainLooper().getThread())
            return reference.get();
        else
            throw new IllegalStateException("You must modify the ObservableList on the main thread");
    }

    @Override
    public void onChanged(ObservableList<Object> observableList) {
        new Handler(Looper.getMainLooper()).post(() -> {
            if(get() != null)
                get().notifyDataSetChanged();
        });
    }

    @Override
    public void onItemRangeChanged(ObservableList<Object> observableList, int from, int count) {
        new Handler(Looper.getMainLooper()).post(() -> {
            if(get() != null)
                get().notifyItemRangeChanged(from, count);
        });
    }

    @Override
    public void onItemRangeInserted(ObservableList<Object> observableList, int from, int count) {
        new Handler(Looper.getMainLooper()).post(() -> {
            if(get() != null)
                get().notifyItemRangeInserted(from, count);
        });
    }

    @Override
    public void onItemRangeMoved(ObservableList<Object> observableList, int from, int to, int count) {
        new Handler(Looper.getMainLooper()).post(() -> {
            if(get() != null) {
                for (int i=0; i<count-1; i++)
                    get().notifyItemMoved(from + i, to + i);
            }
        });
    }

    @Override
    public void onItemRangeRemoved(ObservableList<Object> observableList, int from, int count) {
        new Handler(Looper.getMainLooper()).post(() -> {
            if(get() != null)
                get().notifyItemRangeRemoved(from, count);
        });
    }
}
