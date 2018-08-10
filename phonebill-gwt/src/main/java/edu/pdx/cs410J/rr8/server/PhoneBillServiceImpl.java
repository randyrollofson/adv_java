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
    public PhoneBill createPhoneBill(String customerName) {
        PhoneBill phoneBill = new PhoneBill(customerName);
        bills.add(phoneBill);
        return phoneBill;
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
    protected void doUnexpectedFailure(Throwable unhandled) {
        unhandled.printStackTrace(System.err);
        super.doUnexpectedFailure(unhandled);
    }

}
