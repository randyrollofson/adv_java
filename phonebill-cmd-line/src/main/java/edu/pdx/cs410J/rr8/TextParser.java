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
    private String filePath;

    /**
     * Creates a new <code>TextParser</code>
     * @param fileName
     *        Name of the text file
     * @param filePath
     *        Relative path of the text file (not including the file name)
     */
    TextParser(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
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
            File file = new File(filePath + fileName);
            BufferedReader bufferedReader;

            try {
                bufferedReader = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e) {
                System.out.println("File not found. Creating new file at path: " + filePath + fileName);
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
                    PhoneCall call = new PhoneCall(args[0], args[1], args[2], args[3], args[4], args[5]);
                    if (!call.isValidPhoneNumber(args[0]) || !call.isValidPhoneNumber(args[1]) || !call.isValidDate(args[2]) || !call.isValidTime(args[3]) || !call.isValidDate(args[4]) || !call.isValidTime(args[5])) {
                        System.err.println("Error: Text file is malformatted");
                        System.exit(1);
                    }
                    phoneBill.addPhoneCall(call);
                }
            }
        } catch (IOException e) {
            throw new ParserException("Error: Problem with reading from text file");
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
