package com.fmf.poem.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fmf on 15/4/2.
 */
public class Poem extends Model implements Parcelable {
    private String title;
    private String subtitle;
    private String author;
    private String created; // Create date
    private String updated; // update datetime
    private String content;
    private String status;
    private String type;
    private long rhythmId;

    public long getRhythmId() {
        return rhythmId;
    }

    public void setRhythmId(long rhythmId) {
        this.rhythmId = rhythmId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.subtitle);
        dest.writeString(this.author);
        dest.writeString(this.created);
        dest.writeString(this.updated);
        dest.writeString(this.content);
        dest.writeString(this.status);
        dest.writeString(this.type);
        dest.writeLong(this.rhythmId);
    }

    public Poem() {
    }

    protected Poem(Parcel in) {
        this.title = in.readString();
        this.subtitle = in.readString();
        this.author = in.readString();
        this.created = in.readString();
        this.updated = in.readString();
        this.content = in.readString();
        this.status = in.readString();
        this.type = in.readString();
        this.rhythmId = in.readLong();
    }

    public static final Parcelable.Creator<Poem> CREATOR = new Parcelable.Creator<Poem>() {
        public Poem createFromParcel(Parcel source) {
            return new Poem(source);
        }

        public Poem[] newArray(int size) {
            return new Poem[size];
        }
    };
}
