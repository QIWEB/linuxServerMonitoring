package com.example.controller;

import com.example.dao.ServiceProcessDao;
import com.example.dao.UserDao;
import com.example.demo.Entity.ParameterList;
import com.example.demo.Entity.Fanban;
import com.example.demo.Entity.ServerArray;
import com.example.demo.Entity.ServiceProcess;
import com.example.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 服务进程操作请求入口
 */
@RestController
@RequestMapping("/process")
@CrossOrigin   //解决跨域问题
public class ServiceProcessController {
    @Autowired
    private ServiceProcessDao serviceProcessDao;
    @Autowired
    private UserDao userDao;
    /**
     * 新增服务进程
     * @param saveServiceProcess
     * @return
     */
    @RequestMapping("/saveServiceProcess")
    public Map<String,Object> saveServiceProcess(@RequestBody ServiceProcess saveServiceProcess){
        Map<String,Object> resultMap=new HashMap<String,Object>();
        //生成32位uuid
        String uuID= UUID.randomUUID().toString().replace("-", "");
        saveServiceProcess.setToken(uuID);
        saveServiceProcess.setCreationDate(Util.dateFormat("yyyy-MM-dd HH:mm:ss"));
        int result=serviceProcessDao.saveServiceProcess(saveServiceProcess);
        if(result==0){
            resultMap.put("success",false);
            resultMap.put("msg","异常错误,新增失败");
        }else if(result==1){
            resultMap.put("success",true);
            resultMap.put("code",1);
            resultMap.put("msg","新增成功");
        }else if(result==2){
            resultMap.put("success",true);
            resultMap.put("code",0);
            resultMap.put("msg","服务进程名已存在");
        }
        return resultMap;
    }

    /**
     * 获取所有服务器归属名以及ip
     * @return
     */
    @RequestMapping("/getServerIp")
    public Map<String,Object> getServerIp(@RequestBody Fanban fanban){
        Map<String,Object> resultMap=new HashMap<String,Object>();
        List<Fanban> FabanList = userDao.findAll(fanban);
        List<ServerArray> serverArrays=new ArrayList<>();
        for (Fanban fb:FabanList){
            ServerArray serverArray=new ServerArray();
            serverArray.setValue(fb.getIp());
            serverArray.setLabel(fb.getServerOwnership()+"-"+fb.getIp());
            serverArrays.add(serverArray);
        }
        resultMap.put("success",true);
        resultMap.put("data",serverArrays);
        return resultMap;
    }

    /**
     * 获取服务进程数据（根据所属服务器ip）
     * @param saveServiceProcess
     * @return
     */
    @RequestMapping("/getServiceProcessList")
    public Map<String,Object> getServiceProcessList(@RequestBody ServiceProcess saveServiceProcess){
        Map<String,Object> resultMap=new HashMap<String,Object>();
        try {
            List<ServiceProcess> serviceProcessList=serviceProcessDao.getServiceProcessList(saveServiceProcess);
            resultMap.put("success",true);
            resultMap.put("data",serviceProcessList);
        }catch (Exception e){
            resultMap.put("success",false);
            resultMap.put("msg","异常错误");
        }
        return resultMap;
    }

    /**
     * 批量更新服务进程状态
     * @param params
     * @return
     */
    @RequestMapping("/batchUpdate")
    public Map<String,Object> batchUpdate(@RequestBody ParameterList params){
        Map<String,Object> resultMap=new HashMap<String,Object>();
        try {
            for (ServiceProcess sp : params.getServiceProcessList()) {
                sp.setStatus(params.getProcessState());
                sp.setUpdateTime(Util.dateFormat("yyyy-MM-dd HH:mm:ss"));

                //根据进程名字获取对象
                List<ServiceProcess> spList=serviceProcessDao.getServiceProcessName(sp);
                //检测要更新的进程状态是否重复，比如已经是启用状态 仍然要更改为启动，这样就不做更新
                String str=spList.get(0).getStatus();
                if(str.equals("1") && sp.getStatus().equals("1")){
                    sp.setStatus("1");
                }else if(str.equals("3") && sp.getStatus().equals("1")){
                    sp.setStatus("3");
                }else if(str.equals("2") && sp.getStatus().equals("2")){
                    sp.setStatus("2");
                }else if(str.equals("4") && sp.getStatus().equals("2")){
                    sp.setStatus("4");
                }else if(str.equals("6") && sp.getStatus().equals("1")){
                    resultMap.put("success",false);
                    resultMap.put("msg","有进程服务已经在运行不可修改！");
                    return resultMap;
                }else if(str.equals("7") && sp.getStatus().equals("2")){
                    resultMap.put("success",false);
                    resultMap.put("msg","有进程服务已经停止不可修改！");
                    return resultMap;
                }
                serviceProcessDao.updateServiceProcess(sp);
            }
            resultMap.put("success",true);
            resultMap.put("msg","更新成功");
        }catch (Exception e){
            resultMap.put("success",false);
            resultMap.put("msg","更新失败");
        }

        return resultMap;
    }
    /**
     * 更新服务进程
     * @param saveServiceProcess
     * @return
     */
    @RequestMapping("/updateServiceProcess")
    public Map<String,Object> updateServiceProcess(@RequestBody ServiceProcess saveServiceProcess){
        Map<String,Object> resultMap=new HashMap<String,Object>();
        //根据进程名字获取对象
        List<ServiceProcess> spList=serviceProcessDao.getServiceProcessName(saveServiceProcess);
        //检测要更新的进程状态是否重复，比如已经是启用状态 仍然要更改为启动，这样就不做更新
        String str=spList.get(0).getStatus();
        if(str.equals("1") && saveServiceProcess.getStatus().equals("1")){
            saveServiceProcess.setStatus("1");
        }else if(str.equals("3") && saveServiceProcess.getStatus().equals("1")){
            saveServiceProcess.setStatus("3");
        }else if(str.equals("2") && saveServiceProcess.getStatus().equals("2")){
            saveServiceProcess.setStatus("2");
        }else if(str.equals("4") && saveServiceProcess.getStatus().equals("2")){
            saveServiceProcess.setStatus("4");
        }else if(str.equals("6") && saveServiceProcess.getStatus().equals("1")){
            resultMap.put("success",false);
            resultMap.put("msg","进程服务已经在运行！");
            return resultMap;
        }else if(str.equals("7") && saveServiceProcess.getStatus().equals("2")){
            resultMap.put("success",false);
            resultMap.put("msg","进程服务已经停止！");
            return resultMap;
        }
        saveServiceProcess.setUpdateTime(Util.dateFormat("yyyy-MM-dd HH:mm:ss"));
        Long result=serviceProcessDao.updateServiceProcess(saveServiceProcess);
        if(result>0){
            resultMap.put("success",true);
            resultMap.put("msg","更新成功");
        }else {
            resultMap.put("success",false);
            resultMap.put("msg","更新失败");
        }
        return resultMap;
    }


