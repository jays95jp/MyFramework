package com.kevadiyakrunalk.rxpreference.rxpref;

import com.kevadiyakrunalk.rxpreference.EncryptedPreferences;

/**
 * The type Boolean preference.
 */
public class BooleanPreference extends BasePreference<Boolean> {

    /**
     * Instantiates a new Boolean preference.
     *
     * @param securePrefManager the secure pref manager
     */
    public BooleanPreference(EncryptedPreferences securePrefManager) {
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