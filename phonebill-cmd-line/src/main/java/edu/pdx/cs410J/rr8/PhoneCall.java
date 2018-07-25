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
    private String prettyStartTimeString;
    private String prettyEndTimeString;
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
        return this.prettyStartTimeString;
    }

    /**
     * Returns the date and time that this phone call ended
     * @return The date and time that the call ended (24-hour time)
     */
    @Override
    public String getEndTimeString() {
        return this.prettyEndTimeString;
    }

    public Long getCallDuration() {
        return this.callDuration;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
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

    public Date validateAndReturnStartDateTime(String dateTime) {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            SimpleDateFormat formatter2 = new SimpleDateFormat("MM/dd/yyyy h:mm a");
//            SimpleDateFormat formatter3 = new SimpleDateFormat("MM/d/yyyy hh:mm a");
//            SimpleDateFormat formatter4 = new SimpleDateFormat("MM/d/yyyy h:mm a");
            //SimpleDateFormat formatter5 = new SimpleDateFormat("M/d/yy hh:mm a");
            Date date1 = formatter1.parse(dateTime);
            Date date2 = formatter2.parse(dateTime);
//            Date date3 = formatter3.parse(dateTime);
//            Date date4 = formatter4.parse(dateTime);
            //Date date5 = formatter5.parse(dateTime);

            if (formatter1.format(date1).equals(dateTime)) {
                DateFormat originalFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                DateFormat targetFormat = new SimpleDateFormat("M/d/yy h:mm a");
                Date date = originalFormat.parse(dateTime);
                prettyStartTimeString = targetFormat.format(date);

                return date1;
            } else if (formatter2.format(date2).equals(dateTime)) {
                DateFormat originalFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a");
                DateFormat targetFormat = new SimpleDateFormat("M/d/yy h:mm a");
                Date date = originalFormat.parse(dateTime);
                prettyStartTimeString = targetFormat.format(date);

                return date2;
//            } else if (formatter3.format(date3).equals(dateTime)) {
//                DateFormat originalFormat = new SimpleDateFormat("MM/d/yyyy hh:mm a");
//                DateFormat targetFormat = new SimpleDateFormat("M/d/yy h:mm a");
//                Date date = originalFormat.parse(dateTime);
//                prettyStartTimeString = targetFormat.format(date);
//
//                return date3;
//            } else if (formatter4.format(date4).equals(dateTime)) {
//                DateFormat originalFormat = new SimpleDateFormat("M/d/yyyy hh:mm a");
//                DateFormat targetFormat = new SimpleDateFormat("M/d/yy h:mm a");
//                Date date = originalFormat.parse(dateTime);
//                prettyStartTimeString = targetFormat.format(date);
//
//                return date4;
//            } else if (formatter5.format(date5).equals(dateTime)) {
//                prettyStartTimeString =dateTime;
//
//                return date5;
            } else {
                System.err.println("Incorrect date/time format");
                System.exit(1);
            }
        } catch (ParseException e){
            System.err.println("Date parsing error");
            System.exit(1);
        }

        return null;
    }

    public Date validateAndReturnEndDateTime(String dateTime) {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            SimpleDateFormat formatter2 = new SimpleDateFormat("MM/dd/yyyy h:mm a");
//            SimpleDateFormat formatter3 = new SimpleDateFormat("MM/d/yyyy hh:mm a");
//            SimpleDateFormat formatter4 = new SimpleDateFormat("M/d/yyyy hh:mm a");
//            SimpleDateFormat formatter5 = new SimpleDateFormat("M/d/yy hh:mm a");
            Date date1 = formatter1.parse(dateTime);
            Date date2 = formatter2.parse(dateTime);
//            Date date3 = formatter3.parse(dateTime);
//            Date date4 = formatter4.parse(dateTime);
//            Date date5 = formatter5.parse(dateTime);

            if (formatter1.format(date1).equals(dateTime)) {
                DateFormat originalFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                DateFormat targetFormat = new SimpleDateFormat("M/d/yy h:mm a");
                Date date = originalFormat.parse(dateTime);
                prettyEndTimeString = targetFormat.format(date);

                return date1;
            } else if (formatter2.format(date2).equals(dateTime)) {
                DateFormat originalFormat = new SimpleDateFormat("M/dd/yyyy h:mm a");
                DateFormat targetFormat = new SimpleDateFormat("M/d/yy h:mm a");
                Date date = originalFormat.parse(dateTime);
                prettyEndTimeString = targetFormat.format(date);

                return date2;
//            } else if (formatter3.format(date3).equals(dateTime)) {
//                DateFormat originalFormat = new SimpleDateFormat("MM/d/yyyy hh:mm a");
//                DateFormat targetFormat = new SimpleDateFormat("M/d/yy h:mm a");
//                Date date = originalFormat.parse(dateTime);
//                prettyEndTimeString = targetFormat.format(date);
//
//                return date3;
//            } else if (formatter4.format(date4).equals(dateTime)) {
//                DateFormat originalFormat = new SimpleDateFormat("M/d/yyyy hh:mm a");
//                DateFormat targetFormat = new SimpleDateFormat("M/d/yy h:mm a");
//                Date date = originalFormat.parse(dateTime);
//                prettyEndTimeString = targetFormat.format(date);
//
//                return date4;
//            } else if (formatter5.format(date5).equals(dateTime)) {
//                prettyEndTimeString =dateTime;
//
//                return date5;
            } else {
                System.err.println("Incorrect date/time format");
                System.exit(1);
            }
        } catch (ParseException e){
            System.err.println("Date parsing error");
            System.exit(1);
        }

        return null;
    }

//    public void validateEndDateTime(String dateTime) {
//        try {
//            SimpleDateFormat formatter = new SimpleDateFormat("M/d/yy h:mm a");
//            Date date = formatter.parse(dateTime);
//
//            if (!formatter.format(date).equals(dateTime)) {
//                System.err.println("Incorrect date/time format");
//                System.exit(1);
//            }
//            endDateTime = date;
//        } catch (ParseException e){
//            System.err.println("Date/time parsing error");
//            System.exit(1);
//        }
//    }

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
