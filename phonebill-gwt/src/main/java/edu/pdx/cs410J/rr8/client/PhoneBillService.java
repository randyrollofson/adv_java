package edu.pdx.cs410J.rr8.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * A GWT remote service that returns a dummy Phone Bill
 */
@RemoteServiceRelativePath("phoneBill")
public interface PhoneBillService extends RemoteService {

    /**
     * Returns the a dummy Phone Bill
     */
    public PhoneBill getPhoneBill(String customerName);

    public void addPhoneCall(String customerName, PhoneCall call);
}
