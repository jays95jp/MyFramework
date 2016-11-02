package com.kevadiyak.rxpreference.rxpref;

import android.text.TextUtils;

import com.kevadiyak.rxpreference.SecuredPreferenceStore;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.subjects.PublishSubject;
import rx.subscriptions.Subscriptions;

/**
 * The type Base preference.
 *
 * @param <T> the type parameter
 */
public abstract class BasePreference<T> {

    /**
     * Gets value.
     *
     * @param key      the key
     * @param defValue the def value
     * @return the value
     */
    protected abstract T getValue(String key, T defValue);

    /**
     * Put value.
     *
     * @param key   the key
     * @param value the value
     */
    protected abstract void putValue(String key, T value);

    /**
     * The M secure pref manager.
     */
    protected final SecuredPreferenceStore mSecurePrefManager;

    /**
     * The M indexes.
     */
    protected List<String> mIndexes;

    /**
     * The M subject.
     */
    protected PublishSubject<String> mSubject;

    /**
     * Instantiates a new Base preference.
     *
     * @param SecurePrefManager the secure pref manager
     */
    public BasePreference(SecuredPreferenceStore SecurePrefManager) {
        if (SecurePrefManager == null) {
            throw new RuntimeException("SharedPreferences can not be null");
        }
        mIndexes = new ArrayList<>();
        mSubject = PublishSubject.create();
        mSecurePrefManager = SecurePrefManager;
        mSecurePrefManager.registerOnSharedPreferenceChangeListener((preferences, key) -> mSubject.onNext(key));
    }

    /**
     * Get t.
     *
     * @param key      the key
     * @param defValue the def value
     * @return the t
     */
    public T get(String key, T defValue) {
        addToIndex(key);
        return getValue(key, defValue);
    }

    /**
     * Observe observable.
     *
     * @param key      the key
     * @param defValue the def value
     * @return the observable
     */
    public Observable<T> observe(String key, T defValue) {
        addToIndex(key);
        return Observable.create(subscriber -> {
            subscriber.onNext(get(key, defValue));
            Subscription subjectSubscription = mSubject
                    .filter(k -> TextUtils.equals(k, key))
                    .map(k -> get(key, defValue))
                    .subscribe(subscriber::onNext);
            subscriber.add(Subscriptions.create(subjectSubscription::unsubscribe));
        });
    }

    /**
     * Put.
     *
     * @param key   the key
     * @param value the value
     */
    public void put(String key, T value) {
        addToIndex(key);
        putValue(key, value);
    }

    /**
     * Reset.
     */
    public void reset() {
        resetButKeep(null);
    }

    /**
     * Reset but keep.
     *
     * @param keys the keys
     */
    public void resetButKeep(List<String> keys) {
        keys = keys != null ? keys : new ArrayList<>();
        if (mIndexes.size() > 0) {
            List<String> keepIndexes = new ArrayList<>();
            int i = 0;
            for (int n = mIndexes.size(); i < n; i++) {
                if (keys.indexOf(mIndexes.get(i)) < 0) {
                    mSecurePrefManager.edit().remove(mIndexes.get(i)).apply();
                } else {
                    keepIndexes.add(mIndexes.get(i));
                }
            }
            mIndexes.clear();
            mIndexes.addAll(keepIndexes);
        }
    }

    /**
     * Add to index.
     *
     * @param key the key
     */
    protected void addToIndex(String key) {
        if (mIndexes.indexOf(key) < 0) {
            mIndexes.add(key);
        }
    }
}