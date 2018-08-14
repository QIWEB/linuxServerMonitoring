package com.example.demo.Entity;


import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
@Document
public class Fanban implements Serializable {

    private static final long serialVersionUID = -3258839839160856613L;
    //服务器id
    private String id;
    //服务器发版状态
    private String isOK;
    //服务器ip
    private String ip;
    //服务器归属
    private String serverOwnership;
    //备注
    private String remarks;
    private String updateTime;//修改时间
    private String creationDate;//创建时间

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getServerOwnership() {
        return serverOwnership;
    }

    public void setServerOwnership(String serverOwnership) {
        this.serverOwnership = serverOwnership;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsOK() {
        return isOK;
    }

    public void setIsOK(String isOK) {
        this.isOK = isOK;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
