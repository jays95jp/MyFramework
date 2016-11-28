package com.kevadiyakrunalk.myframework.other.location.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.kevadiyakrunalk.mvvmarchitecture.MvvmActivity;
import com.kevadiyakrunalk.mvvmarchitecture.common.BindingConfig;
import com.kevadiyakrunalk.myframework.R;
import com.kevadiyakrunalk.myframework.databinding.ActivityPlacesResultBinding;
import com.kevadiyakrunalk.myframework.other.location.viewmodels.PlacesResultViewModel;

public class PlacesResultActivity extends MvvmActivity<ActivityPlacesResultBinding, PlacesResultViewModel> {
    private String EXTRA_PLACE_ID = "EXTRA_PLACE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Toolbar toolbar = getBinding().toolbar;
        setSupportActionBar(toolbar);
        getPlaceIdFromIntent();
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @NonNull
    @Override
    public BindingConfig getBindingConfig() {
        return new BindingConfig(R.layout.activity_places_result);
    }

    @NonNull
    @Override
    public PlacesResultViewModel createViewModel() {
        return new PlacesResultViewModel(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void getPlaceIdFromIntent() {
        Intent loadedIntent = getIntent();
        if (loadedIntent.getStringExtra(EXTRA_PLACE_ID) == null)
            throw new IllegalStateException("You must start SearchResultsActivity with a non-null place Id using getStartIntent(Context, String)");

        getViewModel().setPlaceId(loadedIntent.getStringExtra(EXTRA_PLACE_ID));
    }
}
