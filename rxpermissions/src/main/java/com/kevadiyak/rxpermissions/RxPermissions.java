package com.kevadiyak.rxpermissions;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observable.Transformer;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

/**
 * The type Rx permissions.
 */
public class RxPermissions {
    /**
     * The constant TAG.
     */
    public static final String TAG = "RxPermissions";
    /**
     * The S singleton.
     */
    static RxPermissions sSingleton;

    /**
     * Gets instance.
     *
     * @param ctx the ctx
     * @return the instance
     */
    public static RxPermissions getInstance(Context ctx) {
        if (sSingleton == null) {
            synchronized (RxPermissions.class) {
                if (sSingleton == null) {
                    sSingleton = new RxPermissions(ctx);
                }
            }
        }
        return sSingleton;
    }

    private Context context;

    // Contains all the current permission requests.
    // Once granted or denied, they are removed from it.
    private Map<String, PublishSubject<Permission>> mSubjects = new HashMap<>();

    /**
     * Instantiates a new Rx permissions.
     *
     * @param ctx the ctx
     */
    RxPermissions(Context ctx) {
        context = ctx;
    }

    /**
     * Map emitted items from the source observable into {@code true} if permissions in parameters
     * are granted, or {@code false} if not.
     * <p>
     * If one or several permissions have never been requested, invoke the related framework method
     * to ask the user if he allows the permissions.
     *
     * @param permissions the permissions
     * @return the transformer
     */
    public Transformer<Object, Boolean> ensure(final String... permissions) {
        return o -> request(o, permissions)
                // Transform Observable<Permission> to Observable<Boolean>
                .buffer(permissions.length)
                .flatMap(new Func1<List<Permission>, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(List<Permission> permissions1) {
                        if (permissions1.isEmpty()) {
                            // Occurs during orientation change, when the subject receives onComplete.
                            // In that case we don't want to propagate that empty list to the
                            // subscriber, only the onComplete.
                            return Observable.empty();
                        }
                        // Return true if all permissions are granted.
                        for (Permission p : permissions1) {
                            if (!p.granted) {
                                return Observable.just(false);
                            }
                        }
                        return Observable.just(true);
                    }
                });
    }

    /**
     * Map emitted items from the source observable into {@link Permission} objects for each
     * permission in parameters.
     * <p>
     * If one or several permissions have never been requested, invoke the related framework method
     * to ask the user if he allows the permissions.
     *
     * @param permissions the permissions
     * @return the transformer
     */
    public Transformer<Object, Permission> ensureEach(final String... permissions) {
        return o -> request(o, permissions);
    }

    /**
     * Request permissions immediately, <b>must be invoked during initialization phase
     * of your application</b>.
     *
     * @param permissions the permissions
     * @return the observable
     */
    public Observable<Boolean> request(final String... permissions) {
        return Observable.just(null).compose(ensure(permissions));
    }

    /**
     * Request permissions immediately, <b>must be invoked during initialization phase
     * of your application</b>.
     *
     * @param permissions the permissions
     * @return the observable
     */
    public Observable<Permission> requestEach(final String... permissions) {
        return Observable.just(null).compose(ensureEach(permissions));
    }

    private Observable<Permission> request(final Observable<?> trigger, final String... permissions) {
        if (permissions == null || permissions.length == 0) {
            throw new IllegalArgumentException("RxPermissions.request/requestEach requires at least one input permission");
        }
        return oneOf(trigger, pending(permissions))
                .flatMap(new Func1<Object, Observable<Permission>>() {
                    @Override
                    public Observable<Permission> call(Object o) {
                        return request_(permissions);
                    }
                });
    }

    private Observable<?> pending(final String... permissions) {
        for (String p : permissions) {
            if (!mSubjects.containsKey(p)) {
                return Observable.empty();
            }
        }
        return Observable.just(null);
    }

    private Observable<?> oneOf(Observable<?> trigger, Observable<?> pending) {
        if (trigger == null) {
            return Observable.just(null);
        }
        return Observable.merge(trigger, pending);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private Observable<Permission> request_(final String... permissions) {

        List<Observable<Permission>> list = new ArrayList<>(permissions.length);
        List<String> unrequestedPermissions = new ArrayList<>();

        // In case of multiple permissions, we create a observable for each of them.
        // At the end, the observables are combined to have a unique response.
        for (String permission : permissions) {
            if (isGranted(permission)) {
                // Already granted, or not Android M
                // Return a granted Permission object.
                list.add(Observable.just(new Permission(permission, true)));
                continue;
            }

            if (isRevoked(permission)) {
                // Revoked by a policy, return a denied Permission object.
                list.add(Observable.just(new Permission(permission, false)));
                continue;
            }

            PublishSubject<Permission> subject = mSubjects.get(permission);
            // Create a new subject if not exists
            if (subject == null) {
                unrequestedPermissions.add(permission);
                subject = PublishSubject.create();
                mSubjects.put(permission, subject);
            }

            list.add(subject);
        }

        if (!unrequestedPermissions.isEmpty()) {
            startPermissionActivity(unrequestedPermissions
                    .toArray(new String[unrequestedPermissions.size()]));
        }
        return Observable.concat(Observable.from(list));
    }

    /**
     * Invokes Activity.shouldShowRequestPermissionRationale and wraps
     * the returned value in an observable.
     * <p>
     * In case of multiple permissions, only emits true if
     * Activity.shouldShowRequestPermissionRationale returned true for
     * all revoked permissions.
     * <p>
     * You shouldn't call this method if all permissions have been granted.
     * <p>
     * For SDK &lt; 23, the observable will always emit false.
     *
     * @param activity    the activity
     * @param permissions the permissions
     * @return the observable
     */
    public Observable<Boolean> shouldShowRequestPermissionRationale(final Activity activity, final String... permissions) {
        if (!isMarshmallow()) {
            return Observable.just(false);
        }
        return Observable.just(shouldShowRequestPermissionRationale_(activity, permissions));
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean shouldShowRequestPermissionRationale_(final Activity activity, final String... permissions) {
        for (String p : permissions) {
            if (!isGranted(p) && !activity.shouldShowRequestPermissionRationale(p)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Start permission activity.
     *
     * @param permissions the permissions
     */
    void startPermissionActivity(String[] permissions) {
        Intent intent = new Intent(context, PermissionActivity.class);
        intent.putExtra("permissions", permissions);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * Returns true if the permission is already granted.
     * <p>
     * Always true if SDK &lt; 23.
     *
     * @param permission the permission
     * @return the boolean
     */
    public boolean isGranted(String permission) {
        return !isMarshmallow() || isGranted_(permission);
    }

    /**
     * Returns true if the permission has been revoked by a policy.
     * <p>
     * Always false if SDK &lt; 23.
     *
     * @param permission the permission
     * @return the boolean
     */
    public boolean isRevoked(String permission) {
        return isMarshmallow() && isRevoked_(permission);
    }

    /**
     * Is marshmallow boolean.
     *
     * @return the boolean
     */
    public boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean isGranted_(String permission) {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean isRevoked_(String permission) {
        return context.getPackageManager().isPermissionRevokedByPolicy(permission, context.getPackageName());
    }

    /**
     * On request permissions result.
     *
     * @param requestCode  the request code
     * @param permissions  the permissions
     * @param grantResults the grant results
     */
    void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        for (int i = 0, size = permissions.length; i < size; i++) {
            // Find the corresponding subject
            PublishSubject<Permission> subject = mSubjects.get(permissions[i]);
            if (subject == null) {
                // No subject found
                throw new IllegalStateException("RxPermissions.onRequestPermissionsResult invoked but didn't find the corresponding permission request.");
            }
            mSubjects.remove(permissions[i]);
            boolean granted = grantResults[i] == PackageManager.PERMISSION_GRANTED;
            subject.onNext(new Permission(permissions[i], granted));
            subject.onCompleted();
        }
    }

    //user use to activity

    /**
     * Check marshmallow ensure permission observable . transformer.
     *
     * @param result          the result
     * @param paramPermission the param permission
     * @return the observable . transformer
     */
    public void checkMEnsureEachPermission(PermissionResult result, String... paramPermission) {
        Observable.just(null)
                .compose(ensureEach(paramPermission))
                .subscribe(permission -> {
                    result.onPermissionResult(permission.name, permission.granted);
                });
    }

    /**
     * Check m ensure permission.
     *
     * @param result          the result
     * @param paramPermission the param permission
     */
    public void checkMEnsurePermission(PermissionResult result, String... paramPermission) {
        Observable.just(null)
                .compose(ensure(paramPermission))
                .subscribe(granted -> {
                    result.onPermissionResult(Arrays.toString(paramPermission), granted);
                });
    }

    /**
     * Check marshmallow multiple permission.
     *
     * @param result          the result
     * @param paramPermission the param permission
     */
    public void checkMPermission(PermissionResult result, String... paramPermission) {
        request(paramPermission)
                .subscribe(granted -> {
                    result.onPermissionResult(Arrays.toString(paramPermission), granted);
                });
    }

    /**
     * Check marshmallow each permission.
     *
     * @param result          the result
     * @param paramPermission the param permission
     */
    public void checkMEachPermission(PermissionResult result, String... paramPermission) {
        requestEach(paramPermission)
                .subscribe(permission -> {
                    result.onPermissionResult(permission.name, permission.granted);
                });
    }
}