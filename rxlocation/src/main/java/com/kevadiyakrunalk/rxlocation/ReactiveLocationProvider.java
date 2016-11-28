package com.kevadiyakrunalk.rxlocation;

import android.app.PendingIntent;
import android.content.Context;
import android.location.Address;
import android.location.Location;
import android.support.annotation.Nullable;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;
import java.util.Locale;

import com.kevadiyakrunalk.rxlocation.observables.GoogleAPIClientObservable;
import com.kevadiyakrunalk.rxlocation.observables.PendingResultObservable;
import com.kevadiyakrunalk.rxlocation.observables.activity.ActivityUpdatesObservable;
import com.kevadiyakrunalk.rxlocation.observables.geocode.GeocodeObservable;
import com.kevadiyakrunalk.rxlocation.observables.geocode.ReverseGeocodeObservable;
import com.kevadiyakrunalk.rxlocation.observables.geofence.AddGeofenceObservable;
import com.kevadiyakrunalk.rxlocation.observables.geofence.RemoveGeofenceObservable;
import com.kevadiyakrunalk.rxlocation.observables.location.AddLocationIntentUpdatesObservable;
import com.kevadiyakrunalk.rxlocation.observables.location.LastKnownLocationObservable;
import com.kevadiyakrunalk.rxlocation.observables.location.LocationUpdatesObservable;
import com.kevadiyakrunalk.rxlocation.observables.location.MockLocationObservable;
import com.kevadiyakrunalk.rxlocation.observables.location.RemoveLocationIntentUpdatesObservable;

import rx.Observable;
import rx.functions.Func1;

/**
 * Factory of observables that can manipulate location
 * delivered by Google Play Services.
 */
public class ReactiveLocationProvider {
    private static ReactiveLocationProvider sSingleton;
    private final Context context;

    /**
     * Instantiates a new Reactive location provider.
     *
     * @param ctx the ctx
     */
    public ReactiveLocationProvider(Context ctx) {
        this.context = ctx;
    }

    /**
     * Gets instance.
     *
     * @param ctx the ctx
     * @return the instance
     */
    public static ReactiveLocationProvider getInstance(Context ctx) {
        if (sSingleton == null) {
            synchronized (ReactiveLocationProvider.class) {
                if (sSingleton == null) {
                    sSingleton = new ReactiveLocationProvider(ctx);
                }
            }
        }
        return sSingleton;
    }

    /**
     * Gets last known location.
     *
     * @return the last known location
     */
    public Observable<Location> getLastKnownLocation() {
        return LastKnownLocationObservable.createObservable(context);
    }

    /**
     * Gets updated location.
     *
     * @param locationRequest the location request
     * @return the updated location
     */
    public Observable<Location> getUpdatedLocation(LocationRequest locationRequest) {
        return LocationUpdatesObservable.createObservable(context, locationRequest);
    }

    /**
     * Mock location observable.
     *
     * @param sourceLocationObservable the source location observable
     * @return the observable
     */
    public Observable<Status> mockLocation(Observable<Location> sourceLocationObservable) {
        return MockLocationObservable.createObservable(context, sourceLocationObservable);
    }

    /**
     * Request location updates observable.
     *
     * @param locationRequest the location request
     * @param intent          the intent
     * @return the observable
     */
    public Observable<Status> requestLocationUpdates(LocationRequest locationRequest, PendingIntent intent) {
        return AddLocationIntentUpdatesObservable.createObservable(context, locationRequest, intent);
    }

    /**
     * Remove location updates observable.
     *
     * @param intent the intent
     * @return the observable
     */
    public Observable<Status> removeLocationUpdates(PendingIntent intent) {
        return RemoveLocationIntentUpdatesObservable.createObservable(context, intent);
    }

    /**
     * Gets reverse geocode observable.
     *
     * @param lat        the lat
     * @param lng        the lng
     * @param maxResults the max results
     * @return the reverse geocode observable
     */
    public Observable<List<Address>> getReverseGeocodeObservable(double lat, double lng, int maxResults) {
        return ReverseGeocodeObservable.createObservable(context, Locale.getDefault(), lat, lng, maxResults);
    }

    /**
     * Gets reverse geocode observable.
     *
     * @param locale     the locale
     * @param lat        the lat
     * @param lng        the lng
     * @param maxResults the max results
     * @return the reverse geocode observable
     */
    public Observable<List<Address>> getReverseGeocodeObservable(Locale locale, double lat, double lng, int maxResults) {
        return ReverseGeocodeObservable.createObservable(context, locale, lat, lng, maxResults);
    }

    /**
     * Gets geocode observable.
     *
     * @param locationName the location name
     * @param maxResults   the max results
     * @return the geocode observable
     */
    public Observable<List<Address>> getGeocodeObservable(String locationName, int maxResults) {
        return getGeocodeObservable(locationName, maxResults, null);
    }

