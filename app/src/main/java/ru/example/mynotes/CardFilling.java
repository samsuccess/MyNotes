package ru.example.mynotes;

import android.os.Parcel;
import android.os.Parcelable;

public class CardFilling implements Parcelable {

    private final String title;
    private final String date;

    public CardFilling(String title, String date) {
        this.title = title;
        this.date = date;
    }

    protected CardFilling(Parcel in) {
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

    public static final Creator<CardFilling> CREATOR = new Creator<CardFilling>() {
        @Override
        public CardFilling createFromParcel(Parcel in) {
            return new CardFilling(in);
        }

        @Override
        public CardFilling[] newArray(int size) {
            return new CardFilling[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }
}
