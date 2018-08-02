package edu.pdx.cs410J.rr8;

import org.junit.Ignore;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import static edu.pdx.cs410J.rr8.PhoneBillServlet.CUSTOMER_PARAMETER;
import static edu.pdx.cs410J.rr8.PhoneBillServlet.START_TIME_PARAMETER;
import static edu.pdx.cs410J.rr8.PhoneBillServlet.END_TIME_PARAMETER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link PhoneBillServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
public class PhoneBillServletTest {

  @Test
  public void initiallyServletContainsNoPhoneBills() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    HttpServletRequest mockRequest = mock(HttpServletRequest.class);
    HttpServletResponse mockResponse = mock(HttpServletResponse.class);
    PrintWriter mockPrintWriter = mock(PrintWriter.class);

    when(mockResponse.getWriter()).thenReturn(mockPrintWriter);

    when(mockRequest.getParameter(CUSTOMER_PARAMETER)).thenReturn("Customer");

    servlet.doGet(mockRequest, mockResponse);

    verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_FOUND);
  }

  @Test
  public void addPhoneBill() throws ServletException, IOException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    String customer = "Customer";
    String caller = "123-456-8901";
    String callee = "234-567-1234";

    long startTime = System.currentTimeMillis();
    long endTime = System.currentTimeMillis() + 100000L;

    HttpServletRequest mockRequest = mock(HttpServletRequest.class);
    when(mockRequest.getParameter("customer")).thenReturn(customer);
    when(mockRequest.getParameter("caller")).thenReturn(caller);
    when(mockRequest.getParameter("callee")).thenReturn(callee);
    when(mockRequest.getParameter("startTime")).thenReturn(String.valueOf(startTime));
    when(mockRequest.getParameter("endTime")).thenReturn(String.valueOf(endTime));

    HttpServletResponse mockResponse = mock(HttpServletResponse.class);
    PrintWriter mockPrintWriter = mock(PrintWriter.class);

    when(mockResponse.getWriter()).thenReturn(mockPrintWriter);

    servlet.doPost(mockRequest, mockResponse);

    verify(mockResponse).setStatus(HttpServletResponse.SC_OK);

    PhoneBill bill = servlet.getPhoneBill(customer);
    assertThat(bill, not(nullValue()));
    assertThat(bill.getCustomer(), equalTo(customer));

    Collection<PhoneCall> calls = bill.getPhoneCalls();
    assertThat(calls.size(), equalTo(1));

    PhoneCall call = calls.iterator().next();
    assertThat(call.getCaller(), equalTo(caller));
    assertThat(call.getCallee(), equalTo(callee));
    assertThat(call.getStartTime(), equalTo(new Date(startTime)));
    assertThat(call.getEndTime(), equalTo(new Date(endTime)));
  }

  @Test
  public void getReturnsPrettyPhoneBill() throws IOException, ServletException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    String customer = "Customer";
    PhoneBill bill = new PhoneBill(customer);
    PhoneCall call = new PhoneCall("123-456-7890", "234-456-6789", new Date(), new Date());
    bill.addPhoneCall(call);
    servlet.addPhoneBill(bill);

    HttpServletRequest mockRequest = mock(HttpServletRequest.class);
    HttpServletResponse mockResponse = mock(HttpServletResponse.class);
    PrintWriter mockPrintWriter = mock(PrintWriter.class);

    when(mockResponse.getWriter()).thenReturn(mockPrintWriter);

    when(mockRequest.getParameter(CUSTOMER_PARAMETER)).thenReturn("Customer");

    servlet.doGet(mockRequest, mockResponse);

    verify(mockResponse).setStatus(HttpServletResponse.SC_OK);
    verify(mockPrintWriter).println(customer);
    verify(mockPrintWriter).println(call.toString());
  }

  @Test
  public void getReturnsPrettyPhoneBillWithinRange() throws IOException, ServletException, ParseException {
    PhoneBillServlet servlet = new PhoneBillServlet();

    String customer = "Customer";
    PhoneBill bill = new PhoneBill(customer);
    String startTime1 = "01/27/2018 8:56 am";
    String endTime1 = "02/27/2018 10:27 am";

    String startTime2 = "02/02/2018 9:00 am";
    String endTime2 = "02/20/2018 10:00 pm";

    String startTime3 = "02/10/2018 10:00 pm";
    String endTime3 = "02/15/2018 6:00 am";

    String startRange = "02/01/2018 6:00 am";
    String endRange = "02/21/2018 10:00 pm";

    DateFormat originalFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a");
    Date start1 = originalFormat.parse(startTime1);
    Date end1 = originalFormat.parse(endTime1);

    Date start2 = originalFormat.parse(startTime2);
    Date end2 = originalFormat.parse(endTime2);

    Date start3 = originalFormat.parse(startTime3);
    Date end3 = originalFormat.parse(endTime3);


    PhoneCall call1 = new PhoneCall("123-456-7890", "234-456-6789", start1, end1);
    PhoneCall call2 = new PhoneCall("123-456-7890", "234-456-6789", start2, end2);
    PhoneCall call3 = new PhoneCall("123-456-7890", "234-456-6789", start3, end3);
    bill.addPhoneCall(call1);
    bill.addPhoneCall(call2);
    bill.addPhoneCall(call3);
    servlet.addPhoneBill(bill);

    HttpServletRequest mockRequest = mock(HttpServletRequest.class);
    HttpServletResponse mockResponse = mock(HttpServletResponse.class);
    PrintWriter mockPrintWriter = mock(PrintWriter.class);

    when(mockResponse.getWriter()).thenReturn(mockPrintWriter);

    when(mockRequest.getParameter(CUSTOMER_PARAMETER)).thenReturn("Customer");
    when(mockRequest.getParameter(START_TIME_PARAMETER)).thenReturn(startRange);
    when(mockRequest.getParameter(END_TIME_PARAMETER)).thenReturn(endRange);

    servlet.doGet(mockRequest, mockResponse);

    verify(mockResponse).setStatus(HttpServletResponse.SC_OK);
    verify(mockPrintWriter).println(customer);
    verify(mockPrintWriter, never()).println(call1.toString());
    verify(mockPrintWriter).println(call2.toString());
    verify(mockPrintWriter).println(call3.toString());
  }
}
