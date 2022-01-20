package com.example.a02_eeapp;

public class UserStatistik {
    private String richtig;
    private String falsch;
    private String richtig_d;
    private String falsch_d;
    private String richtig_ds;
    private String falsch_ds;
    private String userID;
    private String classID;

    public UserStatistik() { }

    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRichtig_d() {
        return richtig_d;
    }
    public String getRichtig_ds() {
        return richtig_ds;
    }

    public void setRichtig_d(String richtig_d) {
        this.richtig_d = richtig_d;
    }
    public void setRichtig_ds(String richtig_ds) {
        this.richtig_ds = richtig_ds;
    }
    public String getFalsch_d() {
        return falsch_d;
    }
    public String getFalsch_ds() {
        return falsch_ds;
    }
    public void setFalsch_d(String falsch_d) {
        this.falsch_d = falsch_d;
    }
    public void setFalsch_ds(String falsch_ds) {
        this.falsch_ds = falsch_ds;
    }

    public String getRichtig() {
        return richtig;
    }
    public void setRichtig(String richtig) {
        this.richtig = richtig;
    }

    public String getFalsch() {
        return falsch;
    }
    public void setFalsch(String  falsch) {
        this.falsch = falsch;
    }

    public String  getClassID() {
        return classID;
    }
    public void setClassID(String  classID) {
        this.classID = classID;
    }
}