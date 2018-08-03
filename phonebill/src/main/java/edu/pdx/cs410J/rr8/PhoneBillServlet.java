package edu.pdx.cs410J.rr8;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>PhoneBill</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class PhoneBillServlet extends HttpServlet
{
    static final String CUSTOMER_PARAMETER = "customer";
    private static final String CALLER_PARAMETER = "caller";
    private static final String CALLEE_PARAMETER = "callee";
    static final String START_TIME_PARAMETER = "startTime";
    static final String END_TIME_PARAMETER = "endTime";

    private final Map<String, String> dictionary = new HashMap<>();
    private Map<String, PhoneBill> bills = new HashMap<>();

    /**
     * Handles an HTTP GET request from a client by writing the definition of the
     * word specified in the "word" HTTP parameter to the HTTP response.  If the
     * "word" parameter is not specified, all of the entries in the dictionary
     * are written to the HTTP response.
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String customer = getParameter(CUSTOMER_PARAMETER, request );

        String startTime = getParameter(START_TIME_PARAMETER, request);
        String endTime = getParameter(END_TIME_PARAMETER, request);

        if (startTime != null && endTime != null) {
            try {
                writePrettyPhoneBillWithinRange(customer, startTime, endTime, response);
            } catch (ParseException e) {
                //TODO handle error
                response.setStatus(404);
                System.err.println("Phone Bill parsing error");
                System.exit(1);
            }
        } else {
            writePrettyPhoneBill(customer, response);
        }
    }

    /**
     * Writes a phone bill w/ all calls
     * @param customer
     *        customer name on phone bill
     * @param response
     *        http response from server
     * @throws IOException
     *         thrown if there is a problem connecting to the server
     */
    private void writePrettyPhoneBill(String customer, HttpServletResponse response) throws IOException {
        PhoneBill bill = getPhoneBill(customer);
        if (bill == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        } else {
            PrintWriter writer = response.getWriter();
            PrettyPrinter pretty = new PrettyPrinter(writer);
            pretty.dump(bill);
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    /**
     * Writes all calls from a phone bill that occur within a given range
     * @param customer
     *        customer name on the phone bill
     * @param startTime
     *         start time range
     * @param endTime
     *         end time range
     * @param response
     *        http response form server
     * @throws IOException
     *         thrown if there is a problem connecting to the server
     * @throws ParseException
     *         thrown if there is a date parsing error
     */
    private void writePrettyPhoneBillWithinRange(String customer, String startTime, String endTime, HttpServletResponse response) throws IOException, ParseException {
        PhoneBill bill = getPhoneBill(customer);
        if (bill == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            DateFormat originalFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a");
            Date start1 = originalFormat.parse(startTime);
            Date end1 = originalFormat.parse(endTime);
            Collection<PhoneCall> calls = bill.getPhoneCalls();
            PhoneBill editedBill = new PhoneBill(customer);
            for (PhoneCall call : calls) {
                if (call.getStartTime().after(start1) && call.getEndTime().before(end1)) {
                    editedBill.addPhoneCall(call);
                }
            }
            if (editedBill.getPhoneCalls().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_OK);
                System.out.println("Query returned empty phone bill");

                return;

            }
            PrintWriter writer = response.getWriter();
            PrettyPrinter pretty = new PrettyPrinter(writer);
            pretty.dump(editedBill);
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    /**
     * Handles an HTTP POST request by storing the dictionary entry for the
     * "word" and "definition" request parameters.  It writes the dictionary
     * entry to the HTTP response.
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String customer = getParameter(CUSTOMER_PARAMETER, request );
        if (customer == null) {
            missingRequiredParameter(response, CUSTOMER_PARAMETER);
            return;
        }

        PhoneBill bill = getPhoneBill(customer);
        if (bill == null) {
            bill = new PhoneBill(customer);
            addPhoneBill(bill);
        }

        String caller = getParameter(CALLER_PARAMETER, request);
        String callee = getParameter(CALLEE_PARAMETER, request);
        String startTime = getParameter(START_TIME_PARAMETER, request);
        String endTime = getParameter(END_TIME_PARAMETER, request);

        Date startDate = new Date(Long.parseLong(startTime));
        Date endDate = new Date(Long.parseLong(endTime));

        PhoneCall call = new PhoneCall(caller, callee, startDate, endDate);
        bill.addPhoneCall(call);

        response.setStatus( HttpServletResponse.SC_OK);
    }

    /**
     * Handles an HTTP DELETE request by removing all dictionary entries.  This
     * behavior is exposed for testing purposes only.  It's probably not
     * something that you'd want a real application to expose.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        this.dictionary.clear();

        PrintWriter pw = response.getWriter();
        pw.println(Messages.allDictionaryEntriesDeleted());
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);

    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
        throws IOException
    {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     *         <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
      String value = request.getParameter(name);
      if (value == null || "".equals(value)) {
        return null;

      } else {
        return value;
      }
    }

    /**
     * Gets a phone bill
     * @param customer
     *        customer name on phone bill
     * @return phone bill
     */
    @VisibleForTesting
    PhoneBill getPhoneBill(String customer) {
        return this.bills.get(customer);
    }

    /**
     * Adds a phone call
     * @param bill
     *        phone bill to add call to
     */
    @VisibleForTesting
    void addPhoneBill(PhoneBill bill) {
        this.bills.put(bill.getCustomer(), bill);
    }

}
