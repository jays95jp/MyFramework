package com.kevadiyak.myframework.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import com.kevadiyak.customfont.CustomFontContextWrapper;
import com.kevadiyak.mvvmarchitecture.NavigatingMvvmActivity;
import com.kevadiyak.mvvmarchitecture.common.BindingConfig;
import com.kevadiyak.myframework.R;
import com.kevadiyak.myframework.databinding.ActivityMainBinding;
import com.kevadiyak.myframework.databinding.NavHeaderMainBinding;
import com.kevadiyak.myframework.navigators.MainNavigator;
import com.kevadiyak.myframework.viewmodels.MainViewModel;
import com.kevadiyak.rxpreference.SecuredPreferenceStore;
import com.kevadiyak.rxpreference.rxpref.RxSharedPreferences;

public class MainActivity extends NavigatingMvvmActivity<MainNavigator, ActivityMainBinding, MainViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Toolbar toolbar = getBinding().toolbar;
        final DrawerLayout drawer = getBinding().drawerLayout;
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = getBinding().navView;
        final NavHeaderMainBinding headerBinding = NavHeaderMainBinding.bind(navigationView.getHeaderView(0));
        headerBinding.setViewModel(getViewModel());
        headerBinding.executePendingBindings();
        navigationView.setNavigationItemSelectedListener(getNavigator());
    }

    @Override
    public void onBackPressed() {
        final DrawerLayout drawer = getBinding().drawerLayout;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @NonNull
    @Override
    public MainNavigator getNavigator() {
        return new MainNavigator(this, getBinding().drawerLayout);
    }

    @NonNull
    @Override
    public MainViewModel createViewModel() {
        return new MainViewModel(new RxSharedPreferences(SecuredPreferenceStore.getSharedInstance(this)));
    }

    @NonNull
    @Override
    public BindingConfig getBindingConfig() {
        return new BindingConfig(R.layout.activity_main);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getViewModel().homeFragment();
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CustomFontContextWrapper.wrap(newBase));
    }
}