    /**
     * 删除服务进程
     * @param saveServiceProcess
     * @return
     */
    @RequestMapping("/deleteServiceProcess")
    public Map<String,Object> deleteServiceProcess(@RequestBody ServiceProcess saveServiceProcess){
        Map<String,Object> resultMap=new HashMap<String,Object>();
        Long result=serviceProcessDao.deleteServiceProcess(saveServiceProcess);
        if(result>0){
            resultMap.put("success",true);
            resultMap.put("msg","删除成功");
        }else {
            resultMap.put("success",false);
            resultMap.put("msg","删除失败");
        }
        return resultMap;
    }

    /** test 测试返回信息
     *
     * linux服务器脚本不断轮询该controller
     *
     * 该接口通过ip去mongodb查询数据 并返回给服务器
     *
     * Linux服务器通过该接口返回的数据来判断是否执行 该ip下面的服务是要要启动和停止服务
     * */
    @RequestMapping("/getDatas")
    public Map<String,String> getMessageOfServer(HttpServletRequest request){

        Map<String,String> resultMap=new HashMap<String,String>();
        Map<String,String> map=new HashMap<String,String>();
        Enumeration em = request.getParameterNames();
        while (em.hasMoreElements()) {
            String name = (String) em.nextElement();
            String value = request.getParameter(name);
            map.put(name,value);
        }

        /**  根据IP获取该IP下面的所有服务器内容  服务器名称和状态 */
        String ipaddr = map.get("ipaddr");
        ServiceProcess serviceProcess = new ServiceProcess();
        serviceProcess.setIp(ipaddr);
        //获取ip下面所有的服务器信息
        List<ServiceProcess> serviceProcessList = serviceProcessDao.getServiceProcessList(serviceProcess);
        for (ServiceProcess list:serviceProcessList) {
            //map中存放服务器名称和服务器状态
            resultMap.put(list.getName(),list.getStatus());
        }
        return resultMap;
    }

    /** test 测试保存数据(修改状态)
     *
     * linux服务器在接收到返回数据后  执行了停止或是启动操作后
     *
     * 会将该状态回传给该controller  此时保存该数据到mongodb即可
     * */

    @RequestMapping("/updateMessage")
    public Map<String,String> updateMessageOfServer(HttpServletRequest request){
        Map<String,String> resultMap=new HashMap<String,String>();
        Map<String,String> map=new HashMap<String,String>();
        Enumeration em = request.getParameterNames();
        while (em.hasMoreElements()) {
            String name = (String) em.nextElement();
            String value = request.getParameter(name);
            map.put(name,value);
        }

        //ipaddr=所属服务器ip
        // name=进程名
        // status=进程状态（1：启动/2：终止）
        //验证参数 是否存在
        if(!map.containsKey("ipaddr")){
            resultMap.put("success","false");
            resultMap.put("msg","ipaddr,参数不存在");
            return resultMap;
        }
        if(!map.containsKey("name")){
            resultMap.put("success","false");
            resultMap.put("msg","name,参数不存在");
            return resultMap;
        }
        if(!map.containsKey("status")){
            resultMap.put("success","false");
            resultMap.put("msg","status,参数不存在");
            return resultMap;
        }
        if(null==map.get("ipaddr").toString() || map.get("ipaddr").toString().equals("")){
            resultMap.put("success","false");
            resultMap.put("msg","ipaddr,参数为空");
            return resultMap;
        }
        if(null==map.get("name").toString() || map.get("name").toString().equals("")){
            resultMap.put("success","false");
            resultMap.put("msg","name,参数为空");
            return resultMap;
        }
        if(null==map.get("status").toString() || map.get("status").toString().equals("")){
            resultMap.put("success","false");
            resultMap.put("msg","status,参数为空");
            return resultMap;
        }

        //开始更新数据
        ServiceProcess sp=new ServiceProcess();
        sp.setIp(map.get("ipaddr").toString());
        sp.setName(map.get("name").toString());
        sp.setStatus(map.get("status").toString());
        sp.setMarked("linux");
        Long s=serviceProcessDao.updateServiceProcess(sp);
        if(s>0){
            resultMap.put("success","true");
            resultMap.put("msg","更新成功");
        }else {
            resultMap.put("success","false");
            resultMap.put("msg","更新失败");
        }
        return resultMap;
    }
}
