package com.skillhunternaim.anotherloser.alonechat;

public class ModelRoutinClassListItem {
    private String time;
    private String sun;
    private String mon;
    private String tue;
    private String wed;
    private String thu;


    public ModelRoutinClassListItem() {
    }

    public ModelRoutinClassListItem(String time,String sun, String mon, String tue, String wed, String thu) {
        this.time=time;
        this.sun = sun;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;

    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSun() {
        return sun;
    }

    public void setSun(String sun) {
        this.sun = sun;
    }

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public String getTue() {
        return tue;
    }

    public void setTue(String tue) {
        this.tue = tue;
    }

    public String getWed() {
        return wed;
    }

    public void setWed(String wed) {
        this.wed = wed;
    }

    public String getThu() {
        return thu;
    }

    public void setThu(String thu) {
        this.thu = thu;
    }

}
