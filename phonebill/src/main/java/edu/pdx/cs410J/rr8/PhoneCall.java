package edu.pdx.cs410J.rr8;

import edu.pdx.cs410J.AbstractPhoneCall;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class for managing phone calls
 */
public class PhoneCall extends AbstractPhoneCall {
    private final String caller;
    private final String callee;
    private final String startTimeString;
    private final String endTimeString;

    /**
     * Creates a new <code>PhoneCall</code>
     *
     * @param caller
     *        The phone number of the caller
     * @param callee
     *        The phone number of the callee
     * @param startTime
     *        The time that the call began (24-hour time)
     * @param endTime
     *        The time that the call began (24-hour time)
     * @param startDate
     *        The date that the call began
     * @param endDate
     *        The date that the call ended
     */
    PhoneCall(String caller, String callee, String startDate, String startTime, String endDate, String endTime) {
        this.caller = caller;
        this.callee = callee;
        this.startTimeString = startDate + ' ' + startTime;
        this.endTimeString = endDate + ' ' + endTime;
    }

    /**
     * Returns the phone number of the person who originated this phone call
     * @return The phone number of the caller
     */
    @Override
    public String getCaller() {
        return this.caller;
    }

    /**
     * Returns the phone number of the person who received this phone call.
     * @return The phone number of the callee
     */
    @Override
    public String getCallee() {
        return this.callee;
    }

    /**
     * Returns the date and time that this phone call was originated
     * @return The date and time that the call began (24-hour time)
     */
    @Override
    public String getStartTimeString() {
        return this.startTimeString;
    }

    /**
     * Returns the date and time that this phone call ended
     * @return The date and time that the call ended (24-hour time)
     */
    @Override
    public String getEndTimeString() {
        return this.endTimeString;
    }

    /**
     * Returns whether or not a phone number has the correct format
     * @param phoneNumber
     *        The phone number to be validated
     * @return True/false based on validation
     */
    boolean isValidPhoneNumber(String phoneNumber) {
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

    /**
     * Returns whether or not a data the correct format
     * @param date
     *        The data (as String) to be validated
     * @return True/false based on validation
     */
    boolean isValidDate(String date) {
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

    /**
     *
     * @param time
     *        The time (as String) to be validated
     * @return True/false based on validation
     */
    boolean isValidTime(String time) {
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
