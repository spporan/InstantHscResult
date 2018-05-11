package com.poran.instanthscresult;

/**
 * Created by poran on 11/29/2017.
 */

public class SendMessageForInter {
    private String examination;
    private String year;
    private String board;
    private String roll;
    private String thanaCode;
    private String message;
    private int hour;
    private int minute;


    public SendMessageForInter(String examination, String year, String board, String roll,String thanaCode){
        this.examination=examination;
        this.year=year;
        this.board=board;
        this.roll=roll;
        this.thanaCode=thanaCode;
    }
    public SendMessageForInter(){

    }

    public void setBoard(String board) {
        this.board = board;
    }
    public void setExamination(String exam){
        this.examination=exam;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }
    public void setThanaCode(String thanaCode){
        this.thanaCode=thanaCode;

    }

    public String sendMessage(){

            return examination+" "+board+" "+roll+" "+year;
    }

    public String sendPMessage(){
        return examination+" "+thanaCode+" "+roll;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getExamination() {
        return examination;
    }

    public String getYear() {
        return year;
    }

    public String getBoard() {
        return board;
    }

    public String getRoll() {
        return roll;
    }

    public String getThanaCode() {
        return thanaCode;
    }
}
