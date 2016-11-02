package com.kevadiyak.rxpreference.rxpref;

import com.kevadiyak.rxpreference.SecuredPreferenceStore;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rx.Observable;

/**
 * The type Rx shared preferences.
 */
public class RxSharedPreferences {

  /**
   * The constant DEBUG.
   */
  public static boolean DEBUG = true;

  /**
   * The M boolean preference.
   */
  protected final BooleanPreference mBooleanPreference;

  /**
   * The M float preference.
   */
  protected final FloatPreference mFloatPreference;

  /**
   * The M integer preference.
   */
  protected final IntegerPreference mIntegerPreference;

  /**
   * The M json preference.
   */
  protected final JSONObjectPreference mJSONPreference;

  /**
   * The M long preference.
   */
  protected final LongPreference mLongPreference;

  /**
   * The M map preference.
   */
  protected final MapPreference mMapPreference;

  /**
   * The M registered preferences.
   */
  protected final List<BasePreference> mRegisteredPreferences;

  /**
   * The M secure pref manager.
   */
  protected final SecuredPreferenceStore mSecurePrefManager;

  /**
   * The M string preference.
   */
  protected final StringPreference mStringPreference;

  /**
   * The M string set preference.
   */
  protected final StringSetPreference mStringSetPreference;

  /**
   * Instantiates a new Rx shared preferences.
   *
   * @param securePrefManager the secure pref manager
   */
  public RxSharedPreferences(SecuredPreferenceStore securePrefManager) {
    if (securePrefManager == null) {
      throw new RuntimeException("SharedPreferences can not be null");
    }
    mSecurePrefManager = securePrefManager;
    mBooleanPreference = new BooleanPreference(mSecurePrefManager);
    mFloatPreference = new FloatPreference(mSecurePrefManager);
    mIntegerPreference = new IntegerPreference(mSecurePrefManager);
    mJSONPreference = new JSONObjectPreference(mSecurePrefManager);
    mLongPreference = new LongPreference(mSecurePrefManager);
    mMapPreference = new MapPreference(mSecurePrefManager);
    mStringPreference = new StringPreference(mSecurePrefManager);
    mStringSetPreference = new StringSetPreference(mSecurePrefManager);

    mRegisteredPreferences = new ArrayList<>();
    register(mBooleanPreference);
    register(mFloatPreference);
    register(mIntegerPreference);
    register(mJSONPreference);
    register(mLongPreference);
    register(mMapPreference);
    register(mStringPreference);
    register(mStringSetPreference);
  }

  /**
   * Gets boolean.
   *
   * @param key      the key
   * @param defValue the def value
   * @return the boolean
   */
  public Boolean getBoolean(String key, Boolean defValue) {
    return mBooleanPreference.get(key, defValue);
  }

  /**
   * Gets float.
   *
   * @param key      the key
   * @param defValue the def value
   * @return the float
   */
  public Float getFloat(String key, Float defValue) {
    return mFloatPreference.get(key, defValue);
  }

  /**
   * Gets int.
   *
   * @param key      the key
   * @param defValue the def value
   * @return the int
   */
  public Integer getInt(String key, Integer defValue) {
    return mIntegerPreference.get(key, defValue);
  }

  /**
   * Gets json object.
   *
   * @param key      the key
   * @param defValue the def value
   * @return the json object
   */
  public JSONObject getJSONObject(String key, JSONObject defValue) {
    return mJSONPreference.get(key, defValue);
  }

  /**
   * Gets long.
   *
   * @param key      the key
   * @param defValue the def value
   * @return the long
   */
  public Long getLong(String key, Long defValue) {
    return mLongPreference.get(key, defValue);
  }

  /**
   * Gets map.
   *
   * @param key      the key
   * @param defValue the def value
   * @return the map
   */
  public Map<String, ?> getMap(String key, Map<String, ?> defValue) {
    return mMapPreference.get(key, defValue);
  }

  /**
   * Gets string.
   *
   * @param key      the key
   * @param defValue the def value
   * @return the string
   */
  public String getString(String key, String defValue) {
    return mStringPreference.get(key, defValue);
  }

  /**
   * Gets string set.
   *
   * @param key      the key
   * @param defValue the def value
   * @return the string set
   */
  public Set<String> getStringSet(String key, Set<String> defValue) {
    return mStringSetPreference.get(key, defValue);
  }

