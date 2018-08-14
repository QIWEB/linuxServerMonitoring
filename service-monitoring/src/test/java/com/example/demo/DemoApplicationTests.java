package com.example.demo;

import com.example.dao.LinuxServerDao;
import com.example.dao.ServiceProcessDao;
import com.example.dao.UserDao;
import com.example.demo.Entity.*;
import com.example.demo.EntityList.ServiceCourse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	private UserDao userDao;
	@Autowired
	private LinuxServerDao linuxServerDao;
	@Autowired
	private ServiceProcessDao serviceProcessDao;

	@Test
	public  void testsaveServiceProcess(){
		ServiceProcess sp=new ServiceProcess();
		//生成32位uuid
		String uuID= UUID.randomUUID().toString().replace("-", "");
		sp.setToken(uuID);
		sp.setIp("192.168.0.73");
		sp.setName("tomcat5");
		sp.setStatus("0");
		sp.setRemarks("备注");
		serviceProcessDao.saveServiceProcess(sp);
	}

	@Test
	public void testgetServiceProcess(){
		List<ServiceCourse> sp=linuxServerDao.getServiceProcess("4cb12a8545f04e5887a90f422beedbf0",0);
		System.err.println(sp);
	}
	@Test
	public void testgetServerDetails(){
		ServerDetails sd=linuxServerDao.getServerDetails("4cb12a8545f04e5887a90f422beedbf0");
		System.err.println(sd);
	}
	@Test
	public void testGetLinuxServerList(){
		UnilizationRatio ls= linuxServerDao.getLinuxServerList("4cb12a8545f04e5887a90f422beedbf0","2018-06-19","11:05","12:05");
	    System.err.println(ls);
	}

	@Test
	public void testSaveUser() throws Exception {
		UserEntity user=new UserEntity();
		user.setId(1L);
		user.setUserName("admin");
		user.setPassWord("admin123");
		userDao.saveUser(user);

		UserEntity user2=new UserEntity();
		user2.setId(2L);
		user2.setUserName("admin001");
		user2.setPassWord("admin123");
		userDao.saveUser(user2);
	}

	// 测试faban实体
	@Test
	public void testSaveFaban() throws Exception {
		Fanban fanban79=new Fanban();
		fanban79.setId("1");
		System.out.println();
		fanban79.setIsOK("OK");
		fanban79.setIp("11.24.200.79");
		fanban79.setServerOwnership("康师傅(康饮)");
		fanban79.setRemarks("");
		userDao.saveFaban(fanban79);


		Fanban fanban27=new Fanban();
		fanban27.setId("2");
		fanban27.setIsOK("notOK");
		fanban27.setIp("11.24.200.27");
		fanban27.setServerOwnership("康师傅(康饮)");
		fanban27.setRemarks("");
		userDao.saveFaban(fanban27);



		Fanban fanban172=new Fanban();
		fanban172.setId("3");
		fanban172.setIsOK("notOK");
		fanban172.setIp("11.24.201.172");
		fanban172.setServerOwnership("康师傅(康饮)");
		fanban172.setRemarks("");
		userDao.saveFaban(fanban172);


		Fanban fanban224=new Fanban();
		fanban224.setId("4");
		fanban224.setIsOK("notOK");
		fanban224.setIp("11.24.200.224");
		fanban224.setServerOwnership("康师傅(康饮)");
		fanban224.setRemarks("");
		userDao.saveFaban(fanban224);



		Fanban fanban223=new Fanban();
		fanban223.setId("5");
		fanban223.setIsOK("OK");
		fanban223.setIp("11.24.201.223");
		fanban223.setServerOwnership("康师傅(百饮)");
		fanban223.setRemarks("");
		userDao.saveFaban(fanban223);

		Fanban fanban6=new Fanban();
		fanban6.setId("6");
		fanban6.setIsOK("OK");
		fanban6.setIp("11.24.201.6");
		fanban6.setServerOwnership("康师傅(百饮)");
		fanban6.setRemarks("");
		userDao.saveFaban(fanban6);

		Fanban fanban230=new Fanban();
		fanban230.setId("7");
		fanban230.setIsOK("OK");
		fanban230.setIp("11.24.200.230");
		fanban230.setServerOwnership("康师傅(百饮)");
		fanban230.setRemarks("");
		userDao.saveFaban(fanban230);

		Fanban fanban231=new Fanban();
		fanban231.setId("8");
		fanban231.setIsOK("OK");
		fanban231.setIp("11.24.200.231");
		fanban231.setServerOwnership("康师傅(百饮)");
		fanban231.setRemarks("");
		userDao.saveFaban(fanban231);

	}

	@Test
	public void updateFaban(){
		Fanban fanban223=new Fanban();
		fanban223.setId("5");
		fanban223.setIsOK("OK");
		fanban223.setIp("11.24.201.223");
		userDao.updateFaban(fanban223);
	}






	@Test
	public void testSaveFaban_27() throws Exception {
		Fanban fanban=new Fanban();
		fanban.setId("4");
		fanban.setIsOK("notOK");
		fanban.setIp("11.24.200.xxxxxxxxx");
		userDao.saveFaban(fanban);
	}






	@Test
	public void findUserByUserName(){
		UserEntity user= userDao.findUserByUserName("小明");
		System.out.println("user is "+user);
	}

	@Test
	public void updateUser(){
		UserEntity user=new UserEntity();
		user.setId(2l);
		user.setUserName("天空");
		user.setPassWord("fffxxxx");
		userDao.updateUser(user);
	}

	@Test
	public void deleteUserById(){
		userDao.deleteUserById(1l);
	}
	@Test
	public void findFaban(){
		Fanban  fanban =userDao.findFaban("11.24.200.79");
		System.err.println(fanban.getIp());
	}
}
