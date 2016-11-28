package com.kevadiyakrunalk.myframework.viewmodels;

import com.kevadiyakrunalk.mvvmarchitecture.common.NavigatingViewModel;
import com.kevadiyakrunalk.myframework.navigators.MainNavigator;
import com.kevadiyakrunalk.rxpreference.rxpref.RxSharedPreferences;

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
