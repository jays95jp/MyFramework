package com.kevadiyak.rxpreference.rxpref;

import com.kevadiyak.rxpreference.SecuredPreferenceStore;

/**
 * The type String preference.
 */
public class StringPreference extends BasePreference<String> {

  /**
   * Instantiates a new String preference.
   *
   * @param securePrefManager the secure pref manager
   */
  public StringPreference(SecuredPreferenceStore securePrefManager) {
    super(securePrefManager);
  }

  @Override
  protected String getValue(String key, String defValue) {
    return mSecurePrefManager.getString(key, defValue);
  }

  @Override
  protected void putValue(String key, String value) {
    mSecurePrefManager.edit().putString(key, value).apply();
  }
}