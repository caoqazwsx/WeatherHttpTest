package com.zhao.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhao on 2017/4/19.
 */
@Entity
public class SysData {

    @Id
    private String id;

    private String description;

    @Generated(hash = 1261270326)
    public SysData(String id, String description) {
        this.id = id;
        this.description = description;
    }

    @Generated(hash = 1090007949)
    public SysData() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
