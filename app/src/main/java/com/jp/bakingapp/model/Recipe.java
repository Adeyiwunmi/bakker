package com.jp.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by LENIOVO on 7/22/2017.
 */

public class Recipe implements Parcelable {

private  String name;
    private ArrayList<Ingredient> ingredientArrayList;
    private ArrayList<Step> stepArrayList;

    public Recipe(String name, ArrayList<Ingredient> ingredientArrayList, ArrayList<Step> stepArrayList, String imageUrl) {
        this.name = name;
        this.ingredientArrayList = ingredientArrayList;
        this.stepArrayList = stepArrayList;
        this.imageUrl = imageUrl;
    }

    private String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredient> getIngredientArrayList() {
        return ingredientArrayList;
    }

    public void setIngredientArrayList(ArrayList<Ingredient> ingredientArrayList) {
        this.ingredientArrayList = ingredientArrayList;
    }

    public ArrayList<Step> getStepArrayList() {
        return stepArrayList;
    }

    public void setStepArrayList(ArrayList<Step> stepArrayList) {
        this.stepArrayList = stepArrayList;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static Creator<Recipe> getCREATOR() {
        return CREATOR;
    }

    protected Recipe(Parcel in) {
        name = in.readString();
        ingredientArrayList = in.createTypedArrayList(Ingredient.CREATOR);
        stepArrayList = in.createTypedArrayList(Step.CREATOR);
        imageUrl = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(ingredientArrayList);
        dest.writeTypedList(stepArrayList);
        dest.writeString(imageUrl);
    }
}
