package com.example.clientwithbottmmenu.ui.profile.model;

public class UserData {
    private boolean mGender;
    private String mAge;
    private String mWeight;
    private String mGrowth;
    private short mLevelActivity;

    public UserData(boolean mGender, String mAge, String mWeight, String mGrowth, short mLevelActivity) {
        this.mGender = mGender;
        this.mAge = mAge;
        this.mWeight = mWeight;
        this.mGrowth = mGrowth;
        this.mLevelActivity = mLevelActivity;
    }

    public boolean isGender() {
        return mGender;
    }

    public void setGender(boolean mGender) {
        this.mGender = mGender;
    }

    public String getAge() {
        return mAge;
    }

    public void setAge(String mAge) {
        this.mAge = mAge;
    }

    public String getWeight() {
        return mWeight;
    }

    public void setWeight(String mWeight) {
        this.mWeight = mWeight;
    }

    public String getGrowth() {
        return mGrowth;
    }

    public void setGrowth(String mGrowth) {
        this.mGrowth = mGrowth;
    }

    public short getLevelActivity() {
        return mLevelActivity;
    }

    public void setLevelActivity(short mLevelActivity) {
        this.mLevelActivity = mLevelActivity;
    }
}
