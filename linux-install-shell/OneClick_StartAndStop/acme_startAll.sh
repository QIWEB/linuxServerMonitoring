#   依次开启各个服务  Author by huangweilin  --   2018-04-17 

#   #    #########################################    #    #

#                  重新开启服务器  针对ip: 192.168.0.73                        #

#   #    ########################################     #    #

#--------------------------  1.启动redis服务

# 进入redis服务目录
cd /tools/redis/redis-4.0.8/src/

# 启动服务
./redis-server /tools/config_redis/redis.conf &




# --------------------------2.启动zookeeper服务

# 进入zookeeper服务目录
cd /tools/zookeeper/zookeeper-3.4.10/bin/

# 启动服务
./zkServer.sh start ../conf/zoo.cfg &




# --------------------------3.启动mongodb服务

# 进入mongodb服务目录
cd /tools/mongodb/mongodb-linux-x86_64-rhel70-3.6.3/bin/

# 启动服务
./mongod --config /tools/config_mongodb/mongodb.conf &





# -------------------------- 4.启动vicarServer服务

# 进入vacar服务目录
cd /tools/vacar/

# 启动服务
./start.sh &


#------------------------------- 5. 启动nginx服务

# 进入nginx 服务目录
#cd /tools/ngnix/nginxStart/sbin/

# 启动服务
#./nginx

#------------------------------- 6.启动tomcat服务

# 进入tomcat 服务目录
cd /tools/tomcat/apache-tomcat-8.5.29/bin/

# 启动服务
./startup.sh &

#------------------------------- 7.启动tomcat服务

# 进入图片tomcat 服务目录
cd /tools/tomcat/pic-server/bin/

# 启动服务
./startup.sh &

# ------------------------------ 8.启动vertx服务

# 进入vretx服务目录
cd /tools/vertx/vertx-server-win-test/bin/

# 启动服务
./vertx_local.sh &































