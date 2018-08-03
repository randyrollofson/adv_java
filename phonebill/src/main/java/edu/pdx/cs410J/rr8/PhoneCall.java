package edu.pdx.cs410J.rr8;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.text.DateFormat;
import java.util.Date;

/**
 * Class for managing phone calls
 */
public class PhoneCall extends AbstractPhoneCall {
    private final String callerNumber;
    private final String calleeNumber;
    private final Date startTime;
    private final Date endTime;


    /**
     * Creates a new <code>PhoneCall</code>
     *
     * @param callerNumber
     *        The phone number of the caller
     * @param calleeNumber
     *        The phone number of the callee
     * @param startTime
     *        The time that the call began (24-hour time)
     * @param endTime
     *        The time that the call began (24-hour time)
     */
    public PhoneCall(String callerNumber, String calleeNumber, Date startTime, Date endTime) {
        this.callerNumber = callerNumber;
        this.calleeNumber = calleeNumber;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Returns the phone number of the person who originated this phone call
     * @return The phone number of the caller
     */
    @Override
    public String getCaller() {
        return this.callerNumber;
    }

    /**
     * Returns the phone number of the person who received this phone call.
     * @return The phone number of the callee
     */
    @Override
    public String getCallee() {
        return this.calleeNumber;
    }

    /**
     * Returns the date and time that this phone call was originated
     * @return The date and time that the call began (12-hour time, SHORT format)
     */
    @Override
    public String getStartTimeString() {
        return formatDate(this.startTime);
    }

    /**
     * Formats date to SHORT
     * @param date
     *        date object
     * @return formatted date as string
     */
    private String formatDate(Date date) {
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(date);
    }

    /**
     * Returns the date and time that this phone call ended
     * @return The date and time that the call ended (12-hour time, SHORT format)
     */
    @Override
    public String getEndTimeString() {
        return formatDate(this.endTime);
        //return "End Time";
    }

    /**
     * Ruturns start time
     * @return start time date
     */
    @Override
    public Date getStartTime() {
        return this.startTime;
    }

    /**
     * Returns end time
     * @return end time date
     */
    @Override
    public Date getEndTime() {
        return this.endTime;
    }

    /**
     * Returns whether or not a phone number has the correct format
     * @param phoneNumber
     *        The phone number to be validated
     * @return True/false based on validation
     */
    public void validatePhoneNumber(String phoneNumber) {
        //Check for standard format
        if (phoneNumber.length() != 12 || phoneNumber.charAt(3) != '-' || phoneNumber.charAt(7) != '-') {
            System.err.println("Incorrect phone number format. Must be xxx-xxx-xxxx");
            System.exit(1);
        }
        //Check for non-numeric characters
        String strippedPhoneNumber = phoneNumber.replaceAll("-", "");
        if (!strippedPhoneNumber.matches("^[0-9]+$")) {
            System.err.println("Phone number contains characters that are non-numeric");
            System.exit(1);
        }
    }

    /**
     * Sets the duration of the call in minutes
     */
}
