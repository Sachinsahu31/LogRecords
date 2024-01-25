package com.example.logrecords;

public class LogData {

    private String contact;
    private String detail;
    private String date;

    public LogData(String contact, String detail, String date) {
        this.contact = contact;
        this.detail = detail;
        this.date = date;
    }

    // Getter methods

    public String getContact() {
        return contact;
    }

    public String getDetail() {
        return detail;
    }

    public String getDate() {
        return date;
    }
}
