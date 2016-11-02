package com.kevadiyak.rxpreference.rxpref;

import com.kevadiyak.rxpreference.SecuredPreferenceStore;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The type Json object preference.
 */
public class JSONObjectPreference extends BasePreference<JSONObject> {

    /**
     * Instantiates a new Json object preference.
     *
     * @param securePrefManager the secure pref manager
     */
    public JSONObjectPreference(SecuredPreferenceStore securePrefManager) {
        super(securePrefManager);
    }

    @Override
    protected JSONObject getValue(String key, JSONObject defValue){
        try {
            return new JSONObject(mSecurePrefManager.getString(key, defValue.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void putValue(String key, JSONObject value) {
        String jsonString = value.toString();
        mSecurePrefManager.edit().putString(key, jsonString).apply();
    }
}