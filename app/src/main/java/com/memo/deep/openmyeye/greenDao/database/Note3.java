package com.memo.deep.openmyeye.greenDao.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Note3 {
    @Property
    private Long id;
    @Property
    private String name3;
    @Generated(hash = 2065588020)
    public Note3(Long id, String name3) {
        this.id = id;
        this.name3 = name3;
    }
    @Generated(hash = 988321718)
    public Note3() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName3() {
        return this.name3;
    }
    public void setName3(String name3) {
        this.name3 = name3;
    }
}
