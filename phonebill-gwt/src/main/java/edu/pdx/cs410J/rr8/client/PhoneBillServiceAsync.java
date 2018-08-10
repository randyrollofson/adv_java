package edu.pdx.cs410J.rr8.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The client-side interface to the phone bill service
 */
public interface PhoneBillServiceAsync {

    /**
     * Returns the a dummy Phone Bill
     */
    void getPhoneBill(String customerName, AsyncCallback<PhoneBill> async);

    void addPhoneCall(String customerName, PhoneCall call, AsyncCallback<Void> async);
}
