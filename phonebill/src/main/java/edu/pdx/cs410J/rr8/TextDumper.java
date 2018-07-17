package edu.pdx.cs410J.rr8;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Collection;
import java.io.IOException;

/**
 * Class that manages writing phone bill information to a text file
 */
public class TextDumper implements PhoneBillDumper {
    private String filePath;
    private String fileName;

    /**
     * Creates new <code>TextDumper</code>
     * @param filePath
     *        The relative path of the text file (not including the file name)
     * @param fileName
     *        The name of the text file
     */
    TextDumper(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    /**
     * Writes a phone bill to a text file
     * @param bill
     *        PhoneBill object to be written to text file
     * @throws IOException
     *         Thrown if there is a problem with writing the file
     */
    @Override
    public void dump(AbstractPhoneBill bill) throws IOException {
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;
        try {
            fileWriter = new FileWriter(filePath + fileName);
            bufferedWriter = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            throw new IOException("Error with writing file");
        }
        Collection<PhoneCall> calls = bill.getPhoneCalls();
        bufferedWriter.write(bill.getCustomer());
        bufferedWriter.newLine();
        for (PhoneCall call : calls) {
            bufferedWriter.write(call.getCaller() + ' ');
            bufferedWriter.write(call.getCallee() + ' ');
            bufferedWriter.write(call.getStartTimeString() + ' ');
            bufferedWriter.write(call.getEndTimeString());
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
        fileWriter.close();
    }
}
