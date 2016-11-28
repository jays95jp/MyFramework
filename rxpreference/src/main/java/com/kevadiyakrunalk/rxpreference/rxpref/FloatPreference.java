package com.kevadiyakrunalk.rxpreference.rxpref;

import com.kevadiyakrunalk.rxpreference.EncryptedPreferences;

/**
 * The type Float preference.
 */
public class FloatPreference extends BasePreference<Float> {

  /**
   * Instantiates a new Float preference.
   *
   * @param securePrefManager the secure pref manager
   */
  public FloatPreference(EncryptedPreferences securePrefManager) {
    super(securePrefManager);
  }

  @Override
  protected Float getValue(String key, Float defValue) {
    return mSecurePrefManager.getFloat(key, defValue);
  }

  @Override
  protected void putValue(String key, Float value) {
    mSecurePrefManager.edit().putFloat(key, value).apply();
  }
}