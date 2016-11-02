package com.kevadiyak.rxpreference.rxpref;

import com.kevadiyak.rxpreference.SecuredPreferenceStore;

/**
 * The type Long preference.
 */
public class LongPreference extends BasePreference<Long> {

  /**
   * Instantiates a new Long preference.
   *
   * @param sharedPreferences the shared preferences
   */
  public LongPreference(SecuredPreferenceStore sharedPreferences) {
    super(sharedPreferences);
  }

  @Override
  protected Long getValue(String key, Long defValue) {
    return mSecurePrefManager.getLong(key, defValue);
  }

  @Override
  protected void putValue(String key, Long value) {
    mSecurePrefManager.edit().putLong(key, value).apply();
  }
}