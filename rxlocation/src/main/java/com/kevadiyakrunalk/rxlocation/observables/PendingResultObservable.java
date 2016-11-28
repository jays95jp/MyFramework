package com.kevadiyakrunalk.rxlocation.observables;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;

import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.Subscriptions;

/**
 * The type Pending result observable.
 *
 * @param <T> the type parameter
 */
public class PendingResultObservable<T extends Result> implements Observable.OnSubscribe<T> {
    private final PendingResult<T> result;
    private boolean complete = false;

    /**
     * Instantiates a new Pending result observable.
     *
     * @param result the result
     */
    public PendingResultObservable(PendingResult<T> result) {
        this.result = result;
    }

    @Override
    public void call(final Subscriber<? super T> subscriber) {
        result.setResultCallback(t -> {
            subscriber.onNext(t);
            complete = true;
            subscriber.onCompleted();
        });
        subscriber.add(Subscriptions.create(() -> {
            if (!complete) {
                result.cancel();
            }
        }));
    }
}
