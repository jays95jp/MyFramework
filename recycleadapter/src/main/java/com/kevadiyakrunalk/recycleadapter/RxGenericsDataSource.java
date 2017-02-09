package com.kevadiyakrunalk.recycleadapter;

import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.annotations.Beta;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;

public class RxGenericsDataSource<DataType> {
    private List<DataType> mDataSet;
    private RxGenericsAdapter mRxAdapter;

    public RxGenericsDataSource(List<DataType> dataSet) {
        this.mDataSet = dataSet;
    }

    public Observable<RxGenericsAdapter.MyBaseViewHolder> bindRecyclerView(RxGenericsAdapter adapter) {
        mRxAdapter = adapter;
        return mRxAdapter.asObservable();
    }

    public RxGenericsAdapter getRxAdapter() {
        return mRxAdapter;
    }

    public RxGenericsDataSource<DataType> updateDataSet(List<DataType> dataSet) {
        mDataSet = dataSet;
        return this;
    }

    public void updateAdapter() {
        if (mRxAdapter != null) {
            //update the update
            mRxAdapter.updateDataSet(mDataSet);
        }
    }

    @SuppressWarnings("unchecked")
    public RxGenericsDataSource<DataType> map(Func1<? super DataType, ? extends DataType> func) {
        mDataSet = (List<DataType>) Observable.from(mDataSet).map(func).toList().toBlocking().first();
        return this;
    }

    public RxGenericsDataSource<DataType> filter(Func1<? super DataType, Boolean> predicate) {
        mDataSet = Observable.from(mDataSet).filter(predicate).toList().toBlocking().first();
        return this;
    }

    public final RxGenericsDataSource<DataType> last() {
        mDataSet = Observable.from(mDataSet).last().toList().toBlocking().first();
        return this;
    }

    public final RxGenericsDataSource<DataType> last(Func1<? super DataType, Boolean> predicate) {
        mDataSet = Observable.from(mDataSet).last(predicate).toList().toBlocking().first();
        return this;
    }

    public final RxGenericsDataSource<DataType> lastOrDefault(DataType defaultValue) {
        mDataSet = Observable.from(mDataSet)
                .takeLast(1)
                .singleOrDefault(defaultValue)
                .toList()
                .toBlocking()
                .first();
        return this;
    }

    public final RxGenericsDataSource<DataType> lastOrDefault(DataType defaultValue,
                                                              Func1<? super DataType, Boolean> predicate) {
        mDataSet = Observable.from(mDataSet)
                .filter(predicate)
                .takeLast(1)
                .singleOrDefault(defaultValue)
                .toList()
                .toBlocking()
                .first();
        return this;
    }

    public final RxGenericsDataSource<DataType> limit(int count) {
        mDataSet = Observable.from(mDataSet).limit(count).toList().toBlocking().first();
        return this;
    }

    public RxGenericsDataSource<DataType> empty() {
        mDataSet = Collections.emptyList();
        return this;
    }

    public final <R> RxGenericsDataSource<DataType> concatMap(
            Func1<? super DataType, ? extends Observable<? extends DataType>> func) {
        mDataSet = Observable.from(mDataSet).concatMap(func).toList().toBlocking().first();
        return this;
    }

    public final RxGenericsDataSource<DataType> concatWith(Observable<? extends DataType> t1) {
        mDataSet = Observable.from(mDataSet).concatWith(t1).toList().toBlocking().first();
        return this;
    }

    public RxGenericsDataSource<DataType> distinct() {
        mDataSet = Observable.from(mDataSet).distinct().toList().toBlocking().first();
        return this;
    }

    public RxGenericsDataSource<DataType> distinct(Func1<? super DataType, ? extends Object> keySelector) {
        mDataSet = Observable.from(mDataSet).distinct(keySelector).toList().toBlocking().first();
        return this;
    }

    public final RxGenericsDataSource<DataType> elementAt(int index) {
        mDataSet = Observable.from(mDataSet).elementAt(index).toList().toBlocking().first();
        return this;
    }

    public final RxGenericsDataSource<DataType> elementAtOrDefault(int index, DataType defaultValue) {
        mDataSet = Observable.from(mDataSet)
                .elementAtOrDefault(index, defaultValue)
                .toList()
                .toBlocking()
                .first();
        return this;
    }

    public final RxGenericsDataSource<DataType> first() {
        mDataSet = Observable.from(mDataSet).first().toList().toBlocking().first();
        return this;
    }

    public final RxGenericsDataSource<DataType> first(Func1<? super DataType, Boolean> predicate) {
        mDataSet = Observable.from(mDataSet).first(predicate).toList().toBlocking().first();
        return this;
    }

