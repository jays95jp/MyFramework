package com.kevadiyak.myframework.other.location.viewmodels;

import android.content.Context;
import android.databinding.Bindable;
import android.text.TextUtils;

import com.kevadiyak.mvvmarchitecture.common.NavigatingViewModel;
import com.kevadiyak.myframework.BR;
import com.kevadiyak.myframework.other.location.navigators.PlacesNavigator;

/**
 * Created by admin on 10/20/2016.
 */

public class PlacesViewModel extends NavigatingViewModel<PlacesNavigator> {
    private Context context;

    private String query;
    private String currentPlace;

    public PlacesViewModel(Context context) {
        this.context = context;
    }

    @Bindable
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        if(TextUtils.equals(this.query, query)) return;

        this.query = query;
        notifyPropertyChanged(BR.query);
    }

    @Bindable
    public String getCurrentPlace() {
        return currentPlace;
    }

    public void setCurrentPlace(String currentPlace) {
        if(TextUtils.equals(this.currentPlace, currentPlace)) return;

        this.currentPlace = currentPlace;
        notifyPropertyChanged(BR.currentPlace);
    }

    public void onStartPlacesResultActivity(String placeId) {
        if (navigator != null) {
            navigator.navigateToPlacesResult(placeId);
        }
    }

}
