package edu.pdx.cs410J.rr8;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link PhoneCall} class.
 */
public class PhoneCallTest {

//    @Test(expected = UnsupportedOperationException.class)
//    public void getStartTimeStringNeedsToBeImplemented() {
//        PhoneCall call = new PhoneCall();
//        call.getStartTimeString();
//    }
//
//    @Test
//    public void initiallyAllPhoneCallsHaveTheSameCallee() {
//        PhoneCall call = new PhoneCall();
//        assertThat(call.getCallee(), containsString("not implemented"));
//    }
//
//    @Test
//    public void forProject1ItIsOkayIfGetStartTimeReturnsNull() {
//        PhoneCall call = new PhoneCall();
//        assertThat(call.getStartTime(), is(nullValue()));
//    }

    @Test
    public void testGetCaller() {
        PhoneCall call = new PhoneCall("971-506-3627", "503-869-8007", "11/1/2018", "1:59", "11/15/2018", "9:15");
        assertThat(call.getCaller(), containsString("971-506-3627"));
    }

    @Test
    public void testGetCallee() {
        PhoneCall call = new PhoneCall("971-506-3627", "503-869-8007", "11/1/2018", "1:59", "11/15/2018", "9:15");
        assertThat(call.getCallee(), containsString("503-869-8007"));
    }

    @Test
    public void testGetStartTimeString() {
        PhoneCall call = new PhoneCall("971-506-3627", "503-869-8007", "11/1/2018", "1:59", "11/15/2018", "9:15");
        assertThat(call.getStartTimeString(), containsString("11/1/2018 1:59"));
    }

    @Test
    public void testGetEndTimeString() {
        PhoneCall call = new PhoneCall("971-506-3627", "503-869-8007", "11/1/2018", "1:59", "11/15/2018", "9:15");
        assertThat(call.getEndTimeString(), containsString("1/15/2018 9:15"));
    }
  
}
