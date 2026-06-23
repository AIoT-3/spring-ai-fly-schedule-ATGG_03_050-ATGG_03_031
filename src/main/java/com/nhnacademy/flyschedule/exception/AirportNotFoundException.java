package com.nhnacademy.flyschedule.exception;

public class AirportNotFoundException extends RuntimeException {
    public AirportNotFoundException(String airportName) {
        super("Not Found Airport: " + airportName);
    }
}
