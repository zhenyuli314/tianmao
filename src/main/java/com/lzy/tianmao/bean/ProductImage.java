package com.lzy.tianmao.bean;

public class ProductImage {
    private int id;
    private int pid;
    private String type;

    public ProductImage(int id, int pid, String type) {
        this.id = id;
        this.pid = pid;
        this.type = type;
    }

    public ProductImage() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ProductImage{" +
                "id=" + id +
                ", pid=" + pid +
                ", type='" + type + '\'' +
                '}';
    }
}
