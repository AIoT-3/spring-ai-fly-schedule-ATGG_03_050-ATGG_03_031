package com.nhnacademy.flyschedule.exception;

public class AirlineNotFoundException extends RuntimeException {
    public AirlineNotFoundException(String airlineName) {
        super("Not Found Airline" + airlineName);
    }
}
