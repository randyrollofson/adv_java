package edu.pdx.cs410J.rr8.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * A GWT remote service that returns a dummy Phone Bill
 */
@RemoteServiceRelativePath("phoneBill")
public interface PhoneBillService extends RemoteService {

    public PhoneBill addPhoneCall();

    /**
     * Returns the a dummy Phone Bill
     */
    public PhoneBill getPhoneBill();

    /**
     * Always throws an undeclared exception so that we can see GWT handles it.
     */
    void throwUndeclaredException();

    /**
     * Always throws a declared exception so that we can see GWT handles it.
     */
    void throwDeclaredException() throws IllegalStateException;

}
