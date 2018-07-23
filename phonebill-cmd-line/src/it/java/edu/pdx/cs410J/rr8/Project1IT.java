package edu.pdx.cs410J.rr8;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project3} main class.
 */
public class Project1IT extends InvokeMainTestCase {
    /**
     * Invokes the main method of {@link Project3} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project3.class, args );
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    public void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain();
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing all command line arguments"));
    }

    /**
     * Tests that invoking the main method with correct arguments issues no error
     */
    @Test
    public void testCorrectCommandLineArguments() {
        MainMethodResult result = invokeMain("\"Randy Rollofson\"", "971-506-3627", "503-869-8007", "1/15/18", "9:00", "AM",  "1/15/18", "9:15", "PM");
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardError(), not(containsString("Missing all command line arguments")));
        assertThat(result.getTextWrittenToStandardError(), not(containsString("Missing one or more command line arguments")));
        assertThat(result.getTextWrittenToStandardError(), not(containsString("Too many command line arguments")));
    }

    @Test
    public void dashReadmeOptionPrintsOnlyReadme() {
        MainMethodResult result = invokeMain("-README");
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), equalTo(Project3.README + "\n"));
        assertThat(result.getTextWrittenToStandardError(), equalTo(""));
    }

    @Test
    public void dashPrintOptionsPrintsNewlyCreatedPhoneCall() {
        String caller = "123-456-7890";
        String callee = "234-567-8901";
        String startDate = "7/4/18";
        String startTime = "6:24";
        String startTimeAmPm = "AM";
        String endDate = "7/4/18";
        String endTime = "6:48";
        String endTimeAmPm = "PM";

        MainMethodResult result =
                invokeMain("-print", "My Customer", caller, callee, startDate, startTime, startTimeAmPm, endDate, endTime, endTimeAmPm);

        assertThat(result.getExitCode(), equalTo(0));
        String phoneCallToString = String.format("Phone call from %s to %s from %s %s %s to %s %s %s",
                caller, callee, startDate, startTime, startTimeAmPm, endDate, endTime, endTimeAmPm);
        assertThat(result.getTextWrittenToStandardOut(), equalTo(phoneCallToString + "\n"));
    }

    @Test
    public void validCommandLineWithNoDashPrintOptionPrintsNothingToStandardOut() {
        String caller = "123-456-7890";
        String callee = "234-567-8901";
        String startDate = "7/4/18";
        String startTime = "6:24";
        String startTimeAmPm = "AM";
        String endDate = "7/4/18";
        String endTime = "6:48";
        String endTimeAmPm = "PM";

        MainMethodResult result =
                invokeMain("My Customer", caller, callee, startDate, startTime, startTimeAmPm, endDate, endTime, endTimeAmPm);

        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), equalTo(""));
        assertThat(result.getTextWrittenToStandardError(), equalTo(""));
    }
}