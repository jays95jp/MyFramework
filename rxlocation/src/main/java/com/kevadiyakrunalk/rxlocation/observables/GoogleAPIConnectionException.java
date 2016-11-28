package com.kevadiyakrunalk.rxlocation.observables;

import com.google.android.gms.common.ConnectionResult;

/**
 * The type Google api connection exception.
 */
public class GoogleAPIConnectionException extends RuntimeException {
    private final ConnectionResult connectionResult;

    /**
     * Instantiates a new Google api connection exception.
     *
     * @param detailMessage    the detail message
     * @param connectionResult the connection result
     */
    GoogleAPIConnectionException(String detailMessage, ConnectionResult connectionResult) {
        super(detailMessage);
        this.connectionResult = connectionResult;
    }

    /**
     * Gets connection result.
     *
     * @return the connection result
     */
    public ConnectionResult getConnectionResult() {
        return connectionResult;
    }
}
