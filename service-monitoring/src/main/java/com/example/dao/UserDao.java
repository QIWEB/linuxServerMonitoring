package com.example.dao;
import com.example.demo.Entity.Fanban;
import com.example.demo.Entity.UserEntity;

import java.util.List;

public interface UserDao {

    public void saveUser(UserEntity user);

    public UserEntity findUserByUserName(String userName);

    public int updateUser(UserEntity user);

    public void deleteUserById(Long id);
    //根据名字和密码查询user
    public UserEntity findUser(UserEntity user);


    public void saveFaban(Fanban fanban);

    public Fanban findUserByIsOK(String isOK, String ip);

    public int updateFaban(Fanban fanban);

    public void deleteFabanById(String id);

    public List<Fanban> findAll(Fanban fanban);

    public Fanban findFaban(String ip);


}
