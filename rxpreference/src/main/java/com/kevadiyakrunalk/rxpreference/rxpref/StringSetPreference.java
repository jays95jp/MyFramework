package com.kevadiyakrunalk.rxpreference.rxpref;

import com.kevadiyakrunalk.rxpreference.EncryptedPreferences;

import java.util.Set;

/**
 * The type String set preference.
 */
public class StringSetPreference extends BasePreference<Set<String>> {

    /**
     * Instantiates a new String set preference.
     *
     * @param securePrefManager the secure pref manager
     */
    public StringSetPreference(EncryptedPreferences securePrefManager) {
        super(securePrefManager);
    }

    @Override
    protected Set<String> getValue(String key, Set<String> defValue) {
        return mSecurePrefManager.getStringSet(key, defValue);
    }

    @Override
    protected void putValue(String key, Set<String> value) {
        mSecurePrefManager.edit().putStringSet(key, value).apply();
    }
}