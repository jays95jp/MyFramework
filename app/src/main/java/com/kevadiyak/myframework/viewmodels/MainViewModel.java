package com.kevadiyak.myframework.viewmodels;

import com.kevadiyak.mvvmarchitecture.common.NavigatingViewModel;
import com.kevadiyak.myframework.navigators.MainNavigator;
import com.kevadiyak.rxpreference.rxpref.RxSharedPreferences;

public class MainViewModel extends NavigatingViewModel<MainNavigator> {

    private String name;
    private final RxSharedPreferences preferences;

    /**
     * Create MainViewModel with preferences
     *
     * @param preferences the AppPreferences
     */
    public MainViewModel(RxSharedPreferences preferences) {
        this.preferences = preferences;
    }

    public String getName() {
        return name;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        name = preferences.getString("NAME_KEY", "Friends");
    }

    public void homeFragment() {
        if(navigator != null)
            navigator.setHomeFragment();
    }
}
