package com.kevadiyakrunalk.myframework.other.location.viewmodels;

import android.content.Context;
import android.databinding.Bindable;
import android.os.Handler;
import android.text.TextUtils;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.kevadiyakrunalk.mvvmarchitecture.common.BaseViewModel;
import com.kevadiyakrunalk.myframework.BR;
import com.kevadiyakrunalk.rxlocation.ReactiveLocationProvider;

import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by admin on 10/20/2016.
 */
public class PlacesResultViewModel extends BaseViewModel {
    private Context context;
    private ReactiveLocationProvider reactiveLocationProvider;
    private CompositeSubscription compositeSubscription;

    private String placeId;
    private String placeName;
    private String placeLocation;
    private String placeAddress;

    public PlacesResultViewModel(Context context) {
        this.context = context;
        reactiveLocationProvider = new ReactiveLocationProvider(context);
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    @Bindable
    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        if (TextUtils.equals(this.placeName, placeName)) return;

        this.placeName = placeName;
        notifyPropertyChanged(BR.placeName);
    }

    @Bindable
    public String getPlaceLocation() {
        return placeLocation;
    }

    public void setPlaceLocation(String placeLocation) {
        if (TextUtils.equals(this.placeLocation, placeLocation)) return;

        this.placeLocation = placeLocation;
        notifyPropertyChanged(BR.placeLocation);
    }

    @Bindable
    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        if (TextUtils.equals(this.placeAddress, placeAddress)) return;

        this.placeAddress = placeAddress;
        notifyPropertyChanged(BR.placeAddress);
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                compositeSubscription = new CompositeSubscription();
                compositeSubscription.add(reactiveLocationProvider.getPlaceById(placeId)
                        .subscribe(new Action1<PlaceBuffer>() {
                            @Override
                            public void call(PlaceBuffer buffer) {
                                Place place = buffer.get(0);
                                if (place != null) {
                                    setPlaceName(place.getName() + "");
                                    setPlaceLocation(place.getLatLng().latitude + ", " + place.getLatLng().longitude);
                                    setPlaceAddress(place.getAddress() + "");
                                }
                                buffer.release();
                            }
                        }));
            }
        }, 1000);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(compositeSubscription != null)
            compositeSubscription.unsubscribe();
        compositeSubscription = null;
    }
}
