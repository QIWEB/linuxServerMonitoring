# linux服务器监控平台
技术：nodejs vue java mongodb springboot linux shell
linux服务器监控项目，前后端分离vue+springboot+mongodb

## ：

  
## 1、启动前台：
使用命令：
### A 先安装nodejs 
并配置好环境变量
### B 先控制台cmd命令切换到项目目录：
CD db-service-monitoring 
### C 第一次运行要安装依赖库：
npm install
### D 后执行：
npm run dev
### E 前台配置后台请求地址
在文件：db-service-monitoring\src\axios\axiosUrl.js
  /**
   * 配置全局使用的请求地址url
   *  11.24.201.68
   *  192.168.0.5
   */
  export default {
    rootDirectory: function () { // 请求接口的根路径
      return 'http://192.168.0.26:9999'
    },
    
修改访问地址和端口号：
打开文件db-service-monitoring\config\index.js

 host: '192.168.0.26', // can be overwritten by process.env.HOST
    port: 8082, // can be overwritten by process.env.PORT, if port is in use, a free one will be determined
    autoOpenBrowser: false,
### F 启动后显示如下信息
 DONE  Compiled successfully in 16239ms                                                                                                                         16:44:38

 I  Your application is running here: http://localhost:8083
 
 
 
##  2、后台启动类
A 配置文件中修改mongodb数据库连接
B 运行单元测试 初始化数据 
service-monitoring\src\test\java\com\example\demo\DemoApplicationTests.java
手动打开mongodb新建一个集合
Collection('userEntity')
并新增一条用户：
{
    "_id" : NumberLong(4),
    "userName" : "qiweb",
    "passWord" : "qiweb",
    "_class" : "com.example.demo.UserEntity"
}
C 运行启动类


 com.example.DemoApplication
 
## 3、linux客户端脚本
   存放在linux-install-shell

## 4、运行效果图
![longin.png](https://raw.githubusercontent.com/QIWEB/linuxServerMonitoring/master/image/longin.png) 
![faban](https://raw.githubusercontent.com/QIWEB/linuxServerMonitoring/master/image/faban.png) 
![yinyongfuwuqi.png](https://raw.githubusercontent.com/QIWEB/linuxServerMonitoring/master/image/yinyongfuwuqi.png) 
![serverinfo.png](https://raw.githubusercontent.com/QIWEB/linuxServerMonitoring/master/image/serverinfo.png) 
![jincheng.png](https://raw.githubusercontent.com/QIWEB/linuxServerMonitoring/master/image/jincheng.png) 
 
## 5、发布项目到ｌｉｎｕｘ服务器
### 前台编译
ｃｍｄ到db-service-monitoring执行
npm run build
得到ｄｉｓｔ就是编译后的前台系统文件
### 后台编译
mvn clean package
获得一个ｗａｒ包
## 技术讨论QQ微信 908701702
