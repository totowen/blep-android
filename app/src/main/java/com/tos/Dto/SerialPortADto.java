package com.tos.Dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 上传串口dto
 *
 * Created by alienware on 2016/8/29.
 */
public class SerialPortADto implements Serializable{

    private static final long serialVersionUID = 1L;

    private String co2;
    private String ch4;
    private String co;
    private String ch2o;
    private String voc;
    private String warn;
    private String state;
    private Date date;


    public String getCo2() {
        return co2;
    }

    public void setCo2(String co2) {
        this.co2 = co2;
    }

    public String getCh4() {
        return ch4;
    }

    public void setCh4(String ch4) {
        this.ch4 = ch4;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getCh2o() {
        return ch2o;
    }

    public void setCh2o(String ch2o) {
        this.ch2o = ch2o;
    }

    public String getVoc() {
        return voc;
    }

    public void setVoc(String voc) {
        this.voc = voc;
    }

    public String getWarn() {
        return warn;
    }

    public void setWarn(String warn) {
        this.warn = warn;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SerialPortADto{" +
                "co2='" + co2 + '\'' +
                ", ch4='" + ch4 + '\'' +
                ", co='" + co + '\'' +
                ", ch2o='" + ch2o + '\'' +
                ", voc='" + voc + '\'' +
                ", warn='" + warn + '\'' +
                ", state='" + state + '\'' +
                ", date=" + date +
                '}';
    }
}
