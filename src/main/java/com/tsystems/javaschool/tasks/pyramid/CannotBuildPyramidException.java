package com.tsystems.javaschool.tasks.pyramid;

public class CannotBuildPyramidException extends RuntimeException {
    public CannotBuildPyramidException() {
    }

    public CannotBuildPyramidException(String message) {
        super(message);
    }

    public CannotBuildPyramidException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotBuildPyramidException(Throwable cause) {
        super(cause);
    }

    public CannotBuildPyramidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
