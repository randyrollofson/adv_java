package edu.pdx.cs410J.rr8.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.*;

/**
 * The client-side interface to the phone bill service
 */
public interface PhoneBillServiceAsync {

    /**
     * Returns the a dummy Phone Bill
     */
    void getPhoneBill(String customerName, AsyncCallback<PhoneBill> async);

    void addPhoneCall(String customerName, PhoneCall call, AsyncCallback<Void> async);

    void searchPhoneBill(String customerName, Date startDateTime, Date endDateTime, AsyncCallback<PhoneBill> async);
}
