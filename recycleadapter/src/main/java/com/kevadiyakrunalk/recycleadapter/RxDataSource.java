package com.kevadiyakrunalk.recycleadapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Scheduler;
import rx.annotations.Beta;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by ahmedrizwan on 26/12/2015.
 */
public class RxDataSource {

    private List<Object> mDataSet;
    private RxBinderAdapter mAdapter;

    public RxDataSource(List<Object> dataSet) {
        this.mDataSet = dataSet;
    }

    @Nullable
    public RxBinderAdapter getAdapter() {
        return mAdapter;
    }

    public Observable<RxBinderAdapter.ViewHolder> bindRecyclerView(RxBinderAdapter adapter) {
        mAdapter = adapter;
        return mAdapter.asObservable();
    }

    /***
     * For setting base dataSet
     */
    public RxDataSource updateDataSet(List<Object> dataSet) {
        mDataSet = dataSet;
        return this;
    }

    /***
     * For updating Adapter
     */
    public void updateAdapter() {
        if(mAdapter != null) {
            mAdapter.updateDataSet(mDataSet);
        }
    }

    public RxDataSource map(Func1<? super Object, ? super Object> func) {
        mDataSet = Observable.from(mDataSet).map(func).toList().toBlocking().first();
        return this;
    }

    public RxDataSource filter(Func1<? super Object, Boolean> predicate) {
        mDataSet = Observable.from(mDataSet).filter(predicate).toList().toBlocking().first();
        return this;
    }

    public final RxDataSource last() {
        mDataSet = Observable.from(mDataSet).last().toList().toBlocking().first();
        return this;
    }

    public final RxDataSource last(Func1<? super Object, Boolean> predicate) {
        mDataSet = Observable.from(mDataSet).last(predicate).toList().toBlocking().first();
        return this;
    }

    public final RxDataSource lastOrDefault(Object defaultValue) {
        mDataSet = Observable.from(mDataSet)
                .takeLast(1)
                .singleOrDefault(defaultValue)
                .toList()
                .toBlocking()
                .first();
        return this;
    }

    public final RxDataSource lastOrDefault(Object defaultValue,
                                                      Func1<? super Object, Boolean> predicate) {
        mDataSet = Observable.from(mDataSet)
                .filter(predicate)
                .takeLast(1)
                .singleOrDefault(defaultValue)
                .toList()
                .toBlocking()
                .first();
        return this;
    }

    public final RxDataSource limit(int count) {
        mDataSet = Observable.from(mDataSet).limit(count).toList().toBlocking().first();
        return this;
    }

    public RxDataSource empty() {
        mDataSet = Collections.emptyList();
        return this;
    }

    public final <R> RxDataSource concatMap(
            Func1<? super Object, ? extends Observable<? extends Object>> func) {
        mDataSet = Observable.from(mDataSet).concatMap(func).toList().toBlocking().first();
        return this;
    }

    public final RxDataSource concatWith(Observable<? extends Object> t1) {
        mDataSet = Observable.from(mDataSet).concatWith(t1).toList().toBlocking().first();
        return this;
    }

    public RxDataSource distinct() {
        mDataSet = Observable.from(mDataSet).distinct().toList().toBlocking().first();
        return this;
    }

    public RxDataSource distinct(Func1<? super Object, ? extends Object> keySelector) {
        mDataSet = Observable.from(mDataSet).distinct(keySelector).toList().toBlocking().first();
        return this;
    }

    public final RxDataSource elementAt(int index) {
        mDataSet = Observable.from(mDataSet).elementAt(index).toList().toBlocking().first();
        return this;
    }

    public final RxDataSource elementAtOrDefault(int index, Object defaultValue) {
        mDataSet = Observable.from(mDataSet)
                .elementAtOrDefault(index, defaultValue)
                .toList()
                .toBlocking()
                .first();
        return this;
    }

    public final RxDataSource first() {
        mDataSet = Observable.from(mDataSet).first().toList().toBlocking().first();
        return this;
    }

    public final RxDataSource first(Func1<? super Object, Boolean> predicate) {
        mDataSet = Observable.from(mDataSet).first(predicate).toList().toBlocking().first();
        return this;
    }

