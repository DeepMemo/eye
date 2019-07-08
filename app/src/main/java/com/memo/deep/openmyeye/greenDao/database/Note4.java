package com.memo.deep.openmyeye.greenDao.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Note4 {
    @Property
    private Long id;
    @Property
    private String name4;
    @Generated(hash = 1860913344)
    public Note4(Long id, String name4) {
        this.id = id;
        this.name4 = name4;
    }
    @Generated(hash = 1836660274)
    public Note4() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName4() {
        return this.name4;
    }
    public void setName4(String name4) {
        this.name4 = name4;
    }
}
