package com.kevadiyakrunalk.recycleadapter;

import android.support.annotation.Nullable;
import android.support.v4.util.Pair;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.annotations.Beta;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;

public class RxDataSource {

    private List<Pair<Object, List<Object>>> mDataSet;
    private RxBinderAdapterDemo mAdapter;

    public RxDataSource(List<Pair<Object, List<Object>>> dataSet) {
        this.mDataSet = dataSet;
    }

    @Nullable
    public RxBinderAdapterDemo getAdapter() {
        return mAdapter;
    }

    public Observable<RxBinderAdapterDemo.MyBaseViewHolder> bindRecyclerView(RxBinderAdapterDemo adapter) {
        mAdapter = adapter;
        return mAdapter.asObservable();
    }

    public RxDataSource updateDataSet(List<Pair<Object, List<Object>>> dataSet) {
        mDataSet = dataSet;
        return this;
    }

    public void updateAdapter() {
        if(mAdapter != null) {
            mAdapter.updateDataSet(mDataSet);
        }
    }

    @SuppressWarnings("unchecked")
    public RxDataSource map(Func1<Pair<Object,List<Object>>, Pair<Object,List<Object>>> func) {
        mDataSet = (List<Pair<Object, List<Object>>>) Observable.from(mDataSet).map(func).toBlocking().first();
        return this;
    }

    public RxDataSource filter(Func1<? super Pair<Object, List<Object>>, Boolean> predicate) {
        mDataSet = Observable.from(mDataSet).filter(predicate).toList().toBlocking().first();
        return this;
    }

    public final RxDataSource last() {
        mDataSet = Observable.from(mDataSet).last().toList().toBlocking().first();
        return this;
    }

    public final RxDataSource last(Func1<? super Pair<Object, List<Object>>, Boolean> predicate) {
        mDataSet = Observable.from(mDataSet).last(predicate).toList().toBlocking().first();
        return this;
    }

    public final RxDataSource lastOrDefault(Pair<Object, List<Object>> defaultValue) {
        mDataSet = Observable.from(mDataSet)
                .takeLast(1)
                .singleOrDefault(defaultValue)
                .toList()
                .toBlocking()
                .first();
        return this;
    }

    public final RxDataSource lastOrDefault(Pair<Object, List<Object>> defaultValue,
                                                      Func1<? super Pair<Object, List<Object>>, Boolean> predicate) {
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
            Func1<? super Pair<Object, List<Object>>, ? extends Observable<? extends Pair<Object, List<Object>>>> func) {
        mDataSet = Observable.from(mDataSet).concatMap(func).toList().toBlocking().first();
        return this;
    }

    public final RxDataSource concatWith(Observable<? extends Pair<Object, List<Object>>> t1) {
        mDataSet = Observable.from(mDataSet).concatWith(t1).toList().toBlocking().first();
        return this;
    }

    public RxDataSource distinct() {
        mDataSet = Observable.from(mDataSet).distinct().toList().toBlocking().first();
        return this;
    }

    public RxDataSource distinct(Func1<? super Pair<Object, List<Object>>, ? extends Pair<Object, List<Object>>> keySelector) {
        mDataSet = Observable.from(mDataSet).distinct(keySelector).toList().toBlocking().first();
        return this;
    }

    public final RxDataSource elementAt(int index) {
        mDataSet = Observable.from(mDataSet).elementAt(index).toList().toBlocking().first();
        return this;
    }

