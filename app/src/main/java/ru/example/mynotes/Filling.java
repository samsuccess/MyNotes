package ru.example.mynotes;

import android.os.Parcel;
import android.os.Parcelable;

public class Filling implements Parcelable {

    private String title;
    private String date;

    public Filling(String title, String description, String date) {
        this.title = title;
        this.date = date;
    }

    protected Filling(Parcel in) {
        title = in.readString();
        date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Filling> CREATOR = new Creator<Filling>() {
        @Override
        public Filling createFromParcel(Parcel in) {
            return new Filling(in);
        }

        @Override
        public Filling[] newArray(int size) {
            return new Filling[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }
}
