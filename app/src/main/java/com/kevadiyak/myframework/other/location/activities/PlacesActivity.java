package com.kevadiyak.myframework.other.location.activities;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.kevadiyak.mvvmarchitecture.NavigatingMvvmActivity;
import com.kevadiyak.mvvmarchitecture.common.BindingConfig;
import com.kevadiyak.myframework.R;
import com.kevadiyak.myframework.databinding.ActivityPlacesBinding;
import com.kevadiyak.myframework.other.location.navigators.PlacesNavigator;
import com.kevadiyak.myframework.other.location.viewmodels.PlacesViewModel;
import com.kevadiyak.rxlocation.ReactiveLocationProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.MainThreadSubscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.subscriptions.CompositeSubscription;

import static rx.android.MainThreadSubscription.verifyMainThread;

public class PlacesActivity extends NavigatingMvvmActivity<PlacesNavigator, ActivityPlacesBinding, PlacesViewModel> {
    private ListView placeSuggestionsList;
    private ReactiveLocationProvider reactiveLocationProvider;
    private CompositeSubscription compositeSubscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Toolbar toolbar = getBinding().toolbar;
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @NonNull
    @Override
    public BindingConfig getBindingConfig() {
        return new BindingConfig(R.layout.activity_places);
    }

    @NonNull
    @Override
    public PlacesViewModel createViewModel() {
        return new PlacesViewModel(this);
    }

    @NonNull
    @Override
    public PlacesNavigator getNavigator() {
        return new PlacesNavigator(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        placeSuggestionsList = (ListView) findViewById(R.id.place_suggestions_list);
        placeSuggestionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AutocompleteInfo info = (AutocompleteInfo) parent.getAdapter().getItem(position);
                getViewModel().onStartPlacesResultActivity(info.id);
            }
        });
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

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                reactiveLocationProvider = new ReactiveLocationProvider(PlacesActivity.this);
                compositeSubscription = new CompositeSubscription();
                compositeSubscription.add(
                        reactiveLocationProvider.getCurrentPlace(null)
                                .subscribe(new Action1<PlaceLikelihoodBuffer>() {
                                    @Override
                                    public void call(PlaceLikelihoodBuffer buffer) {
                                        PlaceLikelihood likelihood = buffer.get(0);
                                        if (likelihood != null) {
                                            getViewModel().setQuery(""+likelihood.getPlace().getName());
                                        }
                                        buffer.release();
                                    }
                                })
                );

                Observable<String> queryObservable =
                        textChange()
                                .debounce(1, TimeUnit.SECONDS)
                                .filter(new Func1<String, Boolean>() {
                                    @Override
                                    public Boolean call(String s) {
                                        return !TextUtils.isEmpty(s);
                                    }
                                });

                Observable<Location> lastKnownLocationObservable = reactiveLocationProvider.getLastKnownLocation();
                Observable<AutocompletePredictionBuffer> suggestionsObservable = Observable
                        .combineLatest(queryObservable, lastKnownLocationObservable, new Func2<String, Location, QueryWithCurrentLocation>() {
                            @Override
                            public QueryWithCurrentLocation call(String query, Location currentLocation) {
                                return new QueryWithCurrentLocation(query, currentLocation);
                            }
                        }).flatMap(new Func1<QueryWithCurrentLocation, Observable<AutocompletePredictionBuffer>>() {
                            @Override
                            public Observable<AutocompletePredictionBuffer> call(QueryWithCurrentLocation q) {
                                if (q.location == null) return Observable.empty();

                                double latitude = q.location.getLatitude();
                                double longitude = q.location.getLongitude();
                                LatLngBounds bounds = new LatLngBounds(
                                        new LatLng(latitude - 0.05, longitude - 0.05),
                                        new LatLng(latitude + 0.05, longitude + 0.05)
                                );
                                return reactiveLocationProvider.getPlaceAutocompletePredictions(q.query, bounds, null);
                            }
                        });

                compositeSubscription.add(suggestionsObservable.subscribe(new Action1<AutocompletePredictionBuffer>() {
                    @Override
                    public void call(AutocompletePredictionBuffer buffer) {
                        List<AutocompleteInfo> infos = new ArrayList<>();
                        for (AutocompletePrediction prediction : buffer) {
                            infos.add(new AutocompleteInfo(prediction.getFullText(null).toString(), prediction.getPlaceId()));
                        }
                        buffer.release();
                        if(placeSuggestionsList != null)
                            placeSuggestionsList.setAdapter(new ArrayAdapter<>(PlacesActivity.this, android.R.layout.simple_list_item_1, infos));
                    }
                }));
            }
        }, 1000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeSubscription.unsubscribe();
        compositeSubscription = null;
    }

    private static class QueryWithCurrentLocation {
        public final String query;
        public final Location location;

        private QueryWithCurrentLocation(String query, Location location) {
            this.query = query;
            this.location = location;
        }
    }

    private static class AutocompleteInfo {
        private final String description;
        private final String id;

        private AutocompleteInfo(String description, String id) {
            this.description = description;
            this.id = id;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    private Observable<String> textChange() {
        return Observable.create(
            new Observable.OnSubscribe<String>() {
                @Override
                public void call(final Subscriber<? super String> subscriber) {
                    verifyMainThread();

                    final TextWatcher watcher = new TextWatcher() {
                        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onNext(s.toString());
                            }
                        }

                        @Override public void afterTextChanged(Editable s) {
                        }
                    };
                    getBinding().placeQueryView.addTextChangedListener(watcher);

                    subscriber.add(new MainThreadSubscription() {
                        @Override protected void onUnsubscribe() {
                            getBinding().placeQueryView.removeTextChangedListener(watcher);
                        }
                    });

                    // Emit initial value.
                    subscriber.onNext(getBinding().placeQueryView.getText().toString());
                }
            });
    }
}
