package com.jishi.daichao.entity;

/**
 * Created by Administrator on 2019/2/25.
 */

public class BorrowListEntity {
    private String img;
    private String title;
    private String requirement;
    private String monety;
    private String rate;
    private String peopleCount;
    private String schedule;

    public BorrowListEntity(String img, String title, String requirement, String monety, String rate, String peopleCount, String schedule) {
        this.img = img;
        this.title = title;
        this.requirement = requirement;
        this.monety = monety;
        this.rate = rate;
        this.peopleCount = peopleCount;
        this.schedule = schedule;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getMonety() {
        return monety;
    }

    public void setMonety(String monety) {
        this.monety = monety;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(String peopleCount) {
        this.peopleCount = peopleCount;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
