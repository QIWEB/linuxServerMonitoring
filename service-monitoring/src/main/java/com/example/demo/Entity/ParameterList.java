package com.example.demo.Entity;

import java.util.List;

/**
 * 封装参数使用
 */
public class ParameterList {
    //服务器开关状态
    private String isOK;
    //批量要更新的服务器对象集合
    private List<Fanban> fabanList;
    //服务进程状态
    private String processState;
    //批量更新服务进程对象集合
    private List<ServiceProcess> serviceProcessList;

    public String getProcessState() {
        return processState;
    }

    public void setProcessState(String processState) {
        this.processState = processState;
    }

    public List<ServiceProcess> getServiceProcessList() {
        return serviceProcessList;
    }

    public void setServiceProcessList(List<ServiceProcess> serviceProcessList) {
        this.serviceProcessList = serviceProcessList;
    }

    public String getIsOK() {
        return isOK;
    }

    public void setIsOK(String isOK) {
        this.isOK = isOK;
    }

    public List<Fanban> getFabanList() {
        return fabanList;
    }

    public void setFabanList(List<Fanban> fabanList) {
        this.fabanList = fabanList;
    }
}
