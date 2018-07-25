package edu.pdx.cs410J.rr8;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;
import java.io.*;

/**
 * Class that manages reading phone bill information from a text file
 */
public class TextParser implements PhoneBillParser<PhoneBill> {
    private String fileName;

    /**
     * Creates a new <code>TextParser</code>
     * @param fileName
     *        Name of the text file
     */
    TextParser(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Reads in phone bill information from text file and creates new PhoneBill
     * @return Returns a new PhoneBill
     * @throws ParserException
     *         Thrown if there is a problem parsing the text file
     */
    @Override
    public PhoneBill parse() throws ParserException {
        PhoneBill phoneBill = null;
        List<String> lines = new ArrayList<>();

        try {
            File file = new File(fileName);
            BufferedReader bufferedReader;

            try {
                bufferedReader = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e) {
                //System.out.println("File not found");
                return null;
            }
            String readLine;

            while ((readLine = bufferedReader.readLine()) != null) {
                lines.add(readLine);
            }
            if (!lines.isEmpty()) {
                phoneBill = new PhoneBill(lines.get(0));
                for (int i = 1; i < lines.size(); i++) {
                    String[] args = lines.get(i).split(" ");
                    if (args.length != 8) {
                        System.err.println("Error: Malformatted Phone Bill");
                        System.exit(1);
                    }
                    PhoneCall call = new PhoneCall(args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7]);
                    call.validatePhoneNumber(args[0]);
                    call.validatePhoneNumber(args[1]);
                    call.setStartDateTime(call.validateAndReturnStartDateTime(args[2] + ' ' + args[3] + ' ' + args[4]));
                    call.setEndDateTime(call.validateAndReturnEndDateTime(args[5] + ' ' + args[6] + ' ' + args[7]));
                    call.setCallDuration();

                    phoneBill.addPhoneCall(call);
                }
            }
        } catch (IOException e) {
            System.err.println("Error: Problem with reading from text file");
            System.exit(1);
        }

        return phoneBill;
    }

    /**
     * Returns name of the text file that is read from
     * @return Name of the text file
     */
    String getFileName() {
        return fileName;
    }
}
