package com.kevadiyakrunalk.rxlocation.observables;

import android.content.Context;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

import rx.Observable;
import rx.Observer;

/**
 * The type Google api client observable.
 */
public class GoogleAPIClientObservable extends BaseObservable<GoogleApiClient> {

    /**
     * Create observable.
     *
     * @param context the context
     * @param apis    the apis
     * @return the observable
     */
    @SafeVarargs
    public static Observable<GoogleApiClient> create(Context context, Api<? extends Api.ApiOptions.NotRequiredOptions>... apis) {
        return Observable.create(new GoogleAPIClientObservable(context, apis));
    }

    /**
     * Instantiates a new Google api client observable.
     *
     * @param ctx  the ctx
     * @param apis the apis
     */
    @SafeVarargs
    protected GoogleAPIClientObservable(Context ctx, Api<? extends Api.ApiOptions.NotRequiredOptions>... apis) {
        super(ctx, apis);
    }

    @Override
    protected void onGoogleApiClientReady(GoogleApiClient apiClient, Observer<? super GoogleApiClient> observer) {
        observer.onNext(apiClient);
        observer.onCompleted();
    }
}
