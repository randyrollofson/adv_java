package edu.pdx.cs410J.rr8;

import java.util.ArrayList;
import java.util.List;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {
    static final String README = "\nREADME\n\nRandy Rollofson\nProject: phonebill\n\nThis program manages phone calls which " +
            "consist of a customer name, caller phone number\n" +
            "callee phone number, start time of a call, end time of a call, and the date that the call was made.\n" +
            "Phone calls are added to a phone bill belonging to a specific customer.";

    /**
     * Main method that reads in command line args
     * @param args
     *        Array of command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 0 && args[0].equals("-README")) {
            displayReadme();
        }
        boolean foundOptions = false;
        List<String> options = new ArrayList<>();
        List<String> parsedArgs = new ArrayList<>();

        if (parseOptions(args, options, parsedArgs)) {
            foundOptions = true;
        }
        validateArgs(parsedArgs);
        String customer = parsedArgs.get(0);
        String callerNumber = parsedArgs.get(1);
        String calleeNumber = parsedArgs.get(2);
        String startDate = parsedArgs.get(3);
        String startTime = parsedArgs.get(4);
        String endDate = parsedArgs.get(5);
        String endTime = parsedArgs.get(6);

        PhoneCall call = new PhoneCall(callerNumber, calleeNumber, startTime, endTime, startDate, endDate);

        if (!call.isValidPhoneNumber(callerNumber)) {
            System.exit(1);
        }
        if (!call.isValidPhoneNumber(calleeNumber)) {
            System.exit(1);
        }
        if (!call.isValidDate(startDate)) {
            System.exit(1);
        }
        if (!call.isValidTime(startTime)) {
            System.exit(1);
        }
        if (!call.isValidDate(endDate)) {
            System.exit(1);
        }
        if (!call.isValidTime(endTime)) {
            System.exit(1);
        }
        if (foundOptions) {
            implementOptions(options, call);
        }

        PhoneBill bill = new PhoneBill(customer);
        bill.addPhoneCall(call);

        System.exit(0);
    }

    /**
     * Parses the command line and returns true/false whether or not any options (beginning with '-') are found
     * @param args
     *        Array of all command line arguments
     * @param options
     *        Command line arguments that begin with '-'
     * @param parsedArgs
     *        Command line arguments that do not begin with '-'
     * @return True/false based on whether or not any options are found
     */
    private static boolean parseOptions(String[] args, List<String> options, List<String> parsedArgs) {
        for (String arg : args) {
            if (arg.charAt(0) == '-') {
                options.add(arg);
            } else {
                parsedArgs.add(arg);
            }
        }
        if (options.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Checks whether the parsed command line arguments (non-options) are of the correct format
     * @param parsedArgs
     *        Non-option command line arguments
     */
    private static void validateArgs(List<String> parsedArgs) {
        if (parsedArgs.size() == 0) {
            System.err.println("Missing all command line arguments");
            System.exit(1);
        }
        if (parsedArgs.size() > 1 && parsedArgs.size() < 7) {
            System.err.println("Missing one or more command line arguments");
            System.exit(1);
        }
        if (parsedArgs.size() > 7) {
            System.err.println("Too many command line arguments");
            System.exit(1);
        }
    }

    /**
     * Implements command line options
     * @param options
     *        List of command line options to be implemented
     * @param call
     *        PhoneCall object
     */
    private static void implementOptions(List<String> options, PhoneCall call) {
        for (String option : options) {
            if (option.equals("-print")) {
                System.out.println(call.toString());
            } else if (option.equals("-README")) {
                displayReadme();
            }
        }
    }

    /**
     * Displays README
     */
    private static void displayReadme() {
        System.out.println(README);
        System.exit(0);
    }
}