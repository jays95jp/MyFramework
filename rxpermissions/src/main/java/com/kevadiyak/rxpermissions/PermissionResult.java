package com.kevadiyak.rxpermissions;

/**
 * The interface Permission result.
 */
public interface PermissionResult {
    /**
     * On permission result.
     *
     * @param permission the permission
     * @param granted    the granted
     */
    public void onPermissionResult(String permission, boolean granted);
}
