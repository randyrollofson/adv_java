package edu.pdx.cs410J.rr8.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;

import java.text.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A basic GWT class that makes sure that we can send an Phone Bill back from the server
 */
public class PhoneBillGwt implements EntryPoint {
    private final Alerter alerter;
    private final PhoneBillServiceAsync phoneBillService;
    private final Logger logger;

    VerticalPanel panel = new VerticalPanel();

    @VisibleForTesting
    Button addPhoneCallButton;

    @VisibleForTesting
    Button showPhoneBillButton;

    public PhoneBillGwt() {
        this(new Alerter() {
            @Override
            public void alert(String message) {
                Window.alert(message);
            }
        });
    }

    @VisibleForTesting
    PhoneBillGwt(Alerter alerter) {
        this.alerter = alerter;
        this.phoneBillService = GWT.create(PhoneBillService.class);
        this.logger = Logger.getLogger("phoneBill");
        Logger.getLogger("").setLevel(Level.INFO);  // Quiet down the default logging
    }

    private void alertOnException(Throwable throwable) {
        Throwable unwrapped = unwrapUmbrellaException(throwable);
        StringBuilder sb = new StringBuilder();
        sb.append(unwrapped.toString());
        sb.append('\n');

        for (StackTraceElement element : unwrapped.getStackTrace()) {
            sb.append("  at ");
            sb.append(element.toString());
            sb.append('\n');
        }

        this.alerter.alert(sb.toString());
    }

    private Throwable unwrapUmbrellaException(Throwable throwable) {
        if (throwable instanceof UmbrellaException) {
            UmbrellaException umbrella = (UmbrellaException) throwable;
            if (umbrella.getCauses().size() == 1) {
                return unwrapUmbrellaException(umbrella.getCauses().iterator().next());
            }

        }

        return throwable;
    }