    public final RxDataSource firstOrDefault(Object defaultValue) {
        mDataSet = Observable.from(mDataSet).firstOrDefault(defaultValue).toList().toBlocking().first();
        return this;
    }

    public final RxDataSource firstOrDefault(Object defaultValue,
                                                       Func1<? super Object, Boolean> predicate) {
        mDataSet = Observable.from(mDataSet)
                .firstOrDefault(defaultValue, predicate)
                .toList()
                .toBlocking()
                .first();
        return this;
    }

    public final RxDataSource flatMap(
            Func1<? super Object, ? extends Observable<? extends Object>> func) {
        mDataSet = Observable.from(mDataSet).flatMap(func).toList().toBlocking().first();
        return this;
    }

    @Beta
    public final RxDataSource flatMap(
            Func1<? super Object, ? extends Observable<? extends Object>> func, int maxConcurrent) {
        mDataSet = Observable.from(mDataSet).flatMap(func, maxConcurrent).toList().toBlocking().first();
        return this;
    }

    public final RxDataSource flatMap(
            Func1<? super Object, ? extends Observable<? extends Object>> onNext,
            Func1<? super Throwable, ? extends Observable<? extends Object>> onError,
            Func0<? extends Observable<? extends Object>> onCompleted) {
        mDataSet = Observable.from(mDataSet)
                .flatMap(onNext, onError, onCompleted)
                .toList()
                .toBlocking()
                .first();
        return this;
    }

    @Beta
    public final RxDataSource flatMap(
            Func1<? super Object, ? extends Observable<? extends Object>> onNext,
            Func1<? super Throwable, ? extends Observable<? extends Object>> onError,
            Func0<? extends Observable<? extends Object>> onCompleted, int maxConcurrent) {
        mDataSet = Observable.from(mDataSet)
                .flatMap(onNext, onError, onCompleted, maxConcurrent)
                .toList()
                .toBlocking()
                .first();
        return this;
    }

    public final <U, R> RxDataSource flatMap(
            final Func1<? super Object, ? extends Observable<? extends U>> collectionSelector,
            final Func2<? super Object, ? super U, ? super Object> resultSelector) {
        mDataSet = Observable.from(mDataSet)
                .flatMap(collectionSelector, resultSelector)
                .toList()
                .toBlocking()
                .first();
        return this;
    }

    public final RxDataSource flatMapIterable(
            Func1<? super Object, ? extends Iterable<? extends Object>> collectionSelector) {
        mDataSet =
                Observable.from(mDataSet).flatMapIterable(collectionSelector).toList().toBlocking().first();
        return this;
    }

    public final RxDataSource reduce(Func2<Object, Object, Object> accumulator) {
        mDataSet = Observable.from(mDataSet).reduce(accumulator).toList().toBlocking().first();
        return this;
    }

    public final RxDataSource reduce(Object initialValue,
                                               Func2<Object, ? super Object, Object> accumulator) {
        mDataSet =
                Observable.from(mDataSet).reduce(initialValue, accumulator).toList().toBlocking().first();
        return this;
    }

    public final RxDataSource repeat(final long count) {
        List<Object> dataSet = mDataSet;
        mDataSet = Observable.from(dataSet).repeat(count).toList().toBlocking().first();
        return this;
    }

    public final RxDataSource repeat(final long count, Scheduler scheduler) {
        mDataSet = Observable.from(mDataSet).repeat(count, scheduler).toList().toBlocking().first();
        return this;
    }

    public final RxDataSource take(final int count) {
        mDataSet = Observable.from(mDataSet).take(count).toList().toBlocking().first();
        return this;
    }

    public final RxDataSource takeFirst(Func1<? super Object, Boolean> predicate) {
        mDataSet = Observable.from(mDataSet).takeFirst(predicate).toList().toBlocking().first();
        return this;
    }

    public final RxDataSource takeLast(final int count) {
        mDataSet = Observable.from(mDataSet).takeLast(count).toList().toBlocking().first();
        return this;
    }
}
