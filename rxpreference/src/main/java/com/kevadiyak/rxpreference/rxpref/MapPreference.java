package com.kevadiyak.rxpreference.rxpref;

import com.kevadiyak.rxpreference.SecuredPreferenceStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * The type Map preference.
 */
public class MapPreference extends BasePreference<Map<String, ?>> {

    /**
     * Instantiates a new Map preference.
     *
     * @param securePrefManager the secure pref manager
     */
    public MapPreference(SecuredPreferenceStore securePrefManager) {
        super(securePrefManager);
    }

    @Override
    protected Map<String, ?> getValue(String key, Map<String, ?> defValue) {
        Map<String, Object> res = new HashMap<>();
        JSONObject jsonObject = getValue(key, new JSONObject(defValue));
        if (jsonObject != null) {
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String k = keys.next();
                try {
                    res.put(k, jsonObject.get(k));
                } catch (JSONException ignore) {
                }
            }
        }
        return res;
    }

    @Override
    protected void putValue(String key, Map<String, ?> value) {
        putValue(key, new JSONObject(value));
    }

    private JSONObject getValue(String key, JSONObject defValue) {
        try {
            return new JSONObject(mSecurePrefManager.getString(key, defValue.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void putValue(String key, JSONObject value) {
        String jsonString = value.toString();
        mSecurePrefManager.edit().putString(key, jsonString).apply();
    }
}