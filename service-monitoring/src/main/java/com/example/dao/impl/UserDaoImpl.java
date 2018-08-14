package com.example.dao.impl;


import com.example.dao.UserDao;
import com.example.demo.Entity.Fanban;
import com.example.demo.Entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建对象
     * @param user
     */
    @Override
    public void saveUser(UserEntity user) {
        mongoTemplate.save(user);
    }

    /**
     * 根据用户名查询对象
     * @param userName
     * @return
     */
    @Override
    public UserEntity findUserByUserName(String userName) {
        Query query=new Query(Criteria.where("userName").is(userName));
        UserEntity user =  mongoTemplate.findOne(query , UserEntity.class);
        return user;
    }

    /**
     * 更新对象
     * @param user
     */
    @Override
    public int updateUser(UserEntity user) {
        Query query=new Query(Criteria.where("id").is(user.getId()));
        Update update= new Update().set("userName", user.getUserName()).set("passWord", user.getPassWord());
        //更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query,update,UserEntity.class);
        //更新查询返回结果集的所有
        return 0;
    }

    /**
     * 删除对象
     * @param id
     */
    @Override
    public void deleteUserById(Long id) {
        Query query=new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query,UserEntity.class);
    }

    @Override
    public UserEntity findUser(UserEntity user) {
        Query query=new Query(Criteria.where("userName").is(user.getUserName()).and("passWord").is(user.getPassWord()));
        UserEntity userEntity =  mongoTemplate.findOne(query , UserEntity.class);
        return userEntity;
    }

    @Override
    public void saveFaban(Fanban fanban) {

        mongoTemplate.save(fanban);
    }

    @Override
    public Fanban findUserByIsOK(String isOK,String ip) {
        Query query=new Query(Criteria.where("isOK").is(isOK).and("ip").is(ip));
        Fanban  fanban =  mongoTemplate.findOne(query , Fanban.class);
        return fanban;
    }

    @Override
    public int updateFaban(Fanban fanban) {
        Query query=new Query(Criteria.where("id").is(fanban.getId()));
        Update update= new Update()
                .set("userName", fanban.getIsOK())
                .set("isOK", fanban.getIsOK())
                .set("ip",fanban.getIp())
                .set("serverOwnership",fanban.getServerOwnership())
                .set("remarks",fanban.getRemarks())
                .set("updateTime",fanban.getUpdateTime());
        //更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query,update,Fanban.class);
        return 0;
    }

    @Override
    public void deleteFabanById(String id) {

        Query query=new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query,Fanban.class);

    }

    @Override
    public List<Fanban> findAll(Fanban fanban) {
        Query query=new Query();
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC,"creationDate")));
        List<Fanban> all = mongoTemplate.find(query,Fanban.class);
        return all;

    }

    /**
     * 根据ip获取对象
     * @param ip
     * @return
     */
    @Override
    public Fanban findFaban(String ip) {
        Query query=new Query(Criteria.where("ip").is(ip));
        Fanban  fanban =  mongoTemplate.findOne(query , Fanban.class);
        return fanban;
    }

}