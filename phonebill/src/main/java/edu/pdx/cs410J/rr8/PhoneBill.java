package edu.pdx.cs410J.rr8;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.ArrayList;
import java.util.Collection;

public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
    private Collection<PhoneCall> calls = new ArrayList<>();
    private final String customerName;

    public PhoneBill(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String getCustomer() {
        return this.customerName;
    }

    @Override
    public void addPhoneCall(PhoneCall call) {
        this.calls.add(call);
    }

    @Override
    public Collection<PhoneCall> getPhoneCalls() {
        return this.calls;
    }
}
