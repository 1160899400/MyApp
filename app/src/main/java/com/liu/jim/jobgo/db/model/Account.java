package com.liu.jim.jobgo.db.model;

import android.content.Intent;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by jim on 2018/4/17.
 * @author Jim.Liu
 * 用户个人信息
 */

@Entity
public class Account {

    @Id(assignable = true)
    private Long id;

    private Byte gender;

    private int contrary;

    private long birth;

    private String description;

    private String resume;

    private String photoLink;

    private Intent education;

    private String realName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public int getContrary() {
        return contrary;
    }

    public void setContrary(int contrary) {
        this.contrary = contrary;
    }

    public long getBirth() {
        return birth;
    }

    public void setBirth(long birth) {
        this.birth = birth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public Intent getEducation() {
        return education;
    }

    public void setEducation(Intent education) {
        this.education = education;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
