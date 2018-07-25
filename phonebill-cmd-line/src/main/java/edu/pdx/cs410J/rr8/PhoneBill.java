package edu.pdx.cs410J.rr8;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.*;

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

    /**
     * Compares each phone call in the phone bill and sorts them by date/time. If date/time are the same, they are sorted by caller number
     */
    public static Comparator<PhoneCall> COMPARE_BY_START_TIME = new Comparator<PhoneCall>() {
        public int compare(PhoneCall one, PhoneCall other) {
            if (one.getStartTimeString().compareTo(other.getStartTimeString()) == 0) {
                return one.getCaller().replaceAll("-", "").compareTo(other.getCaller().replaceAll("-", ""));
            }
            return one.getStartTimeString().compareTo(other.getStartTimeString());
        }
    };

    /**
     * Helper function that sorts a list of phone calls in a phone bill
     */
    public void sortBill() {
        List<PhoneCall> phoneCallList = new ArrayList<>(calls);
        Collections.sort(phoneCallList, COMPARE_BY_START_TIME);
        calls = phoneCallList;
    }
}

