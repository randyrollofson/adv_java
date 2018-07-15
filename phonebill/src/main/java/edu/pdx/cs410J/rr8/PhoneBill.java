package edu.pdx.cs410J.rr8;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Class for managing phone bills
 */
public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
    private Collection<PhoneCall> calls = new ArrayList<>();
    private final String customerName;

    /**
     * Creates a new <code>PhoneBill</code>
     *
     * @param customerName
     *        The name of the customer that the phone bill belongs to
     */
    PhoneBill(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Returns the name of the customer whose phone bill this is
     * @return The name of the customer that the phone bill belongs to
     */
    @Override
    public String getCustomer() {
        return this.customerName;
    }

    /**
     * Adds a phone call to this phone bill
     * @param call
     *        A phoneCall object
     */
    @Override
    public void addPhoneCall(PhoneCall call) {
        this.calls.add(call);
    }

    /**
     * Returns all of the phone calls in this phone bill
     * @return A collection of phone calls belonging to this phone bill
     */
    @Override
    public Collection<PhoneCall> getPhoneCalls() {
        return this.calls;
    }
}
