package com.parking.exception;

public class SlotNotFoundException extends RuntimeException {
    public SlotNotFoundException(String message) {

      super(message);
    }
}
