package com.memo.deep.openmyeye.greenDao.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Note2 {
    @Property
    private Long id;
    @Property
    private String name2;
    @Generated(hash = 715778620)
    public Note2(Long id, String name2) {
        this.id = id;
        this.name2 = name2;
    }
    @Generated(hash = 971933578)
    public Note2() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName2() {
        return this.name2;
    }
    public void setName2(String name2) {
        this.name2 = name2;
    }
}
