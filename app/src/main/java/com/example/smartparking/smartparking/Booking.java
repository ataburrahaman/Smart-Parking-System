package com.example.smartparking.smartparking;

public class Booking {
    String ldate;
    String ltime;
    String starttime;
    String endtime;
    String uname;
    String otp;

    public Booking(){

    }
    public Booking(String ldate, String ltime, String starttime, String endtime, String uname, String otp) {
        this.ldate = ldate;
        this.ltime = ltime;
        this.starttime = starttime;
        this.endtime = endtime;
        this.uname = uname;
        this.otp = otp;
    }

    public String getLdate() {
        return ldate;
    }

    public void setLdate(String ldate) {
        this.ldate = ldate;
    }

    public String getLtime() {
        return ltime;
    }

    public void setLtime(String ltime) {
        this.ltime = ltime;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
