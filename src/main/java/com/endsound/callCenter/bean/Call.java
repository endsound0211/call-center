package com.endsound.callCenter.bean;

public class Call {
    private Integer id;
    private Integer level;

    public Call(Integer id){
        this.id = id;
        this.level = 0;
    }

    public Integer getLevel() {
        return level;
    }

    public Call setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public Integer getId() {
        return id;
    }
}
