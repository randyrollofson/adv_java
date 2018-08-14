package edu.pdx.cs410J.rr8.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.*;

/**
 * A GWT remote service that returns a dummy Phone Bill
 */
@RemoteServiceRelativePath("phoneBill")
public interface PhoneBillService extends RemoteService {

    /**
     * Gets a phone bill for a specific customer
     * @param customerName
     *        customer name on phone bill
     * @return phone bill
     */
    public PhoneBill getPhoneBill(String customerName);

    /**
     * Adds phone call to a phone bill
     * @param customerName
     *        name of customer on phone bill
     * @param call
     *        phone call to add
     */
    public void addPhoneCall(String customerName, PhoneCall call);

    /**
     * Searches all phone bills based on customer name and date/time range
     * @param customerName
     *        name of customer to search for
     * @param startDateTime
     *        start range for search
     * @param endDateTime
     *        end range for search
     * @return all calls from a customer's bill between the specified date/time range
     */
    public PhoneBill searchPhoneBill(String customerName, Date startDateTime, Date endDateTime);
}
