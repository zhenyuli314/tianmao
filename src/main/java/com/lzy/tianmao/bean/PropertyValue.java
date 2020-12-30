package com.lzy.tianmao.bean;

public class PropertyValue {
    private int id;
    private int pid;
    private int ptid;
    private String value;

    //额外增加的属性，后期需要取对应的属性名，而不只是ptid


    public PropertyValue(int id, int pid, int ptid, String value) {
        this.id = id;
        this.pid = pid;
        this.ptid = ptid;
        this.value = value;
    }

    public PropertyValue() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getPtid() {
        return ptid;
    }

    public void setPtid(int ptid) {
        this.ptid = ptid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return "PropertyValue{" +
                "id=" + id +
                ", pid=" + pid +
                ", ptid=" + ptid +
                ", value='" + value + '\'' +
                '}';
    }
}
