package com.amitnehra.exceptions;

public class NoRequestFound extends RuntimeException {
    public NoRequestFound(String message) {
        super(message);
    }
}
