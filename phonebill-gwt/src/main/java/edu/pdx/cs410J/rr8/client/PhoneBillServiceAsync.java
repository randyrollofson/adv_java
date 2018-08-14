package edu.pdx.cs410J.rr8.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.*;

/**
 * The client-side interface to the phone bill service
 */
public interface PhoneBillServiceAsync {

    /**
     * Gets phone bill
     * @param customerName
     *        owner of phone bill
     * @param async
     *        asynchronous callback
     */
    void getPhoneBill(String customerName, AsyncCallback<PhoneBill> async);

    /**
     * Adds phone call to phone bill
     * @param customerName
     *        owner of phone bill
     * @param call
     *        phone call to add
     * @param async
     *        asynchronous callback
     */
    void addPhoneCall(String customerName, PhoneCall call, AsyncCallback<Void> async);

    /**
     * Searches all phone bills based on customer name and date/time range
     * @param customerName
     *        name of customer to search for
     * @param startDateTime
     *        start range for search
     * @param endDateTime
     *        end range for search
     * @param async
     *        asynchronous callback
     */
    void searchPhoneBill(String customerName, Date startDateTime, Date endDateTime, AsyncCallback<PhoneBill> async);
}
