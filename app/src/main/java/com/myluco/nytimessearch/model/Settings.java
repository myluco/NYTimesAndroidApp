package com.myluco.nytimessearch.model;

import android.content.res.Resources;

import com.myluco.nytimessearch.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by lcc on 2/14/16.
 */
public class Settings implements Serializable, Cloneable{

    private int year=0;
    private int month=0;
    private int day=0;
    private boolean oldest = true;
    private boolean[] newsDesk = {false, false, false};

    public Settings() {
         // today


    }
    public boolean artSet()  {
        return newsDesk[0];
    }
    public boolean fashionSet()  {
        return newsDesk[1];
    }
    public boolean sportSet()  {
        return newsDesk[2];
    }
    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }



    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }


    public boolean isOldest() {
        return oldest;
    }

    public boolean[] getNewsDesk() {
        return newsDesk;
    }


    public void setArts(boolean v) {
        newsDesk[0]= v;
    }
    public void setFashion(boolean v) {
        newsDesk[1]= v;
    }
    public void setSports(boolean v) {
        newsDesk[2]= v;
    }

    public void setOldest(boolean oldest) {
        this.oldest = oldest;
    }
    public Settings clone() throws CloneNotSupportedException {
        Settings other = (Settings) super.clone();
        other.newsDesk = new boolean[3];
        for (int i = 0; i < newsDesk.length; i++) {
            other.newsDesk[i] = newsDesk[i];
        }
        return other;
    }

    public String getNewsDeskList(Resources res) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");

        if (newsDesk[0]) {
            sb.append(" \"");
            sb.append(res.getString(R.string.arts));
            sb.append("\" ");
        }
        if (newsDesk[1]) {
            sb.append(" \"");
            sb.append(res.getString(R.string.fashion));
            sb.append("\" ");
        }
        if (newsDesk[2]) {
            sb.append(" \"");
            sb.append(res.getString(R.string.sports));
            sb.append("\" ");
        }
        sb.append(")");
        return sb.toString();
    }
}
