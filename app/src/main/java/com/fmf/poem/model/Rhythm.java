package com.fmf.poem.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fmf on 15/4/2.
 */
public class Rhythm extends Model implements Parcelable {
    private String name;
    private String alias;
    private String intro;
    private int count;
    private String metre;
    private String sample;
    private String comment;
    private String type;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getMetre() {
        return metre;
    }

    public void setMetre(String metre) {
        this.metre = metre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.alias);
        dest.writeString(this.intro);
        dest.writeInt(this.count);
        dest.writeString(this.metre);
        dest.writeString(this.sample);
        dest.writeString(this.comment);
        dest.writeString(this.type);
    }

    public Rhythm() {
    }

    protected Rhythm(Parcel in) {
        this.name = in.readString();
        this.alias = in.readString();
        this.intro = in.readString();
        this.count = in.readInt();
        this.metre = in.readString();
        this.sample = in.readString();
        this.comment = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<Rhythm> CREATOR = new Parcelable.Creator<Rhythm>() {
        public Rhythm createFromParcel(Parcel source) {
            return new Rhythm(source);
        }

        public Rhythm[] newArray(int size) {
            return new Rhythm[size];
        }
    };
}
