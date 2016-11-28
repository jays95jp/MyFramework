package com.kevadiyakrunalk.myframework.other.location.viewmodels;

import android.content.Context;
import android.databinding.Bindable;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.kevadiyakrunalk.mvvmarchitecture.common.BaseViewModel;
import com.kevadiyakrunalk.myframework.BR;
import com.kevadiyakrunalk.rxlocation.ReactiveLocationProvider;

import java.util.Date;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.subjects.PublishSubject;

public class MockLocationViewModel extends BaseViewModel {
    private Context context;

    private ReactiveLocationProvider locationProvider;
    private Observable<Location> mockLocationObservable;
    private Subscription mockLocationSubscription;
    private Subscription updatedLocationSubscription;
    private PublishSubject<Location> mockLocationSubject;

    private String strLatitude;
    private String strLongitude;
    private String strMokeLocation;
    private String strUpdateLocation;
    private boolean isToggle;

    public MockLocationViewModel(Context context) {
        this.context = context;
        isToggle = true;
        locationProvider = new ReactiveLocationProvider(context);
        mockLocationSubject = PublishSubject.create();
        mockLocationObservable = mockLocationSubject.asObservable();
    }

    @Bindable
    public String getStrLatitude() {
        return strLatitude;
    }

    public void setStrLatitude(String strLatitude) {
        if (TextUtils.equals(this.strLatitude, strLatitude)) return;

        this.strLatitude = strLatitude;
        notifyPropertyChanged(BR.strLatitude);
    }

    @Bindable
    public String getStrLongitude() {
        return strLongitude;
    }

    public void setStrLongitude(String strLongitude) {
        if (TextUtils.equals(this.strLongitude, strLongitude)) return;

        this.strLongitude = strLongitude;
        notifyPropertyChanged(BR.strLongitude);
    }

    @Bindable
    public String getStrMokeLocation() {
        return strMokeLocation;
    }

    public void setStrMokeLocation(String strMokeLocation) {
        if (TextUtils.equals(this.strMokeLocation, strMokeLocation)) return;

        this.strMokeLocation = strMokeLocation;
        notifyPropertyChanged(BR.strMokeLocation);
    }

    @Bindable
    public String getStrUpdateLocation() {
        return strUpdateLocation;
    }

    public void setStrUpdateLocation(String strUpdateLocation) {
        if (TextUtils.equals(this.strUpdateLocation, strUpdateLocation)) return;

        this.strUpdateLocation = strUpdateLocation;
        notifyPropertyChanged(BR.strUpdateLocation);
    }

    @Bindable
    public boolean isToggle() {
        return isToggle;
    }

    public void setToggle(boolean toggle) {
        isToggle = toggle;
        notifyPropertyChanged(BR.toggle);
    }

    public void onLocation(View view) {
        addMockLocation();
    }

    public void onToggle(View view) {
        setMockMode(((ToggleButton) view).isChecked());
        setToggle(((ToggleButton) view).isChecked());
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final LocationRequest locationRequest = LocationRequest.create()
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setInterval(2000);

                updatedLocationSubscription = locationProvider
                        .getUpdatedLocation(locationRequest)
                        .map(new Func1<Location, String>() {
                            @Override
                            public String call(Location location) {
                                if (location != null)
                                    return location.getLatitude() + " " + location.getLongitude() + " (" + location.getAccuracy() + ")";
                                return "no location available";
                            }
                        })
                        .map(new Func1<String, String>() {
                            int count = 0;
                            @Override
                            public String call(String s) {
                                return s + " " + count++;
                            }
                        })
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                setStrUpdateLocation(s);
                            }
                        });
            }
        }, 1000);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mockLocationSubscription != null)
            mockLocationSubscription.unsubscribe();
        if(updatedLocationSubscription != null)
            updatedLocationSubscription.unsubscribe();
    }

    private void addMockLocation() {
        try {
            mockLocationSubject.onNext(createMockLocation());
        } catch (Throwable e) {
            Toast.makeText(context, "Error parsing input.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setMockMode(boolean toggle) {
        if (toggle) {
            mockLocationSubscription =
                    Observable.zip(locationProvider.mockLocation(mockLocationObservable),
                            mockLocationObservable, new Func2<Status, Location, String>() {
                                int count = 0;
                                @Override
                                public String call(Status result, Location location) {
                                    return new Func1<Location, String>() {
                                        @Override
                                        public String call(Location location) {
                                            if (location != null)
                                                return location.getLatitude() + " " + location.getLongitude() + " (" + location.getAccuracy() + ")";
                                            return "no location available";
                                        }
                                    }.call(location) + " " + count++;
                                }
                            })
                            .subscribe(new Action1<String>() {
                                @Override
                                public void call(String s) {
                                    setStrMokeLocation(s);
                                }
                            }, new ErrorHandler());
        } else {
            mockLocationSubscription.unsubscribe();
        }
    }

    private Location createMockLocation() {
        if (!strLatitude.isEmpty() && !strLongitude.isEmpty()) {
            double longitude = Location.convert(strLongitude);
            double latitude = Location.convert(strLatitude);

            Location mockLocation = new Location("flp");
            mockLocation.setLatitude(latitude);
            mockLocation.setLongitude(longitude);
            mockLocation.setAccuracy(1.0f);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mockLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
            }
            mockLocation.setTime(new Date().getTime());
            return mockLocation;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private class ErrorHandler implements Action1<Throwable> {
        @Override
        public void call(Throwable throwable) {
            if (throwable instanceof SecurityException) {
                Toast.makeText(context, "You need to enable mock locations in Developer Options.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Error occurred.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
