package edu.pdx.cs410J.rr8;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        //return "Start Time";
    }

    private String formatDate(Date date) {
        return DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(date);
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

    @Override
    public Date getStartTime() {
        return this.startTime;
    }

    @Override
    public Date getEndTime() {
        return this.endTime;
    }

//    /**
//     * Returns original startTimeString from command line
//     * @return original startTimeString from command line
//     */
//    public String getOriginalStartTimeString() {
//        return this.startTimeString;
//    }
//
//    /**
//     * Returns original endTimeString from command line
//     * @return original endTimeString from command line
//     */
//    public String getOriginalEndTimeString() {
//        return this.endTimeString;
//    }
//
//    /**
//     * Returns the duration of the call in minutes
//     * @return the duration of the call in minutes
//     */
//    public Long getCallDuration() {
//        return this.callDuration;
//    }

//    /**
//     * Sets the startDateTime Date object
//     * @param startDateTime
//     *        Date object for start date and time
//     */
//    public void setStartDateTime(Date startTime) {
//        this.startDateTime = startDateTime;
//    }
//
//    /**
//     * Sets the endDateTime Date object
//     * @param endDateTime
//     *        Date object for end date and time
//     */
//    public void setEndDateTime(Date endDateTime) {
//        this.endDateTime = endDateTime;
//    }

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
     * Validates the format of the start date and time, returns as Date object
     * @param dateTime
     *        Date and time string from the command line
     * @return Date object containing formatted date and time
     */
//    public Date validateAndReturnStartDateTime(String dateTime) {
//        try {
//            SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
//            SimpleDateFormat formatter2 = new SimpleDateFormat("MM/dd/yyyy h:mm a");
//            Date date1 = formatter1.parse(dateTime);
//            Date date2 = formatter2.parse(dateTime);
//
//            if (formatter1.format(date1).equals(dateTime)) {
//                DateFormat originalFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
//                DateFormat targetFormat = new SimpleDateFormat("M/d/yy h:mm a");
//                Date date = originalFormat.parse(dateTime);
//                prettyStartTimeString = targetFormat.format(date);
//
//                return date1;
//            } else if (formatter2.format(date2).equals(dateTime)) {
//                DateFormat originalFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a");
//                DateFormat targetFormat = new SimpleDateFormat("M/d/yy h:mm a");
//                Date date = originalFormat.parse(dateTime);
//                prettyStartTimeString = targetFormat.format(date);
//
//                return date2;
//            } else {
//                System.err.println("Incorrect date/time format");
//                System.exit(1);
//            }
//        } catch (ParseException e){
//            System.err.println("Date parsing error");
//            System.exit(1);
//        }
//
//        return null;
//    }

    /**
     * Validates the format of the end date and time, returns as Date object
     * @param dateTime
     *        Date and time string from the command line
     * @return Date object containing formatted date and time
     */
//    public Date validateAndReturnEndDateTime(String dateTime) {
//        try {
//            SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
//            SimpleDateFormat formatter2 = new SimpleDateFormat("MM/dd/yyyy h:mm a");
//
//            Date date1 = formatter1.parse(dateTime);
//            Date date2 = formatter2.parse(dateTime);
//
//            if (formatter1.format(date1).equals(dateTime)) {
//                DateFormat originalFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
//                DateFormat targetFormat = new SimpleDateFormat("M/d/yy h:mm a");
//                Date date = originalFormat.parse(dateTime);
//                prettyEndTimeString = targetFormat.format(date);
//
//                return date1;
//            } else if (formatter2.format(date2).equals(dateTime)) {
//                DateFormat originalFormat = new SimpleDateFormat("M/dd/yyyy h:mm a");
//                DateFormat targetFormat = new SimpleDateFormat("M/d/yy h:mm a");
//                Date date = originalFormat.parse(dateTime);
//                prettyEndTimeString = targetFormat.format(date);
//
//                return date2;
//            } else {
//                System.err.println("Incorrect date/time format");
//                System.exit(1);
//            }
//        } catch (ParseException e){
//            System.err.println("Date parsing error");
//            System.exit(1);
//        }
//
//        return null;
//    }

    /**
     * Checks that the start date/time is before end date/time
     * @param fullStartDateTime
     *        start date/time string
     * @param fullEndDateTime
     *        end date/time string
     */
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

    /**
     * Sets the duration of the call in minutes
     */
//    public void setCallDuration() {
//        long temp = endDateTime.getTime() - startDateTime.getTime();
//        callDuration = temp / (60 * 1000);
//    }
}