    /**
     * Gets geocode observable.
     *
     * @param locationName the location name
     * @param maxResults   the max results
     * @param bounds       the bounds
     * @return the geocode observable
     */
    public Observable<List<Address>> getGeocodeObservable(String locationName, int maxResults, LatLngBounds bounds) {
        return GeocodeObservable.createObservable(context, locationName, maxResults, bounds);
    }

    /**
     * Add geofences observable.
     *
     * @param geofenceTransitionPendingIntent the geofence transition pending intent
     * @param request                         the request
     * @return the observable
     */
    public Observable<Status> addGeofences(PendingIntent geofenceTransitionPendingIntent, GeofencingRequest request) {
        return AddGeofenceObservable.createObservable(context, request, geofenceTransitionPendingIntent);
    }

    /**
     * Remove geofences observable.
     *
     * @param pendingIntent the pending intent
     * @return the observable
     */
    public Observable<Status> removeGeofences(PendingIntent pendingIntent) {
        return RemoveGeofenceObservable.createObservable(context, pendingIntent);
    }

    /**
     * Remove geofences observable.
     *
     * @param requestIds the request ids
     * @return the observable
     */
    public Observable<Status> removeGeofences(List<String> requestIds) {
        return RemoveGeofenceObservable.createObservable(context, requestIds);
    }

    /**
     * Gets detected activity.
     *
     * @param detectIntervalMiliseconds the detect interval miliseconds
     * @return the detected activity
     */
    public Observable<ActivityRecognitionResult> getDetectedActivity(int detectIntervalMiliseconds) {
        return ActivityUpdatesObservable.createObservable(context, detectIntervalMiliseconds);
    }

    /**
     * Check location settings observable.
     *
     * @param locationRequest the location request
     * @return the observable
     */
    public Observable<LocationSettingsResult> checkLocationSettings(final LocationSettingsRequest locationRequest) {
        return getGoogleApiClientObservable(LocationServices.API)
                .flatMap(new Func1<GoogleApiClient, Observable<LocationSettingsResult>>() {
                    @Override
                    public Observable<LocationSettingsResult> call(GoogleApiClient googleApiClient) {
                        return fromPendingResult(LocationServices.SettingsApi.checkLocationSettings(googleApiClient, locationRequest));
                    }
                });
    }

    /**
     * Gets current place.
     *
     * @param placeFilter the place filter
     * @return the current place
     */
    public Observable<PlaceLikelihoodBuffer> getCurrentPlace(@Nullable final PlaceFilter placeFilter) {
        return getGoogleApiClientObservable(Places.PLACE_DETECTION_API, Places.GEO_DATA_API)
                .flatMap(new Func1<GoogleApiClient, Observable<PlaceLikelihoodBuffer>>() {
                    @Override
                    public Observable<PlaceLikelihoodBuffer> call(GoogleApiClient api) {
                        return fromPendingResult(Places.PlaceDetectionApi.getCurrentPlace(api, placeFilter));
                    }
                });
    }

    /**
     * Gets place by id.
     *
     * @param placeId the place id
     * @return the place by id
     */
    public Observable<PlaceBuffer> getPlaceById(@Nullable final String placeId) {
        return getGoogleApiClientObservable(Places.PLACE_DETECTION_API, Places.GEO_DATA_API)
                .flatMap(new Func1<GoogleApiClient, Observable<PlaceBuffer>>() {
                    @Override
                    public Observable<PlaceBuffer> call(GoogleApiClient api) {
                        return fromPendingResult(Places.GeoDataApi.getPlaceById(api, placeId));
                    }
                });
    }

    /**
     * Gets place autocomplete predictions.
     *
     * @param query  the query
     * @param bounds the bounds
     * @param filter the filter
     * @return the place autocomplete predictions
     */
    public Observable<AutocompletePredictionBuffer> getPlaceAutocompletePredictions(final String query, final LatLngBounds bounds, final AutocompleteFilter filter) {
        return getGoogleApiClientObservable(Places.PLACE_DETECTION_API, Places.GEO_DATA_API)
                .flatMap(new Func1<GoogleApiClient, Observable<AutocompletePredictionBuffer>>() {
                    @Override
                    public Observable<AutocompletePredictionBuffer> call(GoogleApiClient api) {
                        return fromPendingResult(Places.GeoDataApi.getAutocompletePredictions(api, query, bounds, filter));
                    }
                });
    }

    /**
     * Gets google api client observable.
     *
     * @param apis the apis
     * @return the google api client observable
     */
    public Observable<GoogleApiClient> getGoogleApiClientObservable(Api... apis) {
        //noinspection unchecked
        return GoogleAPIClientObservable.create(context, apis);
    }

    /**
     * From pending result observable.
     *
     * @param <T>    the type parameter
     * @param result the result
     * @return the observable
     */
    public static <T extends Result> Observable<T> fromPendingResult(PendingResult<T> result) {
        return Observable.create(new PendingResultObservable<>(result));
    }
}
