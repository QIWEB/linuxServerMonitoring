package com.example.demo.Entity;

/**
 * 封装服务器基本信息返回到vue前端
 */
public class ServerArray {

    //服务器唯一id值
    private String value;
    //服务器归属名
    private String label;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
