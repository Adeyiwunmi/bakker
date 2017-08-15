package com.jp.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LENIOVO on 7/22/2017.
 */

public class Ingredient implements Parcelable {
    private String name;
    private  String measure;
    private int quantity;




    public Ingredient(String name, String measure, int quantity) {
        this.name = name;
        this.measure = measure;
        this.quantity = quantity;
    }


    protected Ingredient(Parcel in) {
        name = in.readString();
        measure = in.readString();
        quantity = in.readInt();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(measure);
        dest.writeInt(quantity);
    }
}
