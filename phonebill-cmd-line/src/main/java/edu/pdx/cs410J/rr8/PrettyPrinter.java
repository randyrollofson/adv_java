package edu.pdx.cs410J.rr8;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

public class PrettyPrinter implements PhoneBillDumper {
    private String fileName;

    public PrettyPrinter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void dump(AbstractPhoneBill bill) throws IOException {
        if (fileName.equals("-")) {
            Collection<PhoneCall> calls = bill.getPhoneCalls();
            System.out.println("\nCustomer Name: " + bill.getCustomer());
            System.out.println();
            int i = 1;
            for (PhoneCall call : calls) {
                System.out.println("CALL " + i);
                System.out.println("Caller Number: " + call.getCaller());
                System.out.println("Callee Number: " + call.getCallee());
                System.out.println("Call Start Date/Time: " + call.getStartTimeString());
                System.out.println("Call End Date/Time: " + call.getEndTimeString());
                System.out.println("Call Duration: " + call.getCallDuration() + " minutes");
                System.out.println();
                i++;
            }
        } else if (fileName.contains(".txt")) {
            FileWriter fileWriter = null;
            BufferedWriter bufferedWriter = null;
            try {
                fileWriter = new FileWriter(fileName);
                bufferedWriter = new BufferedWriter(fileWriter);
            } catch (IOException e) {
                System.err.println("Error: Problem when writing to file");
                System.exit(1);
            }
            int i = 1;
            Collection<PhoneCall> calls = bill.getPhoneCalls();
            bufferedWriter.write("Customer Name: " + bill.getCustomer());
            bufferedWriter.newLine();
            for (PhoneCall call : calls) {
                bufferedWriter.write("\nCALL " + i);
                bufferedWriter.write("\nCaller Number: " + call.getCaller());
                bufferedWriter.write("\nCallee Number: " + call.getCallee());
                bufferedWriter.write("\nCall Start Date/Time: " + call.getStartTimeString());
                bufferedWriter.write("\nCall End Date/Time: " + call.getEndTimeString());
                bufferedWriter.write("\nCall Duration: " + call.getCallDuration() + " minutes");
                bufferedWriter.newLine();
                i++;
            }
            bufferedWriter.close();
            fileWriter.close();
        }
    }

}
