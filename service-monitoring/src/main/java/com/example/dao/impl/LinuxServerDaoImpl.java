package com.example.dao.impl;

import com.example.dao.LinuxServerDao;
import com.example.demo.EntityList.*;
import com.example.demo.Entity.LinuxServer;
import com.example.demo.Entity.ServerDetails;
import com.example.demo.Entity.UnilizationRatio;
import com.example.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class LinuxServerDaoImpl implements LinuxServerDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存采集的Linux系统数据
     *
     * @param linuxServer
     * @return
     */
    @Override
    public int saveLinuxServer(LinuxServer linuxServer) {
        try {
            mongoTemplate.save(linuxServer);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 根据token（服务器唯一标示）参数获取服务器信息
     *
     * @param token
     * @return
     */
    @Override
    public UnilizationRatio getLinuxServerList(String token, String dateTime, String startTime, String finishTime) {
        //开始拼接日期参数
        startTime = dateTime + " " + startTime;//拼接开始时间
        finishTime = dateTime + " " + finishTime;//拼接结束时间
        UnilizationRatio result = new UnilizationRatio();//存储最终返回数据结果
        Query query = new Query(Criteria.where("token").is(token).and("creationDate").gte(startTime).lte(finishTime));
        List<LinuxServer> linuxServerList = mongoTemplate.find(query, LinuxServer.class);
        Double[] disk = new Double[linuxServerList.size()];//硬盘使用率数组
        Double[] ram = new Double[linuxServerList.size()];//内存使用率数组
        Double[] cpu = new Double[linuxServerList.size()];//cpu使用率数组
        String[] dataArr = new String[linuxServerList.size()];//时间
        int i = 0;
        for (LinuxServer ls : linuxServerList) {
            Long disk_total = Long.valueOf(ls.getDisk_total());//硬盘总数
            Long disk_usage = Long.valueOf(ls.getDisk_usage());//硬盘使用数
            Long ram_total = Long.valueOf(ls.getRam_total());//内存总数
            Long ram_usage = Long.valueOf(ls.getRam_usage());//内存使用总数
            // 创建一个数值格式化对象
            NumberFormat numberFormat = NumberFormat.getInstance();
            // 设置精确到小数点后2位
            numberFormat.setMaximumFractionDigits(2);
            //计算硬盘使用率
            String diskResult = numberFormat.format((float) disk_usage / (float) disk_total * 100);
            //计算内存使用率
            String ramReault = numberFormat.format((float) ram_usage / (float) ram_total * 100);
            //开始封装返回数据
            disk[i] = Double.valueOf(diskResult);
            ram[i] = Double.valueOf(ramReault);
            cpu[i] = Double.valueOf(ls.getLoad_cpu());
            dataArr[i] = ls.getCreationDate().substring(11, 16);//截取时分
            i++;
          /*  if (i == 12) {//防止数组赋值越界
                break;
            }*/
        }
        result.setDisk(disk);
        result.setRam(ram);
        result.setCpu(cpu);
        result.setDataArr(dataArr);
        return result;
    }

    /**
     * 获取服务器配置信息（处理计算后）
     *
     * @param token:服务器唯一标识
     * @return
     */
    @Override
    public ServerDetails getServerDetails(String token) {
        Query query = new Query(Criteria.where("token").is(token));
        query.skip(0);// skip相当于从那条记录开始
        query.limit(1);// 从skip开始,取多少条记录
        //按创建时间倒序排列
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "creationDate")));
        List<LinuxServer> linuxServerList = mongoTemplate.find(query, LinuxServer.class);
        Long uptime = Long.valueOf(linuxServerList.get(0).getUptime());//转换类型
        Long days = uptime / (24 * 60 * 60);//换算为天数
        //把Bytes根据数值大小转换为KB,MB,GB
        //接收流量
        String rx = Util.getNetFileSizeDescription(Long.valueOf(linuxServerList.get(0).getRx()));
        //发送流量
        String tx = Util.getNetFileSizeDescription(Long.valueOf(linuxServerList.get(0).getTx()));
        ServerDetails sd = new ServerDetails();
        sd.setUptime(days.toString());
        sd.setCpuName(linuxServerList.get(0).getCpu_name());
        sd.setOsName(linuxServerList.get(0).getOs_name() + linuxServerList.get(0).getOs_arch());
        sd.setIp(linuxServerList.get(0).getIpv4());
        sd.setCpuFreq(linuxServerList.get(0).getCpu_cores() + "x" + linuxServerList.get(0).getCpu_freq());
        sd.setOsKernel(linuxServerList.get(0).getOs_kernel());
        sd.setRx(rx);
        sd.setTx(tx);
        sd.setConnections(linuxServerList.get(0).getConnections());
        sd.setNic(linuxServerList.get(0).getNic());
        sd.setSessions(linuxServerList.get(0).getSessions());
        sd.setProcesses(linuxServerList.get(0).getProcesses());
        return sd;
    }

    //获取服务器各服务进程信息
    @Override
    public List<ServiceCourse> getServiceProcess(String token,int count) {
        Query query = new Query(Criteria.where("token").is(token));
        query.skip(0);// skip相当于从那条记录开始
        query.limit(1);// 从skip开始,取多少条记录
        //按创建时间倒序排列
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "creationDate")));
        List<LinuxServer> linuxServerList = mongoTemplate.find(query, LinuxServer.class);
        //获取processes_array：进程列队
        String processes = linuxServerList.get(0).getProcesses_array();
        String[] processes_array = processes.split(";");//按分号分割字符串
        List<String> stringList = new ArrayList<>();
        /**
         * 循环筛选必要的进程信息保存在stringList
         * 有的服务进程是不要的
         */
        for (int i = 0; i < processes_array.length; i++) {
            stringList.add(processes_array[i]);
        }

        //实例各个服务进程对象并调用计算各个进程cop，内存占用方法
        List<ServiceCourse> serviceCourseList = new ArrayList<>();
        List<ServiceCourse> scList = new ArrayList<>();
        //封装解析后的进程数据
        for (String str : stringList) {
            String[] sc_str = str.split(" ");
            ServiceCourse sc = new ServiceCourse();
            sc.setUser(sc_str[0]);
            sc.setCpu(sc_str[1]);
            sc.setmMemory(Util.getNetFileSizeDescription(Long.valueOf(sc_str[2])));
            sc.setName(sc_str[3]);
            serviceCourseList.add(sc);
        }
        //根据count参数 保留要显示的数据
        //count>0 表示根据count数值显示，否则显示全部
        if(count>0){
            for(int i=0;i<count;i++){
                scList.add(serviceCourseList.get(i));
            }
            return scList;
        }
        return serviceCourseList;
    }

}
