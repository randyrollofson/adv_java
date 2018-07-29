package edu.pdx.cs410J.rr8;

public class NoSuchPhoneBillException extends RuntimeException {
    private final String customerName;

    public NoSuchPhoneBillException(String customerName) {
        this.customerName = customerName;
    }
}