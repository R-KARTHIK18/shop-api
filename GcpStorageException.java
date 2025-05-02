package com.pubsub.process.GCPStorage;

public class GcpStorageException extends RuntimeException {

    private String statusCode;
    private Object error;

    public GcpStorageException(String message) {
        super(message);
    }

    public GcpStorageException(String message, String statusCode, Object error) {
        super(message);
        this.statusCode = statusCode;
        this.error = error;
    }

}
