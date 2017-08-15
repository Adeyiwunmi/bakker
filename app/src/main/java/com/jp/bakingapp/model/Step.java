package com.jp.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LENIOVO on 7/22/2017.
 */

public class Step implements Parcelable {



    private int id;
    private String description;
    private  String videoUrl;
    private String thumbnailUrl;
    private String shortDescription;




    public Step(int id, String description, String videoUrl, String thumbnailUrl, String shortDescription) {
        this.id = id;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.shortDescription = shortDescription;
    }


    protected Step(Parcel in) {
        id = in.readInt();
        description = in.readString();
        videoUrl = in.readString();
        thumbnailUrl = in.readString();
        shortDescription = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(description);
        dest.writeString(videoUrl);
        dest.writeString(thumbnailUrl);
        dest.writeString(shortDescription);
    }
}
