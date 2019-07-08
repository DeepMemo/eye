package com.memo.deep.openmyeye.greenDao.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Note {
    @Property
    private long id;
    @Property
    private String name;
    @Generated(hash = 701879999)
    public Note(long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 1272611929)
    public Note() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
