package edu.pdx.cs410J.rr8;

import edu.pdx.cs410J.ParserException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project3 {
    static final String README = "\nREADME\n\nRandy Rollofson\nProject: phonebill\n\nThis program manages phone calls which " +
            "consist of a customer name, caller phone number\n" +
            "callee phone number, start time of a call, end time of a call, and the date that the call was made.\n" +
            "Phone calls are added to a phone bill that is read in from a text file.\n\n" +
            "If the bill is not found at the specified file path, a new bill will be created and added to.\n" +
            "If the file exists, the phone call information provided via the command line\n" +
            "will be appended to the existing phone bill.";

    static private String FILE_NAME = null;
    static private String PRETTY_FILE_NAME = null;

    /**
     * Main method that reads in command line args
     * @param args
     *        Array of command line arguments
     * @throws ParserException
     *         The parse method in TextParser throws this exception if there is a problem parsing the text file
     * @throws IOException
     *         The dump method in TextDumper throws this exception if there is a problem with writing to a text file
     */
    public static void main(String[] args) throws ParserException, IOException {
        TextParser textParser = null;
        //TextParser prettyTextParser = null;
        PhoneBill bill = null;
        PhoneBill prettyBill = null;
        if (args.length != 0 && args[0].equals("-README")) {
            displayReadme();
        }
        boolean foundOptions = false;
        List<String> options = new ArrayList<>();
        List<String> parsedArgs = new ArrayList<>();

        if (isTextFileOption(args)) {
            textParser = new TextParser(FILE_NAME);
            bill = textParser.parse();
        }
//        if (isPrettyOption(args)) {
//            prettyTextParser = new TextParser(PRETTY_FILE_NAME);
//            prettyBill = prettyTextParser.parse();
//        }

        if (parseOptions(args, options, parsedArgs)) {
            foundOptions = true;
        }
        validateArgs(parsedArgs);
        String customer = parsedArgs.get(0);
        if (textParser != null) {
            if (bill != null && !customer.equals(bill.getCustomer())) {
                if (bill.getCustomer().isEmpty()) {
                    System.err.println("Error: Customer name not found on phone bill");
                } else {
                    System.err.println("Error: Customer name to add does not match customer name on phone bill");
                }
                System.exit(1);
            }
        }

        String callerNumber = parsedArgs.get(1);
        String calleeNumber = parsedArgs.get(2);
        String startDate = parsedArgs.get(3);
        String startTime = parsedArgs.get(4);
        String startTimeAmPm = parsedArgs.get(5).toUpperCase();
        String endDate = parsedArgs.get(6);
        String endTime = parsedArgs.get(7);
        String endTimeAmPm = parsedArgs.get(8).toUpperCase();

        PhoneCall call = new PhoneCall(callerNumber, calleeNumber, startDate, startTime, startTimeAmPm, endDate, endTime, endTimeAmPm);

        String fullStartDateTime = (startDate + ' ' + startTime + ' ' + startTimeAmPm);
        String fullEndDateTime = (endDate + ' ' + endTime + ' ' + endTimeAmPm);
        call.validatePhoneNumber(callerNumber);
        call.validatePhoneNumber(calleeNumber);
        call.setStartDateTime(call.validateAndReturnStartDateTime(fullStartDateTime));
        call.setEndDateTime(call.validateAndReturnEndDateTime(fullEndDateTime));
        call.validateStartEndTimes(fullStartDateTime, fullEndDateTime);
        call.setCallDuration();

        if (foundOptions) {
            implementOptions(options, call);
        }

        if (bill == null) {
            bill = new PhoneBill(customer);
        }
        bill.addPhoneCall(call);
//        if (prettyBill == null) {
//            prettyBill = new PhoneBill(customer);
//        }
        //prettyBill.addPhoneCall(call);

        if (textParser != null) {
            TextDumper textDumper = new TextDumper(textParser.getFileName());
            if (isTextFileOption(args)) {
                textDumper.dump(bill);
            }
        }
        //if (prettyTextParser != null) {
            //PrettyPrinter prettyPrinter = new PrettyPrinter(PRETTY_FILE_NAME);
            if (isPrettyOption(args)) {
                PrettyPrinter prettyPrinter = new PrettyPrinter(PRETTY_FILE_NAME);
                bill.sortBill();
                prettyPrinter.dump(bill);
            }
        //}

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
                if (!args[i].equals("-print") && !args[i].equals("-README") && !args[i].equals("-textFile") && !args[i].equals("-pretty")) {
                    System.err.println("Error: Incorrect command line option");
                    System.exit(1);
                } else if (!args[i].equals("-textFile") && !args[i].equals("-pretty") && (args[i].equals("-print") || args[i].equals("-README"))) {
                    options.add(args[i]);
                } else if (args[i].equals("-textFile")) {
                    i++;
                } else if (args[i].equals("-pretty")) {
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
        if (parsedArgs.size() > 1 && parsedArgs.size() < 9) {
            System.err.println("Missing one or more command line arguments");
            System.exit(1);
        }
        if (parsedArgs.size() > 9) {
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

    /**
     * Handles setting the file name if the textFile option is in the command line arguments
     * @param args
     *        Array of command line arguments
     * @return True/false whether or not the "-textFile" option is found the command line arguments
     */
    private static boolean isTextFileOption(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-textFile")) {
                if (!args[i + 1].contains(".txt")) {
                    System.err.println("Error: Incorrect text file");
                    System.exit(1);
                } else {
                    FILE_NAME = args[i + 1];
                    if (FILE_NAME.equals(PRETTY_FILE_NAME)) {
                        System.err.println("Error: FILE_NAME can not be the same as PRETTY_FILE_NAME");
                        System.exit(1);
                    }
                }
                return true;
            }
        }

        return false;
    }

    private static boolean isPrettyOption(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-pretty")) {
                if (!args[i + 1].contains(".txt") && !args[i + 1].contains("-")) {
                    System.err.println("Error: Incorrect Pretty Print option");
                    System.exit(1);
                } else {
                    PRETTY_FILE_NAME = args[i + 1];
                    if (PRETTY_FILE_NAME.equals(FILE_NAME)) {
                        System.err.println("Error: PRETTY_FILE_NAME can not be the same as FILE_NAME");
                        System.exit(1);
                    }
                }
                return true;
            }
        }

        return false;
    }
}
