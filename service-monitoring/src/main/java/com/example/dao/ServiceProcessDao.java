package com.example.dao;

import com.example.demo.Entity.ServiceProcess;

import java.util.List;

/**
 * 服务进程操作接口
 */
public interface ServiceProcessDao {
    //新增服务进程
    public int saveServiceProcess(ServiceProcess serviceProcess);
    //获取服务进程
    public List<ServiceProcess> getServiceProcessList(ServiceProcess serviceProcess);
    //更新服务进程
    public Long updateServiceProcess(ServiceProcess serviceProcess);
    //删除服务进程
    public Long deleteServiceProcess(ServiceProcess serviceProcess);
    //根据进程名获取对象
    public List<ServiceProcess> getServiceProcessName(ServiceProcess serviceProcess);
}
