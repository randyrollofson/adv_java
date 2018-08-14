package edu.pdx.cs410J.rr8.client;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.lang.Override;
import java.util.Date;
import com.google.gwt.i18n.client.DateTimeFormat;

public class PhoneCall extends AbstractPhoneCall {
    private String callerNumber;
    private String calleeNumber;
    private Date startTime;
    private Date endTime;


    /**
     * In order for GWT to serialize this class (so that it can be sent between
     * the client and the server), it must have a zero-argument constructor.
     */
    public PhoneCall() {

    }

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
     * Gets caller number
     * @return caller number
     */
    @Override
    public String getCaller() {
        return callerNumber;
    }

    /**
     * Gets start time
     * @return start time
     */
    @Override
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Gets start time as string
     * @return start time string
     */
    @Override
    public String getStartTimeString() {
        String dateString = DateTimeFormat.getFormat("M/dd/yy h:mm a").format(this.startTime);

        return dateString;
    }

    /**
     * Gets callee number
     * @return callee number
     */
    @Override
    public String getCallee() {
        return calleeNumber;
    }

    /**
     * Gets end time
     * @return end time
     */
    @Override
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Gets end time as string
     * @return end time string
     */
    @Override
    public String getEndTimeString() {
        String dateString = DateTimeFormat.getFormat("M/dd/yy h:mm a").format(this.endTime);

        return dateString;
    }
}
