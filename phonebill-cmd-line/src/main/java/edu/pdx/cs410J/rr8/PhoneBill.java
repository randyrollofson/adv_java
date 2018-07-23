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

    public static Comparator<PhoneCall> COMPARE_BY_START_TIME = new Comparator<PhoneCall>() {
        public int compare(PhoneCall one, PhoneCall other) {
            if (one.getStartTimeString().compareTo(other.getStartTimeString()) == 0) {
                return one.getCaller().replaceAll("-", "").compareTo(other.getCaller().replaceAll("-", ""));
            }
            return one.getStartTimeString().compareTo(other.getStartTimeString());
        }
    };

    public void sortBill() {
        List<PhoneCall> phoneCallList = new ArrayList<>(calls);
        Collections.sort(phoneCallList, COMPARE_BY_START_TIME);
        calls = phoneCallList;
    }

//    @Override
//    public int compare(PhoneCall o1, PhoneCall o2) {
//        if (o1.getStartTimeString().compareTo(o2.getStartTimeString()) == 0) {
//            return o1.getCaller().replaceAll("-", "").compareTo(o2.getCaller().replaceAll("-", ""));
//        }
//
//        return o1.getStartTimeString().compareTo(o2.getStartTimeString());
//    }
}

