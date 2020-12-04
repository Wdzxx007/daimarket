package com.jishi.daichao.db.entity;



import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "garbage")
public class GarbageEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    Long id;
    /**
     * 数据操作保存时间
     */
    @ColumnInfo(name = "submit_time")
    String submitTime;
    @ColumnInfo(name = "number")
    String number;
    @ColumnInfo(name = "weight")
    String weight;
    @ColumnInfo(name = "longitude")
    String longitude;
    @ColumnInfo(name = "latitude")
    String latitude;
    @ColumnInfo(name = "type")
    String type;
    @ColumnInfo(name = "image")
    String image;
    @ColumnInfo(name = "take_photo_time")
    long takePhotoTime;

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getTakePhotoTime() {
        return takePhotoTime;
    }

    public void setTakePhotoTime(long takePhotoTime) {
        this.takePhotoTime = takePhotoTime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

}
