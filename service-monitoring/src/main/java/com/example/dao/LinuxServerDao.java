package com.example.dao;

import com.example.demo.EntityList.ServiceCourse;
import com.example.demo.Entity.LinuxServer;
import com.example.demo.Entity.ServerDetails;
import com.example.demo.Entity.UnilizationRatio;

import java.util.List;

/**
 * Linux服务器信息操作接口
 */
public interface LinuxServerDao {
    //保存服务器配置详情信息
    public int saveLinuxServer(LinuxServer linuxServer);
    //获取服务器占用率数据
    public UnilizationRatio getLinuxServerList(String token,String dateTime,String startTime,String finishTime);
    //获取服务器配置信息（处理计算后）
    public ServerDetails getServerDetails(String token);
    //获取服务器各服务进程信息
    public List<ServiceCourse> getServiceProcess(String token,int count);
}
