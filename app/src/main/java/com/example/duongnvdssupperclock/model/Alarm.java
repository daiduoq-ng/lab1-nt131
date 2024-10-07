package com.example.duongnvdssupperclock.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Alarm implements Parcelable {
    private int id;
    private int hour;
    private int minute;
    private String label;
    private boolean enabled;
    private int year;   // Năm
    private int month;  // Tháng
    private int day;    // Ngày

    public Alarm(int id, int hour, int minute, String label, boolean enabled, int year, int month, int day) {
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.label = label;
        this.enabled = enabled;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    protected Alarm(Parcel in) {
        id = in.readInt();
        hour = in.readInt();
        minute = in.readInt();
        label = in.readString();
        enabled = in.readByte() != 0;
        year = in.readInt();   // Đọc năm từ Parcel
        month = in.readInt();  // Đọc tháng từ Parcel
        day = in.readInt();    // Đọc ngày từ Parcel
    }

    public static final Creator<Alarm> CREATOR = new Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(hour);
        dest.writeInt(minute);
        dest.writeString(label);
        dest.writeByte((byte) (enabled ? 1 : 0));
        dest.writeInt(year);   // Ghi năm vào Parcel
        dest.writeInt(month);  // Ghi tháng vào Parcel
        dest.writeInt(day);    // Ghi ngày vào Parcel
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getLabel() {
        return label;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getYear() {
        return year;   // Getter cho năm
    }

    public int getMonth() {
        return month;  // Getter cho tháng
    }

    public int getDay() {
        return day;    // Getter cho ngày
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public String getFormattedDate() {
        return String.format("%02d/%02d/%04d", day, month + 1, year); // month + 1 because months are zero-based
    }

}