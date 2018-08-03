package edu.pdx.cs410J.rr8;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;

import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class PhoneBillRestClient extends HttpRequestHelper
{
    private static final String WEB_APP = "phonebill";
    private static final String SERVLET = "calls";

    private final String url;


    /**
     * Creates a client to the Phone Bill REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public PhoneBillRestClient( String hostName, int port )
    {
        this.url = String.format( "http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET );
    }

    /**
     * Returns all phone calls from the server for a given customer
     * @param customerName
     */
    public String getPrettyPhoneBill(String customerName) throws IOException {
        Response response = get(this.url, "customer", customerName);

        throwExceptionIfNotOkayHttpStatus(response);

        return response.getContent();
    }

    /**
     * Returns all phone calls within a given range
     * @param customerName
     *        customer name of the phone bill
     * @param startTime
     *        start time range
     * @param endTime
     *        end time range
     * @return all phone calls that occur within the given range
     * @throws IOException
     *         thrown if there is a problem connecting to server
     */
    public String getCallsWithinRange(String customerName, String startTime, String endTime) throws IOException {
        Response response = get(this.url, "customer", customerName, "startTime", startTime, "endTime", endTime);

        throwExceptionIfNotOkayHttpStatus(response);

        return response.getContent();
    }

    /**
     * Adds a phone call to a phone bill
     * @param customerName
     *        customer name on the phone bill
     * @param call
     *        call to add
     * @throws IOException
     *         thrown if there is a problem connecting to server
     */
    public void addPhoneCall(String customerName, PhoneCall call) throws IOException {
        String [] postParameters = {
            "customer", customerName,
            "caller", call.getCaller(),
            "callee", call.getCallee(),
            "startTime", String.valueOf(call.getStartTime().getTime()),
            "endTime", String.valueOf(call.getEndTime().getTime()),

        };
        Response response = postToMyURL(postParameters);
        throwExceptionIfNotOkayHttpStatus(response);
    }

    /**
     * Calls post http method
     * @param postParameters
     *        phone call data
     * @return http resonse form server
     * @throws IOException
     *         thrown if there is a problem connecting to server
     */
    @VisibleForTesting
    Response postToMyURL(String... postParameters) throws IOException {
      return post(this.url, postParameters);
    }

    /**
     * Removes all phone bills
     * @throws IOException
     *         thrown if there is a problem connecting to server
     */
    public void removeAllPhoneBills() throws IOException {
      Response response = delete(this.url);
      throwExceptionIfNotOkayHttpStatus(response);
    }

    /**
     * Checks the http response status
     * @param response
     *        http response from server
     * @return http status code
     */
    private Response throwExceptionIfNotOkayHttpStatus(Response response) {
        int code = response.getCode();
        if (code == HTTP_NOT_FOUND) {
            //String customer = response.getContent();
            //throw new NoSuchPhoneBillException(customer);
            System.err.println("No such customer");
            System.exit(1);

        } else if (code != HTTP_OK) {
            //throw new PhoneBillRestException(code);
            System.err.println("Error: HTTP status " + code);
            System.exit(1);
        }
        return response;
    }

//    private class PhoneBillRestException extends RuntimeException {
//      public PhoneBillRestException(int httpStatusCode) {
//        super("Got an HTTP Status Code of " + httpStatusCode);
//      }
//    }
}
