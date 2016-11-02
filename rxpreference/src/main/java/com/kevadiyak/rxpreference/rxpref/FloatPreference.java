package com.kevadiyak.rxpreference.rxpref;

import com.kevadiyak.rxpreference.SecuredPreferenceStore;

/**
 * The type Float preference.
 */
public class FloatPreference extends BasePreference<Float> {

  /**
   * Instantiates a new Float preference.
   *
   * @param securePrefManager the secure pref manager
   */
  public FloatPreference(SecuredPreferenceStore securePrefManager) {
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