  /**
   * Observe boolean observable.
   *
   * @param key      the key
   * @param defValue the def value
   * @return the observable
   */
  public Observable<Boolean> observeBoolean(String key, Boolean defValue) {
    return mBooleanPreference.observe(key, defValue);
  }

  /**
   * Observe float observable.
   *
   * @param key      the key
   * @param defValue the def value
   * @return the observable
   */
  public Observable<Float> observeFloat(String key, Float defValue) {
    return mFloatPreference.observe(key, defValue);
  }

  /**
   * Observe int observable.
   *
   * @param key      the key
   * @param defValue the def value
   * @return the observable
   */
  public Observable<Integer> observeInt(String key, Integer defValue) {
    return mIntegerPreference.observe(key, defValue);
  }

  /**
   * Observe json object observable.
   *
   * @param key      the key
   * @param defValue the def value
   * @return the observable
   */
  public Observable<JSONObject> observeJSONObject(String key, JSONObject defValue) {
    return mJSONPreference.observe(key, defValue);
  }

  /**
   * Observe long observable.
   *
   * @param key      the key
   * @param defValue the def value
   * @return the observable
   */
  public Observable<Long> observeLong(String key, Long defValue) {
    return mLongPreference.observe(key, defValue);
  }

  /**
   * Observe map observable.
   *
   * @param key      the key
   * @param defValue the def value
   * @return the observable
   */
  public Observable<Map<String, ?>> observeMap(String key, Map<String, ?> defValue) {
    return mMapPreference.observe(key, defValue);
  }

  /**
   * Observe string observable.
   *
   * @param key      the key
   * @param defValue the def value
   * @return the observable
   */
  public Observable<String> observeString(String key, String defValue) {
    return mStringPreference.observe(key, defValue);
  }

  /**
   * Observe string set observable.
   *
   * @param key      the key
   * @param defValue the def value
   * @return the observable
   */
  public Observable<Set<String>> observeStringSet(String key, Set<String> defValue) {
    return mStringSetPreference.observe(key, defValue);
  }

  /**
   * Put boolean.
   *
   * @param key   the key
   * @param value the value
   */
  public void putBoolean(String key, Boolean value) {
    mBooleanPreference.put(key, value);
  }

  /**
   * Put float.
   *
   * @param key   the key
   * @param value the value
   */
  public void putFloat(String key, Float value) {
    mFloatPreference.put(key, value);
  }

  /**
   * Put int.
   *
   * @param key   the key
   * @param value the value
   */
  public void putInt(String key, Integer value) {
    mIntegerPreference.put(key, value);
  }

  /**
   * Put json object.
   *
   * @param key   the key
   * @param value the value
   */
  public void putJSONObject(String key, JSONObject value) {
    mJSONPreference.put(key, value);
  }

  /**
   * Put long.
   *
   * @param key   the key
   * @param value the value
   */
  public void putLong(String key, Long value) {
    mLongPreference.put(key, value);
  }

  /**
   * Put map.
   *
   * @param key   the key
   * @param value the value
   */
  public void putMap(String key, Map<String, ?> value) {
    mMapPreference.put(key, value);
  }

  /**
   * Put string.
   *
   * @param key   the key
   * @param value the value
   */
  public void putString(String key, String value) {
    mStringPreference.put(key, value);
  }

  /**
   * Put string set.
   *
   * @param key   the key
   * @param value the value
   */
  public void putStringSet(String key, Set<String> value) {
    mStringSetPreference.put(key, value);
  }

  /**
   * Reset.
   */
  public void reset() {
    int i = 0;
    for (int n = mRegisteredPreferences.size(); i < n; i++) {
      mRegisteredPreferences.get(i).reset();
    }
  }

  /**
   * Reset but keep.
   *
   * @param keys the keys
   */
  public void resetButKeep(List<String> keys) {
    int i = 0;
    for (int n = mRegisteredPreferences.size(); i < n; i++) {
      mRegisteredPreferences.get(i).resetButKeep(keys);
    }
  }

  /**
   * Register.
   *
   * @param basePreference the base preference
   */
  protected void register(BasePreference basePreference) {
    mRegisteredPreferences.add(basePreference);
  }
}