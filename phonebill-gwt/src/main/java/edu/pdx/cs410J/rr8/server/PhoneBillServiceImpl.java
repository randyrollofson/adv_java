package edu.pdx.cs410J.rr8.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.rr8.client.PhoneBill;
import edu.pdx.cs410J.rr8.client.PhoneCall;
import edu.pdx.cs410J.rr8.client.PhoneBillService;

import java.util.*;

/**
 * The server-side implementation of the Phone Bill service
 */
public class PhoneBillServiceImpl extends RemoteServiceServlet implements PhoneBillService {
    private List<PhoneBill> bills = new ArrayList<>();

    @Override
    public void addPhoneCall(String customerName, PhoneCall call) {
        for (PhoneBill bill : bills) {
            if (bill.getCustomer().equals(customerName)) {
                bill.addPhoneCall(call);
                return;
            }
        }
        PhoneBill phoneBill = new PhoneBill(customerName);
        phoneBill.addPhoneCall(call);
        bills.add(phoneBill);
    }

    @Override
    public PhoneBill getPhoneBill(String customerName) {
        for (PhoneBill bill : bills) {
            if (bill.getCustomer().equals(customerName)) {
                return bill;
            }
        }

        return null;
    }

    @Override
    public PhoneBill searchPhoneBill(String customerName, Date startDateTime, Date endDateTime) {
        PhoneBill phoneBill = getPhoneBill(customerName);

        if (phoneBill == null) {
            return null;
        }

        PhoneBill billToReturn = new PhoneBill(customerName);

        for (PhoneCall call : phoneBill.getPhoneCalls()) {
            if (call.getStartTime().after(startDateTime) && call.getEndTime().before(endDateTime)) {
                billToReturn.addPhoneCall(call);
            }
        }

        return billToReturn;
    }

    @Override
    protected void doUnexpectedFailure(Throwable unhandled) {
        unhandled.printStackTrace(System.err);
        super.doUnexpectedFailure(unhandled);
    }
}
