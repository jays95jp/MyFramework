package com.kevadiyakrunalk.rxlocation.observables;

import com.google.android.gms.common.api.Status;

/**
 * The type Status exception.
 */
public class StatusException extends Throwable {
    private final Status status;

    /**
     * Instantiates a new Status exception.
     *
     * @param status the status
     */
    public StatusException(Status status) {
        this.status = status;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public Status getStatus() {
        return status;
    }
}
