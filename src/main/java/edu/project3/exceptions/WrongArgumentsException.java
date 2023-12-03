package edu.project3.exceptions;

public class WrongArgumentsException extends RuntimeException {

    public WrongArgumentsException(String message) {
        super(message);
    }

    public WrongArgumentsException(String message, Throwable cause) {
        super(message, cause);
    }

}
