package edu.pdx.cs410J.rr8;

import edu.pdx.cs410J.AbstractPhoneCall;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;

/**
 * Class for managing phone calls
 */
public class PhoneCall extends AbstractPhoneCall {
    private final String caller;
    private final String callee;
    private final String startTimeString;
    private final String endTimeString;
    private Date startDateTime;
    private Date endDateTime;
    private Long callDuration;

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
    PhoneCall(String caller, String callee, String startDate, String startTime, String startTimeAmPm, String endDate, String endTime, String endTimeAmPm) {
        this.caller = caller;
        this.callee = callee;
        this.startTimeString = startDate + ' ' + startTime + ' ' + startTimeAmPm;
        this.endTimeString = endDate + ' ' + endTime + ' ' + endTimeAmPm;
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

    public Long getCallDuration() {
        return this.callDuration;
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

    public void validateStartDateTime(String dateTime) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("M/d/yy h:mm a");
            Date date = formatter.parse(dateTime);

            if (!formatter.format(date).equals(dateTime)) {
                System.err.println("Incorrect date/time format");
                System.exit(1);
            }
            startDateTime = date;
        } catch (ParseException e){
            System.err.println("Date/time parsing error");
            System.exit(1);
        }
    }

    public void validateEndDateTime(String dateTime) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("M/d/yy h:mm a");
            Date date = formatter.parse(dateTime);

            if (!formatter.format(date).equals(dateTime)) {
                System.err.println("Incorrect date/time format");
                System.exit(1);
            }
            endDateTime = date;
        } catch (ParseException e){
            System.err.println("Date/time parsing error");
            System.exit(1);
        }
    }

    public void validateStartEndTimes(String fullStartDateTime, String fullEndDateTime) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("M/d/yy h:mm a");
            Date start = formatter.parse(fullStartDateTime);
            Date end = formatter.parse(fullEndDateTime);

            if (start.after(end)) {
                System.err.println("Error: Start date/time is after End date/time");
                System.exit(1);
            }
        } catch (ParseException e){
            System.err.println("Date/time parsing error");
            System.exit(1);
        }
    }

    public void setCallDuration() {
        long temp = endDateTime.getTime() - startDateTime.getTime();
        callDuration = temp / (60 * 1000);
    }
}
