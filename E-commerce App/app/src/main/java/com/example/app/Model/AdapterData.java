package com.example.app.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class AdapterData implements Parcelable {
    public String name;
    public String price;
    public String details;
    public String url;
    public String id;

    public AdapterData(String name, String price, String details, String url, String id) {
        this.name = name;
        this.price = price;
        this.details = details;
        this.url = url;
        this.id = id;
    }

    protected AdapterData(Parcel in) {
        name = in.readString();
        price = in.readString();
        details = in.readString();
        url = in.readString();
        id = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(details);
        dest.writeString(url);
        dest.writeString(id);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<AdapterData> CREATOR = new Parcelable.Creator<AdapterData>() {
        @Override
        public AdapterData createFromParcel(Parcel in) {
            return new AdapterData(in);
        }

        @Override
        public AdapterData[] newArray(int size) {
            return new AdapterData[size];
        }
    };
}