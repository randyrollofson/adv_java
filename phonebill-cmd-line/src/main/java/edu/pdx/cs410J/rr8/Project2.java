package edu.pdx.cs410J.rr8;

import edu.pdx.cs410J.ParserException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project2 {
    static final String README = "\nREADME\n\nRandy Rollofson\nProject: phonebill\n\nThis program manages phone calls which " +
            "consist of a customer name, caller phone number\n" +
            "callee phone number, start time of a call, end time of a call, and the date that the call was made.\n" +
            "Phone calls are added to a phone bill that is read in from a text file.\n\n" +
            "If the bill is not found at the specified file path, a new bill will be created and added to.\n" +
            "If the file exists, the phone call information provided via the command line\n" +
            "will be appended to the existing phone bill.";

    static private String FILE_NAME = null;

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
        //String filePath = "src/main/java/edu/pdx/cs410J/rr8";
        TextParser textParser = null;
        PhoneBill bill = null;
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
        String endDate = parsedArgs.get(5);
        String endTime = parsedArgs.get(6);

        PhoneCall call = new PhoneCall(callerNumber, calleeNumber, startDate, startTime, endDate, endTime);

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

        if (bill == null) {
            bill = new PhoneBill(customer);
        }
            bill.addPhoneCall(call);
        if (textParser != null) {
            TextDumper textDumper = new TextDumper(textParser.getFileName());
            textDumper.dump(bill);
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
            if (args[i].charAt(0) == '-') {
                if (!args[i].equals("-print") && !args[i].equals("-README") && !args[i].equals("-textFile")) {
                    System.err.println("Error: Incorrect command line option");
                    System.exit(1);
                } else if (!args[i].equals("-textFile") && (args[i].equals("-print") || args[i].equals("-README"))) {
                    options.add(args[i]);
                } else if (args[i].equals("-textFile")) {
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
                }
                return true;
            }
        }

        return false;
    }
}