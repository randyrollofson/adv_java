package edu.pdx.cs410J.rr8;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Arrays;
import java.lang.String;


import java.io.*;

public class TextParser implements PhoneBillParser {
    private String fileName;
    private String filePath;
    public PhoneBill phoneBill;

    public TextParser(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    @Override
    public AbstractPhoneBill parse() throws ParserException {
        //String customerName;
        List<String> lines = new ArrayList<>();

        try {
            File file = new File(filePath + fileName);
            BufferedReader bufferedReader = null;

            try {
                bufferedReader = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e) {
                System.err.println("File not found!");
                System.exit(1);
            }
            String readLine;

            while ((readLine = bufferedReader.readLine()) != null) {
                lines.add(readLine);
            }
            System.out.println("Lines:" + lines);
            phoneBill = new PhoneBill(lines.get(0));

            for (int i = 1; i < lines.size(); i++) {
                String[] args = lines.get(i).split(" ");
                PhoneCall call = new PhoneCall(args[0], args[1], args[2], args[3], args[4], args[5]);
                phoneBill.addPhoneCall(call);
            }

            //System.out.println(phoneBill.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public PhoneBill getPhoneBill() {
        return phoneBill;
    }

    public String getFileName() {
        return fileName;
    }
}
