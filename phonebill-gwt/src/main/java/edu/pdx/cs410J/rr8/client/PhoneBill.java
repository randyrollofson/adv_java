package edu.pdx.cs410J.rr8.client;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.lang.Override;
import java.util.ArrayList;
import java.util.Collection;

public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
    private Collection<PhoneCall> calls = new ArrayList<>();
    private String customerName;

    /**
     * In order for GWT to serialize this class (so that it can be sent between
     * the client and the server), it must have a zero-argument constructor.
     */
    public PhoneBill() {

    }

    /**
     * Creates a new <code>PhoneBill</code>
     * @param customerName
     *        The name of the customer that the phone bill belongs to
     */
    public PhoneBill(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Gets customer name
     * @return customer name
     */
    @Override
    public String getCustomer() {
        return customerName;
    }

    /**
     * Adds a phone call to a list of calls
     * @param call
     *        phone call to add
     */
    @Override
    public void addPhoneCall(PhoneCall call) {
        this.calls.add(call);
    }

    /**
     * Gets all phone calls
     * @return phone calls
     */
    @Override
    public Collection<PhoneCall> getPhoneCalls() {
        return this.calls;
    }
}
