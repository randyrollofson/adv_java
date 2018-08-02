package edu.pdx.cs410J.rr8;

import java.io.IOException;
import java.net.ConnectException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The main class that parses the command line and communicates with the
 * Phone Bill server using REST.
 */
public class Project4 {
    static final String README = "\nREADME\n\nRandy Rollofson\nProject: phonebill\n\nThis program manages phone calls which " +
            "consist of a customer name, caller phone number\n" +
            "callee phone number, start time of a call, end time of a call, and the date that the call was made.\n" +
            "Phone calls are added to a phone bill that is read in from a text file.\n\n" +
            "If the bill is not found at the specified file path, a new bill will be created and added to.\n" +
            "If the file exists, the phone call information provided via the command line\n" +
            "will be appended to the existing phone bill. Phone bills can be written to a file in a pretty format with the -pretty option.";


    public static final String MISSING_ARGS = "Missing command line arguments";
    public static String host = null;
    public static int port = 0;
    public static boolean searchOn = false;


    public static void main(String[] args) throws IOException {
        if (args.length != 0 && args[0].equals("-README")) {
            displayReadme();
        }

        if (isSearchOption(args)) {
            searchOn = true;
        }
        boolean foundOptions = false;
        List<String> options = new ArrayList<>();
        List<String> parsedArgs = new ArrayList<>();

        validateHostOption(args);
        validatePortOption(args);

        PhoneBillRestClient client = new PhoneBillRestClient(host, port);


        if (host == null || port == 0) {
            System.err.println("Port/Host Error");
            System.exit(1);
        }

        if (parseOptions(args, options, parsedArgs)) {
            foundOptions = true;
        }

        validateArgs(parsedArgs);

        String customer = parsedArgs.get(0);
        if (customer.getClass() != String.class) {
            System.err.println("Customer name is not a string");
            System.exit(1);
        }

        if (parsedArgs.size() == 1) {
            String response = null;
            try {
                // Print all phone calls in a phone bill
                response = client.getPrettyPhoneBill(customer);

            } catch (IOException ex) {
                System.err.println("Status Code: 404\nError while contacting server");
                System.exit(1);
            }

            System.out.println(response);
            System.exit(0);
        }

        String startDate;
        String startTime;
        String startTimeAmPm;
        String endDate;
        String endTime;
        String endTimeAmPm;
        String callerNumber;
        String calleeNumber;
        if (searchOn) {
            startDate = parsedArgs.get(1);
            startTime = parsedArgs.get(2);
            startTimeAmPm = parsedArgs.get(3).toUpperCase();
            endDate = parsedArgs.get(4);
            endTime = parsedArgs.get(5);
            endTimeAmPm = parsedArgs.get(6).toUpperCase();
            String fullStartDateTime = (startDate + ' ' + startTime + ' ' + startTimeAmPm);
            String fullEndDateTime = (endDate + ' ' + endTime + ' ' + endTimeAmPm);

            String response = null;

            try {
                // Print all phone calls within the given range
                response = client.getCallsWithinRange(customer, fullStartDateTime, fullEndDateTime);

            } catch ( IOException ex ) {
                System.err.println("Status Code: 404\nError while contacting server");
                System.exit(1);
            }

            System.out.println(response);
            System.exit(0);
        } else {
            callerNumber = parsedArgs.get(1);
            calleeNumber = parsedArgs.get(2);
            startDate = parsedArgs.get(3);
            startTime = parsedArgs.get(4);
            startTimeAmPm = parsedArgs.get(5).toUpperCase();
            endDate = parsedArgs.get(6);
            endTime = parsedArgs.get(7);
            endTimeAmPm = parsedArgs.get(8).toUpperCase();

            String fullStartDateTime = (startDate + ' ' + startTime + ' ' + startTimeAmPm);
            String fullEndDateTime = (endDate + ' ' + endTime + ' ' + endTimeAmPm);

            Date start = validateAndReturnDateTime(fullStartDateTime);
            Date end = validateAndReturnDateTime(fullEndDateTime);

            PhoneCall call = new PhoneCall(callerNumber, calleeNumber, start, end);

            call.validatePhoneNumber(callerNumber);
            call.validatePhoneNumber(calleeNumber);
            call.validateStartEndTimes(fullStartDateTime, fullEndDateTime);

            if (foundOptions) {
                implementOptions(options, call);
            }

            client.addPhoneCall(customer, call);

            String response;

            try {
                // Print all phone calls in a phone bill
                response = client.getPrettyPhoneBill(customer);

            } catch (IOException ex) {
                System.err.println("Status Code: 404\nError while contacting server");
                return;
            }

            System.out.println(response);
        }

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
        for (int i = 0; i < args.length; i++) {
            if (args[i].charAt(0) == '-' && args[i].length() > 1) {
                if (!args[i].equals("-print") && !args[i].equals("-README") && !args[i].equals("-host") && !args[i].equals("-port") && !args[i].equals("-search")) {
                    System.err.println("Error: Incorrect command line option");
                    System.exit(1);
                } else if (!args[i].equals("-host") && !args[i].equals("-port") && (args[i].equals("-print") || args[i].equals("-README"))) {
                    options.add(args[i]);
                } else if (args[i].equals("-host")) {
                    i++;
                } else if (args[i].equals("-port")) {
                    i++;
                }
            } else {
                parsedArgs.add(args[i]);
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
        if (searchOn) {
            if (parsedArgs.size() < 7) {
                System.err.println("Missing one or more command line arguments");
                System.exit(1);
            }
            if (parsedArgs.size() > 7) {
                System.err.println("Too many command line arguments");
                System.exit(1);
            }
        } else {
            if (parsedArgs.size() > 1 && parsedArgs.size() < 9) {
                System.err.println("Missing one or more command line arguments");
                System.exit(1);
            }
            if (parsedArgs.size() > 9) {
                System.err.println("Too many command line arguments");
                System.exit(1);
            }
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

    public static Date validateAndReturnDateTime(String dateTime) {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            SimpleDateFormat formatter2 = new SimpleDateFormat("MM/dd/yyyy h:mm a");

            Date date1 = formatter1.parse(dateTime);
            Date date2 = formatter2.parse(dateTime);

            if (formatter1.format(date1).equals(dateTime)) {

                return date1;
            } else if (formatter2.format(date2).equals(dateTime)) {

                return date2;
            } else {
                System.err.println("Incorrect date/time format");
                System.exit(1);
            }
        } catch (ParseException e){
            System.err.println("Date parsing error");
            System.exit(1);
        }

        return null;
    }

    private static void validateHostOption(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-host")) {
                if ((args[i + 1]).equals("localhost")) {
                    host = "127.0.0.1";
                    return;
                } else {
                    host = args[i + 1];
                    return;
                }
            }
        }
    }

    private static void validatePortOption(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-port")) {
                try {
                    port = Integer.parseInt(args[i + 1]);

                } catch (NumberFormatException ex) {
                    System.err.println("Error: Port " + args[i + 1] + " must be an integer");
                    System.exit(1);
                }
                return;
            }
        }
    }

    private static boolean isSearchOption(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-search")) {
                return true;
            }
        }
        return false;
    }
}