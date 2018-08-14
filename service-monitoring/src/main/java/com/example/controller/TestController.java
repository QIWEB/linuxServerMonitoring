package com.example.controller;

import com.example.dao.LinuxServerDao;
import com.example.dao.UserDao;
import com.example.demo.Entity.*;
import com.example.demo.EntityList.ServiceCourse;
import com.example.util.Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/xx")
@CrossOrigin   //解决跨域问题
public class TestController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private LinuxServerDao linuxServerDao;


    @RequestMapping("/login")
    public Map<String,Object> login(@RequestBody UserEntity user){
        Map<String,Object> map=new HashMap<>();
        UserEntity userEntity=userDao.findUser(user);
        if(null!=userEntity){
            map.put("success",true);
        }else{
            map.put("success",false);
        }
        return map;
    }


    /** --------------------------------------------------康饮服务自动发版-------------------------------------------------------------  */

    /** KY_11_24_200_79  服务自动发版   */
    @RequestMapping("79")
    public String getKY_11_24_200_79(HttpServletRequest req)throws Exception{
        //携带的参数 测试请求是否可以发过来
        String parameter = req.getParameter("FABANMA");
        System.out.println("-----------------------------"+parameter);
        /**
         * 1.查询数据库  如果可以查到数据就发版  返回ok就可以
         *
         *
         * 2.查询数据库  如果可以查到数据就发版  返回notOK就不发版
         *
         * */
        String isOK="OK";
        String ip="11.24.200.79";
        String isOKNOTOK ="";
        Fanban userByIsOK = userDao.findUserByIsOK(isOK, ip);
        if(userByIsOK!=null&&userByIsOK.getIsOK()!=null&&"OK".equals(userByIsOK.getIsOK())){
            isOKNOTOK="OK";
        }else {
            isOKNOTOK="notOK";
        }
        return isOKNOTOK;
    }


    /** KY_11_24_200_27  服务自动发版   */
    @RequestMapping("27")
    public String getKY_11_24_200_27(HttpServletRequest req)throws Exception{
        //携带的参数 测试请求是否可以发过来
        String parameter = req.getParameter("FABANMA");
        //  TODO  去数据库查询  如果可以查到数据就发版
        /**
         * 1.查询数据库  如果可以查到数据就发版  返回ok就可以
         *
         *
         * 2.查询数据库  如果可以查到数据就发版  返回notOK就不发版
         *
         * */
        String isOK="OK";
        String ip="11.24.200.27";
        String isOKNOTOK ="";
        Fanban userByIsOK = userDao.findUserByIsOK(isOK, ip);
        if(userByIsOK!=null&&userByIsOK.getIsOK()!=null&&"OK".equals(userByIsOK.getIsOK())){
            isOKNOTOK="OK";
        }else {
            isOKNOTOK="notOK";
        }
        return isOKNOTOK;
    }

    /** KY_11_24_201_172  服务自动发版   */
    @RequestMapping("172")
    public String getKY_11_24_201_172(HttpServletRequest req)throws Exception{
        //携带的参数 测试请求是否可以发过来
        String parameter = req.getParameter("FABANMA");
        //  TODO  去数据库查询  如果可以查到数据就发版
        /**
         * 1.查询数据库  如果可以查到数据就发版  返回ok就可以
         *
         *
         * 2.查询数据库  如果可以查到数据就发版  返回notOK就不发版
         *
         * */
        String isOK="OK";
        String ip="11.24.201.172";
        String isOKNOTOK ="";
        Fanban userByIsOK = userDao.findUserByIsOK(isOK, ip);
        if(userByIsOK!=null&&userByIsOK.getIsOK()!=null&&"OK".equals(userByIsOK.getIsOK())){
            isOKNOTOK="OK";
        }else {
            isOKNOTOK="notOK";
        }
        return isOKNOTOK;
    }

    /** KY_11_24_200_224  服务自动发版   */
    @RequestMapping("224")
    public String getKY_11_24_200_224(HttpServletRequest req)throws Exception{
        //携带的参数 测试请求是否可以发过来
        String parameter = req.getParameter("FABANMA");
        //  TODO  去数据库查询  如果可以查到数据就发版
        /**
         * 1.查询数据库  如果可以查到数据就发版  返回ok就可以
         *
         *
         * 2.查询数据库  如果可以查到数据就发版  返回notOK就不发版
         *
         * */
        String isOK="OK";
        String ip="11.24.200.224";
        String isOKNOTOK ="";
        Fanban userByIsOK = userDao.findUserByIsOK(isOK, ip);
        if(userByIsOK!=null&&userByIsOK.getIsOK()!=null&&"OK".equals(userByIsOK.getIsOK())){
            isOKNOTOK="OK";
        }else {
            isOKNOTOK="notOK";
        }
        return isOKNOTOK;
    }


    /** ----------------------------------------------百饮服务自动发版-----------------------------------------------------------------  */


    /** BY_11_24_201_6  服务自动发版   */
    @RequestMapping("6")
    public String getBY_11_24_201_6(HttpServletRequest req)throws Exception{
        //携带的参数 测试请求是否可以发过来
        String parameter = req.getParameter("FABANMA");
        //  TODO  去数据库查询  如果可以查到数据就发版
        /**
         * 1.查询数据库  如果可以查到数据就发版  返回ok就可以
         *
         *
         * 2.查询数据库  如果可以查到数据就发版  返回notOK就不发版
         *
         * */
        String isOK="OK";
        String ip="11.24.201.6";
        String isOKNOTOK ="";
        Fanban userByIsOK = userDao.findUserByIsOK(isOK, ip);
        if(userByIsOK!=null&&userByIsOK.getIsOK()!=null&&"OK".equals(userByIsOK.getIsOK())){
            isOKNOTOK="OK";
        }else {
            isOKNOTOK="notOK";
        }
        return isOKNOTOK;
    }


    /** BY_11_24_200_230  服务自动发版   */
    @RequestMapping("230")
    public String getBY_11_24_200_230(HttpServletRequest req)throws Exception{
        //携带的参数 测试请求是否可以发过来
        String parameter = req.getParameter("FABANMA");
        //  TODO  去数据库查询  如果可以查到数据就发版
        /**
         * 1.查询数据库  如果可以查到数据就发版  返回ok就可以
         *
         *
         * 2.查询数据库  如果可以查到数据就发版  返回notOK就不发版
         *
         * */




        String isOK="OK";
        String ip="11.24.200.230";
        String isOKNOTOK ="";
        Fanban userByIsOK = userDao.findUserByIsOK(isOK, ip);
        if(userByIsOK!=null&&userByIsOK.getIsOK()!=null&&"OK".equals(userByIsOK.getIsOK())){
            isOKNOTOK="OK";
        }else {
            isOKNOTOK="notOK";
        }
        return isOKNOTOK;
    }

    /** BY_11_24_200_233  服务自动发版   */
    @RequestMapping("233")
    public String BY_11_24_201_233(HttpServletRequest req)throws Exception{
        //携带的参数 测试请求是否可以发过来
        String parameter = req.getParameter("FABANMA");
        //  TODO  去数据库查询  如果可以查到数据就发版
        /**
         * 1.查询数据库  如果可以查到数据就发版  返回ok就可以
         *
         *
         * 2.查询数据库  如果可以查到数据就发版  返回notOK就不发版
         *
         * */
        String isOK="OK";
        String ip="11.24.200.233";
        String isOKNOTOK ="";
        Fanban userByIsOK = userDao.findUserByIsOK(isOK, ip);
        if(userByIsOK!=null&&userByIsOK.getIsOK()!=null&&"OK".equals(userByIsOK.getIsOK())){
            isOKNOTOK="OK";
        }else {
            isOKNOTOK="notOK";
        }
        return isOKNOTOK;
    }

    /** BY_11_24_200_231  服务自动发版   */
    @RequestMapping("/231")
    public String BY_11_24_200_231(HttpServletRequest req)throws Exception{
        //携带的参数 测试请求是否可以发过来
        String parameter = req.getParameter("FABANMA");
        System.out.println(parameter);
        //  TODO  去数据库查询  如果可以查到数据就发版
        /**
         * 1.查询数据库  如果可以查到数据就发版  返回ok就可以
         *
         *
         * 2.查询数据库  如果可以查到数据就发版  返回notOK就不发版
         *
         * */
        String isOK="OK";
        String ip="11.24.200.231";
        String isOKNOTOK ="";
        Fanban userByIsOK = userDao.findUserByIsOK(isOK, ip);
        if(userByIsOK!=null&&userByIsOK.getIsOK()!=null&&"OK".equals(userByIsOK.getIsOK())){
            isOKNOTOK="OK";
        }else {
            isOKNOTOK="notOK";
        }
        return isOKNOTOK;
    }






    /** -----------------------------------------------testing----------------------------------------------------------------------- */

    /** BY_11_24_201_223 服务自动发版   */
    @RequestMapping("/223")
    public String KY_11_24_201_223(HttpServletRequest req)throws Exception{
        //携带的参数 测试请求是否可以发过来
        String parameter = req.getParameter("FABANMA");
        //  TODO  去数据库查询  如果可以查到数据就发版
        /**
         * 1.查询数据库  如果可以查到数据就发版  返回ok就可以
         *
         *
         * 2.查询数据库  如果可以查到数据就发版  返回notOK就不发版
         *
         * */
        System.out.println(parameter);
        String isOK="OK";
        String ip="11.24.201.223";
        String isOKNOTOK ="";
        Fanban userByIsOK = userDao.findUserByIsOK(isOK, ip);
        if(userByIsOK!=null&&userByIsOK.getIsOK()!=null&&"OK".equals(userByIsOK.getIsOK())){
            isOKNOTOK="OK";
        }else {
            isOKNOTOK="notOK";
        }
        return isOKNOTOK;
    }




    /** BY_11_24_201_223 服务自动发版
     * 获取所有服务器数据
     *  */
    @RequestMapping("/getAll")
    public Map<String,Object> getAll(HttpServletRequest req,Fanban fanban)throws Exception{
        //携带的参数 测试请求是否可以发过来
        //String parameter = req.getParameter("FABANMA");
        //  TODO  去数据库查询  如果可以查到数据就发版
        /**
         * 1.查询数据库  如果可以查到数据就发版  返回ok就可以
         *
         *
         * 2.查询数据库  如果可以查到数据就发版  返回notOK就不发版
         *
         * */
       /* String isOK="OK";
        String ip="11.24.201.223";
        String isOKNOTOK ="";*/
        // Fanban fanban = new Fanban();
        //List<Fanban> FabanList = userDao.findAll(fanban);
        /*MongoCollection<Document> faban = mongoTemplate.getCollection("Faban");
        MongoNamespace namespace = faban.getNamespace();
        String collectionName = namespace.getCollectionName();
        System.out.println(collectionName);
        String databaseName = namespace.getDatabaseName();
        System.out.println(databaseName);
        String fullName = namespace.getFullName();
        System.out.println(fullName);

        System.out.println("xxxxxxxxxxxxxxxxxxxxxx"+faban.count());
        FindIterable<Document> documents = faban.find();
        System.out.println("yyyyyyyyyyyyy"+documents.toString());
        MongoCursor<Document> iterator = documents.iterator();
        System.out.println(iterator.toString()+"xxxxxxxxxxx");*/

        List<Fanban> FabanList = userDao.findAll(fanban);
        Map<String,Object> map=new HashMap<String,Object>();
            map.put("success",true);
            map.put("data",FabanList);
        return map;
    }

    /**
     * 批量/单个更新服务器发版状态
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateFabanAll")
    public Map<String,Object> updateFabanAll(@RequestBody ParameterList params){
        Map<String,Object> map=new HashMap<String,Object>();
        //验证参数
        if(null==params.getIsOK() || null==params.getFabanList()){
            map.put("success",false);
            return map;
        }
        for (int i=0;i<params.getFabanList().size();i++){
            params.getFabanList().get(i).setIsOK(params.getIsOK());
            params.getFabanList().get(i).setUpdateTime(Util.dateFormat("yyyy-MM-dd HH:mm:ss"));
            userDao.updateFaban(params.getFabanList().get(i));
        }
        map.put("success",true);
        return map;
    }

    /**
     * 新增服务器
     * @return
     */
    @RequestMapping("/saveFaban")
    public Map<String,Object> saveFaban(@RequestBody Fanban fanban){
        Map<String,Object> map=new HashMap<String,Object>();
        //验证ip是否重复
        Fanban  fanbanIp =userDao.findFaban(fanban.getIp());
        if(null!=fanbanIp){
            map.put("success",false);
            map.put("msg","ip已存在");
            return map;
        }
        try {
            //生成32位uuid
            String uuID=UUID.randomUUID().toString().replace("-", "");
            fanban.setId(uuID);
            fanban.setCreationDate(Util.dateFormat("yyyy-MM-dd HH:mm:ss"));
            userDao.saveFaban(fanban);
            map.put("success",true);
            map.put("token",uuID);
        }catch (Exception e){
            map.put("success",false);
            map.put("msg","异常错误");
        }
        return map;
    }

    /**
     * 单个更新服务器信息
     * @param fanban
     * @return
     */
    @RequestMapping("/updateFaban")
    public Map<String,Object> updateFaban(@RequestBody Fanban fanban){
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            fanban.setUpdateTime(Util.dateFormat("yyyy-MM-dd HH:mm:ss"));
            userDao.updateFaban(fanban);
            map.put("success",true);
        }catch (Exception e){
            map.put("success",false);
            map.put("msg","异常错误");
        }
        return map;
    }

    /**
     * 删除服务器信息
     * @param fanban
     * @return
     */
    @RequestMapping("/deleteFaban")
    public Map<String,Object> deleteFaban(@RequestBody Fanban fanban){
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            userDao.deleteFabanById(fanban.getId());
            map.put("success",true);
        }catch (Exception e){
            map.put("success",false);
            map.put("msg","异常错误");
        }
        return map;
    }

    /**
     * 获取采集的Linux服务器数据
     * @param request
     * @return
     */
    @RequestMapping("/getLinuxData")
    public Map<String,Object> getLinuxData(HttpServletRequest request){
        Map<String,Object> resultMap=new HashMap<String,Object>();
        Map<String,Object> map=new HashMap<String,Object>();
        LinuxServer ls=null;
        Enumeration em = request.getParameterNames();
        while (em.hasMoreElements()) {
            String name = (String) em.nextElement();
            String value = request.getParameter(name);
            map.put(name,value);
        }
        try {
             ls=(LinuxServer) Util.convertMap(LinuxServer.class,map);
            String time= Util.dateFormat("yyyy-MM-dd HH:mm:ss");
            ls.setCreationDate(time);
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("success",false);
            resultMap.put("msg","异常错误");
        }
        Fanban fb=userDao.findFaban(ls.getIpv4());//根据ip获取服务器对象
        if(null==fb){
            resultMap.put("success",false);
            resultMap.put("msg","此服务器不在监测范围，请添加服务器到mongodb!");
        }
        ls.setToken(fb.getId());//把服务器id替换到LinuxServer实体的Token字段作为唯一字段
          int addLS=linuxServerDao.saveLinuxServer(ls);
        if(addLS>0){
            resultMap.put("success",true);
        }else{
            resultMap.put("success",false);
            resultMap.put("msg","添加失败!");
        }
        return resultMap;
    }

    /**
     * 获取所有服务器归属名以及id
     * @return
     */
    @RequestMapping("/getServerOwnership")
    public Map<String,Object> getServerOwnership(Fanban fanban){
        Map<String,Object> resultMap=new HashMap<String,Object>();
        List<Fanban> FabanList = userDao.findAll(fanban);
        List<ServerArray> serverArrays=new ArrayList<>();
        for (Fanban fb:FabanList){
            ServerArray serverArray=new ServerArray();
            serverArray.setValue(fb.getId());
            serverArray.setLabel(fb.getServerOwnership()+"-"+fb.getIp());
            serverArrays.add(serverArray);
        }
        resultMap.put("success",true);
        resultMap.put("data",serverArrays);
        return resultMap;
    }
    /**
     * 获取服务器使用率和负载信息
     * @param token:服务器唯一标识
     * @param dateTime:筛选条件 年月日
     * @param startTime：筛选条件 开始时间
     * @param finishTime：筛选条件 结束时间
     * @return
     */
    @RequestMapping("/getUnilizationRatio")
    public Map<String,Object> getUnilizationRatio(String token,String dateTime,String startTime,String finishTime){
        Map<String,Object> resultMap=new HashMap<String,Object>();
        UnilizationRatio urList=linuxServerDao.getLinuxServerList(token,dateTime,startTime,finishTime);
        if(urList.getDataArr().length>0){
            if(urList.getDisk().length>0 || urList.getRam().length>0 || urList.getCpu().length>0){
                resultMap.put("success",true);
                resultMap.put("data",urList);
            }else{
                resultMap.put("success",false);
                resultMap.put("data","");
            }

        }else{
            resultMap.put("success",false);
            resultMap.put("data","");
        }
        return resultMap;
    }

    /**
     * 获取服务器配置信息（处理计算后）
     * @param token
     * @return
     */
    @RequestMapping("/getServerDetails")
    public Map<String,Object> getServerDetails(String token){
        Map<String,Object> resultMap=new HashMap<String,Object>();
        try {
            ServerDetails sd=linuxServerDao.getServerDetails(token);
            resultMap.put("success",true);
            resultMap.put("data",sd);
        }catch (Exception e){
            resultMap.put("success",false);
            resultMap.put("data","");
        }
        return resultMap;
    }

    /**
     * 获取服务器运行进程信息
     * @param token
     * count 要显示的条数
     * @return
     */
    @RequestMapping("/getServiceProcess")
    public Map<String,Object> getServiceProcess(String token,int count){
        Map<String,Object> resultMap=new HashMap<String,Object>();
        try{
            List<ServiceCourse> sp=linuxServerDao.getServiceProcess(token,count);
            resultMap.put("success",true);
            resultMap.put("data",sp);
        }catch (Exception e){
            resultMap.put("success",false);
            resultMap.put("data","");
        }
        return resultMap;
    }












































    // 字符串读取
    // 方法一
    public static String ReadAsChars(HttpServletRequest request)
    {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try
        {

            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null)
            {
                sb.append(str);
            }
            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != br)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    // 方法二
    public  String ReadAsCharsXX(HttpServletRequest request)
    {
        InputStream is = null;
        StringBuilder sb = null;
        try
        {
            is = request.getInputStream();
            sb = new StringBuilder();
            byte[] b = new byte[4096];
            for (int n; (n = is.read(b)) != -1;)
            {
                sb.append(new String(b, 0, n));
            }

        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != is)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    // 二进制读取
    public static byte[] readAsBytesYY(HttpServletRequest request)
    {

        int len = request.getContentLength();
        byte[] buffer = new byte[len];
        ServletInputStream in = null;

        try
        {
            in = request.getInputStream();
            in.read(buffer, 0, len);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != in)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return buffer;
    }

    /**   服务自动发版
     * 通过ip或者自动获取ip
     * */
    @RequestMapping("auto")
    public String getAuto(HttpServletRequest req)throws Exception{
        //携带的参数 测试请求是否可以发过来
        String parameter = req.getParameter("FABANMA");
        //  TODO  去数据库查询  如果可以查到数据就发版
        /**
         * 1.查询数据库  如果可以查到数据就发版  返回ok就可以
         *
         *
         * 2.查询数据库  如果可以查到数据就发版  返回notOK就不发版
         *
         * */
        String ip=req.getParameter("ip");
        String isOK="OK";
        if(StringUtils.isBlank(ip)){
            ip= req.getRemoteAddr();
        }
        System.out.println(ip);
        //String ip="11.24.201.172";
        String isOKNOTOK ="";
        Fanban userByIsOK = userDao.findUserByIsOK(isOK, ip);
        if(userByIsOK!=null&&userByIsOK.getIsOK()!=null&&"OK".equals(userByIsOK.getIsOK())){
            isOKNOTOK="OK";
        }else {
            isOKNOTOK="notOK";
        }
        return isOKNOTOK;
    }


    public static void main(String[] args) {
        //测试生成32位uuid
        //System.err.println(UUID.randomUUID().toString().replace("-", ""));
       // "disk_total" : "36471050240", "disk_usage" : "6491516928",
       /*String date="2018-06-19 11:07:39";
        System.err.println("数据："+date.substring(0,11));*/
        /*long days = 4815580/(24*60*60);
        System.err.println(days);*/
        Map<String,String> map=new HashMap<String,String>();
        map.put("opt","");
       if(map.containsKey("opt2")){
           System.err.println("存在");
       }else{
           System.err.println("不存在");
       }
    }

}
