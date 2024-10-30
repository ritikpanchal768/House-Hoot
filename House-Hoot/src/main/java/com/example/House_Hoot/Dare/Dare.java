package com.example.House_Hoot.Dare;

import java.sql.Timestamp;

public class Dare {
    private String id;
    private String dare;
    private Timestamp createdOn;
    private Timestamp modifiedOn;
    private String tag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDare() {
        return dare;
    }

    public void setDare(String dare) {
        this.dare = dare;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Timestamp getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Timestamp modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
