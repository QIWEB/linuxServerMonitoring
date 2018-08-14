package com.example.dao.impl;

import com.example.dao.ServiceProcessDao;
import com.example.demo.Entity.ServiceProcess;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceProcessDaoImpl implements ServiceProcessDao{

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public int saveServiceProcess(ServiceProcess serviceProcess) {
        //根据名字查询是否存在，验证服务进程名是否重复
        Query query=new Query(Criteria.where("name").is(serviceProcess.getName()));
        List<ServiceProcess> serviceProcessList= mongoTemplate.find(query,ServiceProcess.class);
        if(serviceProcessList.size()>0){
            return 2;//代表服务进程名已存在
        }
        try {
            mongoTemplate.save(serviceProcess);
            return 1;//代表添加成功
        }catch (Exception e){
            return 0;//代表添加失败
        }
    }

    @Override
    public List<ServiceProcess> getServiceProcessList(ServiceProcess serviceProcess) {
        Query query=new Query();
      if(StringUtils.isNotBlank(serviceProcess.getIp())){
          if(StringUtils.isNotBlank(serviceProcess.getName())){
              query.addCriteria(Criteria.where("ip").is(serviceProcess.getIp()).and("name").is(serviceProcess.getName()));
          }else if(StringUtils.isNotBlank(serviceProcess.getStatus())){
              query.addCriteria(Criteria.where("ip").is(serviceProcess.getIp()).and("status").is(serviceProcess.getStatus()));
          }else if(StringUtils.isNotBlank(serviceProcess.getStatus())
                  && StringUtils.isNotBlank(serviceProcess.getName())){
              query.addCriteria(Criteria.where("ip").is(serviceProcess.getIp())
                      .and("name").is(serviceProcess.getName())
                      .and("status").is(serviceProcess.getStatus()));
          }else {
              query.addCriteria(Criteria.where("ip").is(serviceProcess.getIp()));
          }
      }
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC,"creationDate")));
        List<ServiceProcess> serviceProcessList= mongoTemplate.find(query,ServiceProcess.class);
        return serviceProcessList;
    }
    @Override
    public Long deleteServiceProcess(ServiceProcess serviceProcess) {
        Query query=new Query(Criteria.where("token").is(serviceProcess.getToken()));
       DeleteResult a= mongoTemplate.remove(query,ServiceProcess.class);
        return a.getDeletedCount();
    }

    @Override
    public List<ServiceProcess> getServiceProcessName(ServiceProcess serviceProcess) {
        Query query=new Query(Criteria.where("name").is(serviceProcess.getName()));
        List<ServiceProcess> serviceProcessList= mongoTemplate.find(query,ServiceProcess.class);
        return serviceProcessList;
    }

    @Override
    public Long updateServiceProcess(ServiceProcess serviceProcess) {

        Query query=new Query();
        if(StringUtils.isBlank(serviceProcess.getMarked())) {
                /**
                 * 更改临时状态
                 * 说明：如果参数状态为1：启动服务进程，就修改为临时状态3：正在启动进程服务...
                 *  如果参数状态为2：终止服务进程，就修改为临时状态4：正在终止进程服务...
                 *  当服务器脚本执行完操作 回调信息请求回来 再修改状态为1/2
                 */
                if (serviceProcess.getStatus().equals("1")) {
                    serviceProcess.setStatus("3");
                } else if (serviceProcess.getStatus().equals("2")) {
                    serviceProcess.setStatus("4");
                }
            query.addCriteria(Criteria.where("token").is(serviceProcess.getToken()));
        }else {
            query.addCriteria(Criteria.where("name").is(serviceProcess.getName()).and("ip").is(serviceProcess.getIp()));
        }

        Update update= new Update()
                .set("ip", serviceProcess.getIp())
                .set("name", serviceProcess.getName())
                .set("status",serviceProcess.getStatus())
                .set("remarks",serviceProcess.getRemarks())
                .set("updateTime",serviceProcess.getUpdateTime());
       UpdateResult result= mongoTemplate.upsert(query,update,ServiceProcess.class);
        return result.getMatchedCount();
    }

}
