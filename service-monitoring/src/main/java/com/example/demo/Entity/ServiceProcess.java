package com.example.demo.Entity;

/**
 * 服务进程实体
 */
public class ServiceProcess {
    private String token;//数据唯一标识32字符串
    private String ip;//进程所属服务器ip
    private String name;//进程名字
    /**进程状态：0:无操作，1:启动进程服务，
     * 2:终止进程服务,3:正在启动进程服务，
     * 4:正在终止进程服务，5:异常，6：进程服务运行中，7：进程服务已停止
     **/
    private String status;
    private String remarks;//备注
    private String updateTime;//修改时间
    private String creationDate;//创建时间
    private String marked;//标记是页面请求/linux服务器sh脚本请求

    public String getMarked() {
        return marked;
    }

    public void setMarked(String marked) {
        this.marked = marked;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
