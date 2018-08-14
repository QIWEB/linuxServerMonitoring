#   依次停止各个服务  Author by huangweilin  --   2018-04-17 

#   #    #########################################    #    #

#                  强制终止服务器                          #

#   #    ########################################     #    #




#------------------------------------   1.停止tomcat服务器

echo  "停止tomcat服务开始。。。。。"

pid_tomcats=$(ps -ef|grep tomcat|grep -v grep|awk '{print$2}')

echo "tomcat的进程id是  "$pid_tomcats

#  判断一下服务是否还存在   如果存在才去杀进程

num_tomcat=0
for pid_tomcat in $pid_tomcats
do
if test $[pid_tomcat] -gt $[num_tomcat]
   then 
	 kill -9 $pid_tomcat  echo "tomcat服务停止"
   else  echo "tomcat服务未启动。。。。"
fi
done
#--------------------------------------- 2.停止zookeeper服务

echo "停止zookeeper服务开始。。。。"

pid_zookeepers=$(ps -ef|grep zookeeper|grep -v grep|awk '{print$2}')

echo "zookeeper的进程id是  "$pid_zookeepers

#  判断一下服务是否还存在   如果存在才去杀进程

num_zookeeper=0
for pid_zookeeper in $pid_zookeepers
do
if test $[pid_zookeeper] -gt $[num_zookeeper]
   then
         kill -9 $pid_zookeeper  echo "zookeeper服务停止"
   else  echo "zookeeper服务未启动。。。。"
fi
done
#---------------------------------------3.停止redis服务

echo "停止redis服务开始。。。。"

num_redis=0

pid_rediss=$(ps -ef|grep redis|grep -v grep |awk '{print$2}')

echo "redis的进程id是  "$pid_rediss

#  判断一下服务是否还存在   如果存在才去杀进程

for pid_redis in $pid_rediss
do
if test $[pid_redis] -gt $[num_redis]
   then
         kill -9 $pid_redis  echo "redis服务停止"
   else  echo "redis服务未启动。。。。"
fi
done
#---------------------------------------4.停止mongodb服务
echo "停止mongodb服务"

num_mongodb=0

pid_mongodbs=$(ps -ef|grep mongodb|grep -v grep|awk '{print$2}')

echo "mongodb的进程id是  "$pid_mongodbs
for pid_mongodb in $pid_mongodbs
do
if test $[pid_mongodb] -gt $[num_mongodb]
   then 
	kill -9 $pid_mongodb echo "mongodb服务停止"
   else echo "mongodb服务未启动。。。"
fi
done
#---------------------------------------5.停止vacar服务
echo "停止vacar服务"

num_vacar=0

pid_vacars=$(ps -ef|grep VicaServer|grep -v grep|awk '{print$2}')

echo "vacar进程id  "$pid_vacars

#  当进程id有多个时候的需要循环遍历  遍历kill  进程

for pid_vacar in $pid_vacars
do
 if test $[pid_vacar] -gt $[num_vacar]
    then
         kill -9 $pid_vacar echo "vertx服务停止"
    else echo "vertx服务未启动。。。"
 fi
done

#--------------------------------------6.停止vertx服务
echo "停止vertx服务"

num_vertx=0

pid_vertxs=$(ps -ef|grep vertx|grep -v grep|awk '{print$2}')

echo "vertx进程id  "$pid_vertxs

#  当进程id有多个时候的需要循环遍历  遍历kill  进程

for pid_vertx in $pid_vertxs
do
 if test $[pid_vertx] -gt $[num_vertx]
    then
         kill -9 $pid_vertx echo "vertx服务停止"
    else echo "vertx服务未启动。。。"
 fi
done

#-------------------------------------7.停止nginx服务

echo "停止nginx服务"

num_nginx=0

pid_nginxs=$(ps -ef|grep nginx|grep -v grep|awk '{print$2}')

echo "nginx进程id  "$pid_nginxs

#  当进程id有多个时候的需要循环遍历  遍历kill  进程

for pid_nginx in $pid_nginxs
do
 if test $[pid_nginx] -gt $[num_nginx]
    then
         kill -9 $pid_nginx echo "nginx服务停止"
    else echo "nginx服务未启动。。。"
 fi
done














