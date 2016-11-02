package com.kevadiyak.myframework.viewmodels;

import android.databinding.Bindable;
import android.text.TextUtils;
import android.view.View;

import com.kevadiyak.mvvmarchitecture.common.NavigatingViewModel;
import com.kevadiyak.myframework.BR;
import com.kevadiyak.myframework.navigators.StartNavigator;
import com.kevadiyak.rxpreference.rxpref.RxSharedPreferences;

public class StartViewModel extends NavigatingViewModel<StartNavigator> {

    private String name;
    private final RxSharedPreferences preferences;

    /**
     * Create StartViewModel with preferences
     *
     * @param preferences the AppPreferences
     */
    public StartViewModel(RxSharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (TextUtils.equals(this.name, name)) return;

        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    /**
     * Clear current name.
     *
     * @param view an interacted view
     */
    public void onNameClear(View view) {
        setName(null);
    }

    /**
     * Start app.
     *
     * @param view an interacted view
     */
    public void onStartAppRequested(View view) {
        preferences.putString("NAME_KEY", name);
        if (navigator != null) {
            navigator.navigateToMain();
        }
    }
}
