package com.kevadiyakrunalk.rxpreference.rxpref;

import com.kevadiyakrunalk.rxpreference.EncryptedPreferences;

/**
 * The type Integer preference.
 */
public class IntegerPreference extends BasePreference<Integer> {

  /**
   * Instantiates a new Integer preference.
   *
   * @param securePrefManager the secure pref manager
   */
  public IntegerPreference(EncryptedPreferences securePrefManager) {
    super(securePrefManager);
  }

  @Override
  protected Integer getValue(String key, Integer defValue) {
    return mSecurePrefManager.getInt(key, defValue);
  }

  @Override
  protected void putValue(String key, Integer value) {
    mSecurePrefManager.edit().putInt(key, value).apply();
  }
}