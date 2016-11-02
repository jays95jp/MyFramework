package com.kevadiyak.rxpreference.rxpref;

import com.kevadiyak.rxpreference.SecuredPreferenceStore;

/**
 * The type Boolean preference.
 */
public class BooleanPreference extends BasePreference<Boolean> {

    /**
     * Instantiates a new Boolean preference.
     *
     * @param securePrefManager the secure pref manager
     */
    public BooleanPreference(SecuredPreferenceStore securePrefManager) {
        super(securePrefManager);
    }

    @Override
    protected Boolean getValue(String key, Boolean defValue) {
        return mSecurePrefManager.getBoolean(key, defValue);
    }

    @Override
    protected void putValue(String key, Boolean value) {
        mSecurePrefManager.edit().putBoolean(key, value).apply();
    }
}