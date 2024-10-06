package com.example.duongnvdssupperclock.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "alarms")
public class Alarm implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int hour;
    private int minute;
    private String label;
    private boolean isEnabled;

    public Alarm(int id, int hour, int minute, String label, boolean isEnabled) {
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.label = label;
        this.isEnabled = isEnabled;
    }

    protected Alarm(Parcel in) {
        id = in.readInt();
        hour = in.readInt();
        minute = in.readInt();
        label = in.readString();
        isEnabled = in.readByte() != 0;
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
        dest.writeByte((byte) (isEnabled ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getHour() { return hour; }
    public void setHour(int hour) { this.hour = hour; }
    public int getMinute() { return minute; }
    public void setMinute(int minute) { this.minute = minute; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public boolean isEnabled() { return isEnabled; }
    public void setEnabled(boolean enabled) { isEnabled = enabled; }
}