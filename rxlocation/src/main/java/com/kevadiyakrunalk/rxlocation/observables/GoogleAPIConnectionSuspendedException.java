package com.kevadiyakrunalk.rxlocation.observables;

/**
 * The type Google api connection suspended exception.
 */
public class GoogleAPIConnectionSuspendedException extends RuntimeException {
    private final int cause;

    /**
     * Instantiates a new Google api connection suspended exception.
     *
     * @param cause the cause
     */
    GoogleAPIConnectionSuspendedException(int cause) {
        this.cause = cause;
    }

    /**
     * Gets error cause.
     *
     * @return the error cause
     */
    public int getErrorCause() {
        return cause;
    }
}