    public final RxGenericsDataSource<DataType> firstOrDefault(DataType defaultValue) {
        mDataSet = Observable.from(mDataSet).firstOrDefault(defaultValue).toList().toBlocking().first();
        return this;
    }

    public final RxGenericsDataSource<DataType> firstOrDefault(DataType defaultValue,
                                                               Func1<? super DataType, Boolean> predicate) {
        mDataSet = Observable.from(mDataSet)
                .firstOrDefault(defaultValue, predicate)
                .toList()
                .toBlocking()
                .first();
        return this;
    }

    public final RxGenericsDataSource<DataType> flatMap(
            Func1<? super DataType, ? extends Observable<? extends DataType>> func) {
        mDataSet = Observable.from(mDataSet).flatMap(func).toList().toBlocking().first();
        return this;
    }

    @Beta
    public final RxGenericsDataSource<DataType> flatMap(
            Func1<? super DataType, ? extends Observable<? extends DataType>> func, int maxConcurrent) {
        mDataSet = Observable.from(mDataSet).flatMap(func, maxConcurrent).toList().toBlocking().first();
        return this;
    }

    public final RxGenericsDataSource<DataType> flatMap(
            Func1<? super DataType, ? extends Observable<? extends DataType>> onNext,
            Func1<? super Throwable, ? extends Observable<? extends DataType>> onError,
            Func0<? extends Observable<? extends DataType>> onCompleted) {
        mDataSet = Observable.from(mDataSet)
                .flatMap(onNext, onError, onCompleted)
                .toList()
                .toBlocking()
                .first();
        return this;
    }

    @Beta
    public final RxGenericsDataSource<DataType> flatMap(
            Func1<? super DataType, ? extends Observable<? extends DataType>> onNext,
            Func1<? super Throwable, ? extends Observable<? extends DataType>> onError,
            Func0<? extends Observable<? extends DataType>> onCompleted, int maxConcurrent) {
        mDataSet = Observable.from(mDataSet)
                .flatMap(onNext, onError, onCompleted, maxConcurrent)
                .toList()
                .toBlocking()
                .first();
        return this;
    }

    @SuppressWarnings("unchecked")
    public final <U, R> RxGenericsDataSource<DataType> flatMap(
            final Func1<? super DataType, ? extends Observable<? extends U>> collectionSelector,
            final Func2<? super DataType, ? super U, ? extends DataType> resultSelector) {
        mDataSet = (List<DataType>) Observable.from(mDataSet)
                .flatMap(collectionSelector, resultSelector)
                .toList()
                .toBlocking()
                .first();
        return this;
    }

    public final RxGenericsDataSource<DataType> flatMapIterable(
            Func1<? super DataType, ? extends Iterable<? extends DataType>> collectionSelector) {
        mDataSet =
                Observable.from(mDataSet).flatMapIterable(collectionSelector).toList().toBlocking().first();
        return this;
    }

    public final RxGenericsDataSource<DataType> reduce(Func2<DataType, DataType, DataType> accumulator) {
        mDataSet = Observable.from(mDataSet).reduce(accumulator).toList().toBlocking().first();
        return this;
    }

    public final RxGenericsDataSource<DataType> reduce(DataType initialValue,
                                                       Func2<DataType, ? super DataType, DataType> accumulator) {
        mDataSet =
                Observable.from(mDataSet).reduce(initialValue, accumulator).toList().toBlocking().first();
        return this;
    }

    public final RxGenericsDataSource<DataType> repeat(final long count) {
        List<DataType> dataSet = mDataSet;
        mDataSet = Observable.from(dataSet).repeat(count).toList().toBlocking().first();
        return this;
    }

    public final RxGenericsDataSource<DataType> repeat(final long count, Scheduler scheduler) {
        mDataSet = Observable.from(mDataSet).repeat(count, scheduler).toList().toBlocking().first();
        return this;
    }

    public final RxGenericsDataSource<DataType> take(final int count) {
        mDataSet = Observable.from(mDataSet).take(count).toList().toBlocking().first();
        return this;
    }

    public final RxGenericsDataSource<DataType> takeFirst(Func1<? super DataType, Boolean> predicate) {
        mDataSet = Observable.from(mDataSet).takeFirst(predicate).toList().toBlocking().first();
        return this;
    }

    public final RxGenericsDataSource<DataType> takeLast(final int count) {
        mDataSet = Observable.from(mDataSet).takeLast(count).toList().toBlocking().first();
        return this;
    }
}