    private void addWidgets(VerticalPanel panel) {
        addPhoneCallButton = new Button("Add Phone Call");
        addPhoneCallButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                addPhoneCallButton.setEnabled(false);
                addPhoneCall();

            }
        });

        showPhoneBillButton = new Button("Show Phone Bill");
        showPhoneBillButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                showPhoneBill();
            }
        });

        panel.add(addPhoneCallButton);
        panel.add(showPhoneBillButton);
    }

    private void addPhoneCall() {
        logger.info("Calling addPhoneCall");
        phoneBillService.addPhoneCall(new AsyncCallback<PhoneBill>() {

            @Override
            public void onFailure(Throwable ex) {
                alertOnException(ex);
            }

            @Override
            public void onSuccess(PhoneBill phoneBill) {
                FlexTable flexTable = new FlexTable();
                FlexTable.FlexCellFormatter formatter = flexTable.getFlexCellFormatter();
                flexTable.setCellSpacing(10);

                HorizontalPanel linkPanel = new HorizontalPanel();
                linkPanel.setSpacing(3);
                flexTable.setWidget(0, 0, linkPanel);
                formatter.setColSpan(0, 0, 2);

                Label header = new Label("Please enter phone call information below:");
                flexTable.setWidget(0, 0, header);

                Label customerNameLabel = new Label("Customer Name:");
                flexTable.setWidget(1, 0, customerNameLabel);
                TextBox customerNameBox = new TextBox();
                customerNameBox.getElement().setPropertyString("placeholder", "Customer Name");
                flexTable.setWidget(1, 1, customerNameBox);

                Label callerNumberLabel = new Label("Caller Number:");
                flexTable.setWidget(2, 0, callerNumberLabel);
                TextBox callerNumberBox = new TextBox();
                callerNumberBox.getElement().setPropertyString("placeholder", "xxx-xxx-xxxx");
                flexTable.setWidget(2, 1, callerNumberBox);

                Label calleeNumberLabel = new Label("Callee Number:");
                flexTable.setWidget(3, 0, calleeNumberLabel);
                TextBox calleeNumberBox = new TextBox();
                calleeNumberBox.getElement().setPropertyString("placeholder", "xxx-xxx-xxxx");
                flexTable.setWidget(3, 1, calleeNumberBox);

                Label startDateLabel = new Label("Start Date:");
                flexTable.setWidget(4, 0, startDateLabel);
                TextBox startDateBox = new TextBox();
                startDateBox.getElement().setPropertyString("placeholder", "MM/DD/YYYY");
                flexTable.setWidget(4, 1, startDateBox);

                Label startTimeLabel = new Label("Start Time:");
                flexTable.setWidget(5, 0, startTimeLabel);
                TextBox startTimeBox = new TextBox();
                startTimeBox.getElement().setPropertyString("placeholder", "HH:MM am/pm");
                flexTable.setWidget(5, 1, startTimeBox);

                Label endDateLabel = new Label("End Date:");
                flexTable.setWidget(6, 0, endDateLabel);
                TextBox endDateBox = new TextBox();
                endDateBox.getElement().setPropertyString("placeholder", "MM/DD/YYYY");
                flexTable.setWidget(6, 1, endDateBox);

                Label endTimeLabel = new Label("End Time:");
                flexTable.setWidget(7, 0, endTimeLabel);
                TextBox endTimeBox = new TextBox();
                endTimeBox.getElement().setPropertyString("placeholder", "HH:MM am/pm");
                flexTable.setWidget(7, 1, endTimeBox);

                Button cancel = new Button("Cancel");
                cancel.addClickHandler(new ClickHandler () {
                    @Override
                    public void onClick(ClickEvent clickEvent) {
                        addPhoneCallButton.setEnabled(true);
                        flexTable.removeAllRows();
                    }
                });

                flexTable.setWidget(8, 0, cancel);

                Button submit = new Button("Submit");
                submit.addClickHandler(new ClickHandler () {
                    @Override
                    public void onClick(ClickEvent clickEvent) {
                        addPhoneCallButton.setEnabled(false);
                        String customerName = customerNameBox.getText();
                        String callerNumber = callerNumberBox.getText();
                        String calleeNumber = calleeNumberBox.getText();
                        String startDate = startDateBox.getText();
                        String startTime = startTimeBox.getText();
                        String endDate = endDateBox.getText();
                        String endTime = endTimeBox.getText();

                        if (customerName.isEmpty()) {
                            alerter.alert("Customer Name field is empty");
                            return;
                        } else if (callerNumber.isEmpty()) {
                            alerter.alert("Caller Number field is empty");
                            return;
                        } else if (calleeNumber.isEmpty()) {
                            alerter.alert("Callee Number field is empty");
                            return;
                        } else if (startDate.isEmpty()) {
                            alerter.alert("Start Date field is empty");
                            return;
                        } else if (startTime.isEmpty()) {
                            alerter.alert("Start Time field is empty");
                            return;
                        } else if (endDate.isEmpty()) {
                            alerter.alert("End Date field is empty");
                            return;
                        } else if (endTime.isEmpty()) {
                            alerter.alert("End Time field is empty");
                            return;
                        }

                        String fullStartDateTime = (startDate + ' ' + startTime);
                        String fullEndDateTime = (endDate + ' ' + endTime);

                        if (!isValidatePhoneNumber(callerNumber)) {
                            return;
                        }
                        if (!isValidatePhoneNumber(calleeNumber)) {
                            return;
                        }

                        Date startDateTime = new Date();
                        if (!isValidateDateTime(fullStartDateTime, startDateTime)) {
                            return;
                        }

                        Date endDateTime = new Date();
                        if (!isValidateDateTime(fullEndDateTime, endDateTime)) {
                            return;
                        }

                        if (!isValidateStartEndTimes(fullStartDateTime, fullEndDateTime)) {
                            return;
                        }

                        addPhoneCallButton.setEnabled(true);
                        flexTable.removeAllRows();
//                        PhoneCall call = new PhoneCall();
//                        PhoneCall call = new PhoneCall(callerNumber, calleeNumber, startDateTime, endDateTime);
                        alerter.alert(customerName);
                    }
                });

                flexTable.setWidget(8, 1, submit);

                panel.add(flexTable);
            }
        });
    }

    private void showPhoneBill() {
        logger.info("Calling getPhoneBill");
        phoneBillService.getPhoneBill(new AsyncCallback<PhoneBill>() {

            @Override
            public void onFailure(Throwable ex) {
                alertOnException(ex);
            }

            @Override
            public void onSuccess(PhoneBill phoneBill) {
                StringBuilder sb = new StringBuilder(phoneBill.toString());
                Collection<PhoneCall> calls = phoneBill.getPhoneCalls();
                for (PhoneCall call : calls) {
                    sb.append(call);
                    sb.append("\n");
                }
                alerter.alert(sb.toString());
            }
        });
    }

    @Override
    public void onModuleLoad() {
        setUpUncaughtExceptionHandler();

        // The UncaughtExceptionHandler won't catch exceptions during module load
        // So, you have to set up the UI after module load...
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                setupUI();
            }
        });
    }


    private void setupUI() {
        RootPanel rootPanel = RootPanel.get();
//        VerticalPanel panel = new VerticalPanel();
        panel.setSpacing(10);
        rootPanel.add(panel);

        addWidgets(panel);
    }

    private void setUpUncaughtExceptionHandler() {
        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
            @Override
            public void onUncaughtException(Throwable throwable) {
                alertOnException(throwable);
            }
        });
    }

    @VisibleForTesting
    interface Alerter {
        void alert(String message);
    }

    /**
     * Returns whether or not a phone number has the correct format
     * @param phoneNumber
     *        The phone number to be validated
     * @return True/false based on validation
     */
    private boolean isValidatePhoneNumber(String phoneNumber) {
        //Check for standard format
        if (phoneNumber.length() != 12 || phoneNumber.charAt(3) != '-' || phoneNumber.charAt(7) != '-') {
            alerter.alert("Incorrect phone number format. Must be xxx-xxx-xxxx");
            return false;
        }
        //Check for non-numeric characters
        String strippedPhoneNumber = phoneNumber.replaceAll("-", "");
        if (!strippedPhoneNumber.matches("^[0-9]+$")) {
            alerter.alert("Phone number contains characters that are non-numeric");
            return false;
        }
        return true;
    }

    /**
     * Checks if start is before/after end time
     * @param fullStartDateTime
     *        date/time string
     * @param fullEndDateTime
     *        date/time string
     */
    private boolean isValidateStartEndTimes(String fullStartDateTime, String fullEndDateTime) {
        try {
            DateTimeFormat formatter = DateTimeFormat.getFormat("MM/dd/yyyy h:mm a");
            Date start = formatter.parse(fullStartDateTime);
            Date end = formatter.parse(fullEndDateTime);

            if (start.after(end)) {
                alerter.alert("Error: Start date/time is after End date/time");
                return false;
            }
        } catch (Exception e){
            alerter.alert("Date/time parsing error");
            return false;
        }
        return true;
    }

    /**
     * Validates a date string and converts it to a date object
     * @param dateTime
     *        full date/time string
     * @return
     *        returns formatted date object
     */
    public boolean isValidateDateTime(String dateTimeString, Date dateTime) {
        try {
            DateTimeFormat formatter1 = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm a");
            DateTimeFormat formatter2 = DateTimeFormat.getFormat("MM/dd/yyyy h:mm a");

            Date date1 = formatter1.parse(dateTimeString);
            Date date2 = formatter2.parse(dateTimeString);

            if (formatter1.format(date1).equals(dateTimeString)) {

                dateTime = date1;
            } else if (formatter2.format(date2).equals(dateTimeString)) {

                dateTime = date2;
            } else {
                alerter.alert("Incorrect date/time format");
                return false;
            }
        } catch (Exception e){
            alerter.alert("Date parsing error");
            return false;
        }

        return true;
    }

}
