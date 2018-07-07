package edu.pdx.cs410J.rr8;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhoneCall extends AbstractPhoneCall {
    private final String caller;
    private final String callee;
    private final String startTime;
    private final String endTime;
    private final String startDate;
    private final String endDate;

    public PhoneCall(String caller, String callee, String startTime, String endTime, String startDate, String endDate) {
        this.caller = caller;
        this.callee = callee;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String getCaller() {
        return this.caller;
    }

    @Override
    public String getCallee() {
        return this.callee;
    }

    @Override
    public String getStartTimeString() {
        return this.startDate + ' ' + this.startTime;
    }

    @Override
    public String getEndTimeString() {
        return this.endDate + ' ' + this.endTime;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        //Check for standard format
        if (phoneNumber.length() != 12 || phoneNumber.charAt(3) != '-' || phoneNumber.charAt(7) != '-') {
            System.err.println("Incorrect phone number format. Must be xxx-xxx-xxxx");

            return false;
        }
        //Check for non-numeric characters
        String strippedPhoneNumber = phoneNumber.replaceAll("-", "");
        if (!strippedPhoneNumber.matches("^[0-9]+$")) {
            System.err.println("Phone number contains characters that are non-numeric");

            return false;
        }

        return true;
    }

    public boolean isValidDate(String date) {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat formatter2 = new SimpleDateFormat("M/dd/yyyy");
            SimpleDateFormat formatter3 = new SimpleDateFormat("MM/d/yyyy");
            SimpleDateFormat formatter4 = new SimpleDateFormat("M/d/yyyy");
            Date date1 = formatter1.parse(date);
            Date date2 = formatter2.parse(date);
            Date date3 = formatter3.parse(date);
            Date date4 = formatter4.parse(date);

            if (!formatter1.format(date1).equals(date) && !formatter2.format(date2).equals(date) &&
                    !formatter3.format(date3).equals(date) && !formatter4.format(date4).equals(date)) {
                System.err.println("Incorrect data format");

                return false;
            }
        } catch (ParseException e){
            System.err.println("Date parsing error");
        }

        return true;
    }

    public boolean isValidTime(String time) {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("kk:mm");
            SimpleDateFormat formatter2 = new SimpleDateFormat("k:mm");
            Date date1 = formatter1.parse(time);
            Date date2 = formatter2.parse(time);

            if (!formatter1.format(date1).equals(time) && !formatter2.format(date2).equals(time)) {
                System.err.println("Incorrect time format");
                return false;
            }
        } catch (ParseException e){
            System.err.println("Time parsing error");
        }

        return true;
    }


}
