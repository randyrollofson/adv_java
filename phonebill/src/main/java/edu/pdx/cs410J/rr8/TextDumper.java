package edu.pdx.cs410J.rr8;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Collection;

import java.io.IOException;

public class TextDumper implements PhoneBillDumper {
    private String filePath;
    private String fileName;

    TextDumper(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    @Override
    public void dump(AbstractPhoneBill bill) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath + fileName);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
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