    public final RxDataSource elementAtOrDefault(int index, Pair<Object, List<Object>> defaultValue) {
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

    public final RxDataSource first(Func1<? super Pair<Object, List<Object>>, Boolean> predicate) {
        mDataSet = Observable.from(mDataSet).first(predicate).toList().toBlocking().first();
        return this;
    }

    public final RxDataSource firstOrDefault(Pair<Object, List<Object>> defaultValue) {
        mDataSet = Observable.from(mDataSet).firstOrDefault(defaultValue).toList().toBlocking().first();
        return this;
    }

    public final RxDataSource firstOrDefault(Pair<Object, List<Object>> defaultValue,
                                                       Func1<? super Pair<Object, List<Object>>, Boolean> predicate) {
        mDataSet = Observable.from(mDataSet)
                .firstOrDefault(defaultValue, predicate)
                .toList()
                .toBlocking()
                .first();
        return this;
    }

    public final RxDataSource flatMap(
            Func1<? super Pair<Object, List<Object>>, ? extends Observable<? extends Pair<Object, List<Object>>>> func) {
        mDataSet = Observable.from(mDataSet).flatMap(func).toList().toBlocking().first();
        return this;
    }

    @Beta
    public final RxDataSource flatMap(
            Func1<? super Pair<Object, List<Object>>, ? extends Observable<? extends Pair<Object, List<Object>>>> func, int maxConcurrent) {
        mDataSet = Observable.from(mDataSet).flatMap(func, maxConcurrent).toList().toBlocking().first();
        return this;
    }

    public final RxDataSource flatMap(
            Func1<? super Pair<Object, List<Object>>, ? extends Observable<? extends Pair<Object, List<Object>>>> onNext,
            Func1<? super Throwable, ? extends Observable<? extends Pair<Object, List<Object>>>> onError,
            Func0<? extends Observable<? extends Pair<Object, List<Object>>>> onCompleted) {
        mDataSet = Observable.from(mDataSet)
                .flatMap(onNext, onError, onCompleted)
                .toList()
                .toBlocking()
                .first();
        return this;
    }

    @Beta
    public final RxDataSource flatMap(
            Func1<? super Pair<Object, List<Object>>, ? extends Observable<? extends Pair<Object, List<Object>>>> onNext,
            Func1<? super Throwable, ? extends Observable<? extends Pair<Object, List<Object>>>> onError,
            Func0<? extends Observable<? extends Pair<Object, List<Object>>>> onCompleted, int maxConcurrent) {
        mDataSet = Observable.from(mDataSet)
                .flatMap(onNext, onError, onCompleted, maxConcurrent)
                .toList()
                .toBlocking()
                .first();
        return this;
    }

    /*@SuppressWarnings("unchecked")
    public final <U, R> RxDataSource flatMap(
            final Func1<? super Pair<Object, List<Object>>, ? extends Observable<? extends U>> collectionSelector,
            final Func2<? super Pair<Object, List<Object>>, ? super U, ? super Pair<Object, List<Object>>> resultSelector) {
        mDataSet = (List<Pair<Object, List<Object>>>) Observable.from(mDataSet)
                .flatMap(collectionSelector, resultSelector)
                .toList()
                .toBlocking()
                .first();
        return this;
    }*/

    public final RxDataSource flatMapIterable(
            Func1<? super Pair<Object, List<Object>>, ? extends Iterable<? extends Pair<Object, List<Object>>>> collectionSelector) {
        mDataSet = Observable.from(mDataSet).flatMapIterable(collectionSelector).toList().toBlocking().first();
        return this;
    }

    public final RxDataSource reduce(Func2<Pair<Object, List<Object>>, Pair<Object, List<Object>>, Pair<Object, List<Object>>> accumulator) {
        mDataSet = Observable.from(mDataSet).reduce(accumulator).toList().toBlocking().first();
        return this;
    }

    public final RxDataSource reduce(Pair<Object, List<Object>> initialValue,
                                               Func2<Pair<Object, List<Object>>, ? super Pair<Object, List<Object>>, Pair<Object, List<Object>>> accumulator) {
        mDataSet = Observable.from(mDataSet).reduce(initialValue, accumulator).toList().toBlocking().first();
        return this;
    }

    public final RxDataSource repeat(final long count) {
        List<Pair<Object, List<Object>>> dataSet = mDataSet;
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

    public final RxDataSource takeFirst(Func1<? super Pair<Object, List<Object>>, Boolean> predicate) {
        mDataSet = Observable.from(mDataSet).takeFirst(predicate).toList().toBlocking().first();
        return this;
    }

    public final RxDataSource takeLast(final int count) {
        mDataSet = Observable.from(mDataSet).takeLast(count).toList().toBlocking().first();
        return this;
    }
}
