#!/bin/bash
# author:hwl
# this is test for geting datas from server
# 获取本机IP地址
ipaddr=$(ip addr | grep 'state UP' -A2 | tail -n1 | awk '{print $2}' | cut -d '/' -f1)
echo "  <-----------------ip address is ---------------->  "$ipaddr
# 服务默认状态未启动
status=0
# 请求接口的地址
url="192.168.0.41:9999/process/getDatas"

# 请求接口的地址  更新状态 停止和启动
urlSuccess="192.168.0.41:9999/process/updateMessage"
# 轮询服务端 获取返回的IP地址和本机IP地址匹配 以及对服务器要进行的操作（重启还是停止）
#curl -i -X POST -H "'Content-type':'application/json'" -d '{"ATime":"'$atime'","BTime":"'$btime'"}' $url
result=$(curl -d "ipaddr=$ipaddr" $url)

echo "go--------------------------start------------------------end  "$result

# 定义服务名称
tomcat_name="tomcat"

zookeeper_name="zookeeper"

mongodb_name="mongodb"

nginx_name="nginx"

redis_name="redis"

vertx_name="vertx"

mysql_name="mysql"

# 定义一个装服务名称的数组
array_service_name[0]=$tomcat_name
array_service_name[1]=$zookeeper_name
array_service_name[2]=$mongodb_name
array_service_name[3]=$nginx_name
array_service_name[4]=$redis_name
array_service_name[5]=$vertx_name
array_service_name[6]=$mysql_name




# 获取json对象属性
#test=jq '.nginx' $result
#test=$result | jq '.redis'
echo $result > json.json
# 获取redis服务
redis_status=$(cat json.json | jq '.redis')
echo $redis_status

# 获取tomcat服务
tomcat_status=$(cat json.json | jq '.tomcat')
echo $tomcat_status

# 获取nginx服务
nginx_status=$(cat json.json | jq '.nginx')
echo $nginx_status

# 获取mongodb服务
mongodb_status=$(cat json.json | jq '.mongodb')
echo $mongodb_status

# 获取zookeeper服务
zookeeper_status=$(cat json.json | jq '.zookeeper')
echo $zookeeper_status

# 获取vertx服务
vertx_status=$(cat json.json | jq '.vertx')
echo $vertx_status

# 获取mysql服务
mysql_status=$(cat json.json | jq '.mysql')
echo $mysql_status
# 定义一个数组 将解析好的状态内容放入到数组中
# status_array=()
# for name in ${array_service_name[@]}  
# do  
#     echo cat json.json | jq '.${name}'  
# done
#  #for name in ${array_service_name[@]}
#  for (( i = 0; i < ${array_service_name[@]}; ++i )); 
#   do
#           echo ${array_service_name[$i]}
#           name='.'${array_service_name[$i]}
#           echo $name
#           echo $(cat json.json | jq "$name")
#           status_array[$i] = $(cat json.json | jq "$name")
#   done
echo "暂停2秒--"
sleep 2s
echo "暂停结束--"

#  定义状态码  3-----正在启动中     4------正在停止中
starting=3
stoping=4
echo $starting
echo $stoping

# 获取tomcat服务
echo "------------进入tomcat服务操作项start-------------------"

echo $tomcat_status
echo $tomcat_status | cut -d '"' -f2
echo $tomcat_status| cut -d '"' -f2  > aaa.txt
AA=$(cat aaa.txt) 
echo $AA

pid_tomcats=$(ps -ef|grep tomcat|grep -v grep|awk '{print$2}')
echo "[tomcat进程id]  "$pid_tomcats
	#说明服务运行中
	# 进程不为空
if [ $pid_tomcats ];
	#----------------------------
	then

			for pid_tomcat in $pid_tomcats
			do	
			 if  [ $pid_tomcat ];
			    then
					 # 如果==4 就需要停止服务
					 if [ $AA = $stoping ];
					 	then
					 	    kill -9 $pid_tomcat  
					 	    echo "tomcat服务停止"
					 	    # 停顿5秒钟
							sleep 5s
					 	    # 停止后 并向远程服务器发送请求 告知服务器服务停止成功
					 	    curl -d "ipaddr=$ipaddr&name=$tomcat_name&status=7" $urlSuccess
					 else
			   				echo "没有符合的条件--------------"
					 fi	    
			    else 
					   #如果==3 就需要启动服务
					   if [ $AA = $starting ];
					 	then
					 	    # 启动服务
					 	    echo "tomcat服务启动中"
					 	    cd /tools/tomcat/apache-tomcat-8.5.29/bin
					 	    # cd /acme_dbgo/tomcat8/apache-tomcat-8.5.29/bin/
							./startup.sh
							# 停顿5秒钟
							sleep 5s
							curl -d "ipaddr=$ipaddr&name=$tomcat_name&status=6" $urlSuccess
					  else
			             echo "没有符合的条件"
			          fi
			 fi
			done
#-----# 进程为空 就需要启动
else
		#如果==3 就需要启动服务
					   if [ $AA = $starting ];
					 	then
					 	    # 启动服务
					 	    echo "tomcat服务启动中"
					 	    cd /acme_dbgo/tomcat/apache-tomcat-8.5.29/bin
					 	    # cd /acme_dbgo/tomcat8/apache-tomcat-8.5.29/bin/
							./startup.sh
							# 停顿5秒钟
							sleep 5s
							curl -d "ipaddr=$ipaddr&name=$tomcat_name&status=6" $urlSuccess
					  else
			             echo "没有符合的条件"
			          fi
		
fi
echo "------------进入tomcat服务操作项end-------------------"















# 获取redis服务
echo "------------进入redis服务操作项start-------------------"
echo $redis_status
echo $redis_status | cut -d '"' -f2
echo $redis_status| cut -d '"' -f2  > aaa.txt
BB=$(cat aaa.txt) 
echo $BB

pid_rediss=$(ps -ef|grep redis|grep -v grep|awk '{print$2}')
echo "[redis进程id]  "$pid_rediss
#  当进程id有多个时候的需要循环遍历  遍历kill  进程
if [ $pid_rediss ];
	#----------------------------
	then
		for pid_redis in $pid_rediss
		do
		 if [ $pid_redis ];
		   then
				 # 如果==4 就需要停止服务
				 if [ $BB = $stoping ];
				 	then
				 	    kill -9 $pid_redis  
				 	    echo "redis服务停止"
				 	    # 停顿5秒钟
						sleep 5s
				 	    # 停止后 并向远程服务器发送请求 告知服务器服务停止成功
				 	    curl -d "ipaddr=$ipaddr&name=$redis_name&status=7" $urlSuccess
				 else
		   				echo "没有符合的条件--------------"
				 fi	    
		    else 
				   #如果==3 就需要启动服务
				   if [ $BB = $starting ];
				 	then
				 	    # 启动服务
				 	    echo "redis服务启动中"
				 	    # 进入redis服务目录
						cd /acme_dbgo/redis/redis-4.0.8/src/

						# 启动服务
						./redis-server /acme_dbgo/config/config_redis/redis.conf &
						# 停顿5秒钟
						sleep 5s
						curl -d "ipaddr=$ipaddr&name=$redis_name&status=6" $urlSuccess
				  else
		             echo "没有符合的条件"
		          fi
		 fi
		done
else
		 #如果==3 就需要启动服务
				   if [ $BB = $starting ];
				 	then
				 	    # 启动服务
				 	    echo "redis服务启动中"
				 	    # 进入redis服务目录
						cd /acme_dbgo/redis/redis-4.0.8/src/

						# 启动服务
						./redis-server /acme_dbgo/config/config_redis/redis.conf &
						# 停顿5秒钟
						sleep 5s
						curl -d "ipaddr=$ipaddr&name=$redis_name&status=6" $urlSuccess
				  else
		             echo "没有符合的条件"
		          fi

fi		
echo "------------完成redis服务操作项end-------------------"

















# 获取mongodb服务
echo "------------进入mongodb服务操作项start-------------------"


echo $mongodb_status
echo $mongodb_status | cut -d '"' -f2
echo $mongodb_status| cut -d '"' -f2  > aaa.txt
CC=$(cat aaa.txt) 
echo $CC

pid_mongodbs=$(ps -ef|grep mongodb|grep -v grep|awk '{print$2}')
echo "[mongodb进程id]  "$pid_mongodbs
#  当进程id有多个时候的需要循环遍历  遍历kill  进程
if [ $pid_mongodbs ];
	#----------------------------
	then

		for pid_mongodb in $pid_mongodbs
		do
		 if [ $pid_mongodb ];
		   then
				 # 如果==4 就需要停止服务
				 if [ $CC = $stoping ];
				 	then
				 	    kill -9 $pid_mongodb  
				 	    echo "redis服务停止"
				 	    # 停顿5秒钟
						sleep 5s
				 	    # 停止后 并向远程服务器发送请求 告知服务器服务停止成功
				 	    curl -d "ipaddr=$ipaddr&name=$mongodb_name&status=7" $urlSuccess
				 else
		   				echo "没有符合的条件--------------"
				 fi	    
		    else 
				   #如果==3 就需要启动服务
				   if [ $CC = $starting ];
				 	then
				 	    # 启动服务
				 	    echo "redis服务启动中"
				 	    #  启动redis 脚本  TODO


						# 停顿5秒钟
						sleep 5s
						curl -d "ipaddr=$ipaddr&name=$mongodb_name&status=6" $urlSuccess
				  else
		             echo "没有符合的条件"
		          fi
		 fi
		done

else
	 #如果==3 就需要启动服务
				   if [ $CC = $starting ];
				 	then
				 	    # 启动服务
				 	    echo "redis服务启动中"
				 	    #  启动redis 脚本  TODO


						# 停顿5秒钟
						sleep 5s
						curl -d "ipaddr=$ipaddr&name=$mongodb_name&status=6" $urlSuccess
				  else
		             echo "没有符合的条件"
		          fi
fi		
echo "------------完成mongodb服务操作项end-------------------"























# 获取zookeeper服务
echo "------------进入zookeeper服务操作项start-------------------"


echo $zookeeper_status
echo $zookeeper_status | cut -d '"' -f2
echo $zookeeper_status| cut -d '"' -f2  > aaa.txt
DD=$(cat aaa.txt) 
echo $DD

pid_zookeepers=$(ps -ef|grep zookeeper|grep -v grep|awk '{print$2}')
echo "[zookeeper进程id]  "$pid_zookeepers
#  当进程id有多个时候的需要循环遍历  遍历kill  进程
if [ $pid_zookeepers ];
	#----------------------------
	then

		for pid_zookeeper in $pid_zookeepers
		do
		 if [ $pid_zookeeper ];
		   then
				 # 如果==4 就需要停止服务
				 if [ $DD = $stoping ];
				 	then
				 	    kill -9 $pid_zookeeper  
				 	    echo "zookeeper服务停止"
				 	    # 停顿5秒钟
						sleep 5s
				 	    # 停止后 并向远程服务器发送请求 告知服务器服务停止成功
				 	    curl -d "ipaddr=$ipaddr&name=$zookeeper_name&status=7" $urlSuccess
				 else
		   				echo "没有符合的条件--------------"
				 fi	    
		    else 
				   #如果==3 就需要启动服务
				   if [ $DD = $starting ];
				 	then
				 	    # 启动服务
				 	    echo "zookeeper服务启动中"
				 	    # 进入zookeeper服务目录
						cd /acme_dbgo/zookeeper/zookeeper-3.4.10/bin/

						# 启动服务
						./zkServer.sh start ../conf/zoo.cfg &

						# 停顿5秒钟
						sleep 5s
						curl -d "ipaddr=$ipaddr&name=$zookeeper_name&status=6" $urlSuccess
				  else
		             echo "没有符合的条件"
		          fi
		 fi
		done

else
	 #如果==3 就需要启动服务
				   if [ $DD = $starting ];
				 	then
				 	    # 启动服务
				 	    echo "zookeeper服务启动中"
				 	    #  启动zookeeper 脚本  TODO
						# 进入zookeeper服务目录
						cd /acme_dbgo/zookeeper/zookeeper-3.4.10/bin/

						# 启动服务
						./zkServer.sh start ../conf/zoo.cfg &

						# 停顿5秒钟
						sleep 5s
						curl -d "ipaddr=$ipaddr&name=$zookeeper_name&status=6" $urlSuccess
				  else
		             echo "没有符合的条件"
		          fi
fi		
echo "------------完成zookeeper服务操作项end-------------------"

















# 获取nginx服务
echo "------------进入nginx服务操作项start-------------------"


echo $nginx_status
echo $nginx_status | cut -d '"' -f2
echo $nginx_status| cut -d '"' -f2  > aaa.txt
DD=$(cat aaa.txt) 
echo $DD

pid_nginxs=$(ps -ef|grep nginx|grep -v grep|awk '{print$2}')
echo "[nginx进程id]  "$pid_nginxs
#  当进程id有多个时候的需要循环遍历  遍历kill  进程
 if [ $pid_nginxs ];
   then

		for pid_nginx in $pid_nginxs
		do
		 if [ $pid_nginx ];
		   then
				 # 如果==4 就需要停止服务
				 if [ $DD = $stoping ];
				 	then
				 	    kill -9 $pid_nginx  
				 	    echo "zookeeper服务停止"
				 	    # 停顿5秒钟
						sleep 5s
				 	    # 停止后 并向远程服务器发送请求 告知服务器服务停止成功
				 	    curl -d "ipaddr=$ipaddr&name=$nginx_name&status=7" $urlSuccess
				 else
		   				echo "没有符合的条件--------------"
				 fi	    
		    else 
				   #如果==3 就需要启动服务
				   if [ $DD = $starting ];
				 	then
				 	    # 启动服务
				 	    echo "nginx服务启动中"
				 	    #  启动zookeeper 脚本  TODO
						# 进入nginx 服务目录
						cd /acme_dbgo/ngnix/nginxStart/sbin/

						# 启动服务
						./nginx

						# 停顿5秒钟
						sleep 5s
						curl -d "ipaddr=$ipaddr&name=$nginx_name&status=6" $urlSuccess
				  else
		             echo "没有符合的条件"
		          fi
		 fi
		done

else
	#如果==3 就需要启动服务
				   if [ $DD = $starting ];
				 	then
				 	    # 启动服务
				 	    echo "nginx服务启动中"
				 	    #  启动zookeeper 脚本  TODO
						# 进入nginx 服务目录
						cd /acme_dbgo/ngnix/nginxStart/sbin/

						# 启动服务
						./nginx

						# 停顿5秒钟
						sleep 5s
						curl -d "ipaddr=$ipaddr&name=$nginx_name&status=6" $urlSuccess
				  else
		             echo "没有符合的条件"
		          fi
fi
echo "------------完成nginx服务操作项end-------------------"
















# 获取vertx服务
echo "------------进入vertx服务操作项start-------------------"


echo $vertx_status
echo $vertx_status | cut -d '"' -f2
echo $vertx_status| cut -d '"' -f2  > aaa.txt
EE=$(cat aaa.txt) 
echo $EE

pid_vertxs=$(ps -ef|grep vertx|grep -v grep|awk '{print$2}')
echo "[vertx进程id]  "$pid_vertxs
#  当进程id有多个时候的需要循环遍历  遍历kill  进程
 if [ $pid_vertxs ];
   then

		for pid_vertx in $pid_vertxs
		do
		 if [ $pid_vertx ];
		   then
				 # 如果==4 就需要停止服务
				 if [ $EE = $stoping ];
				 	then
				 	    kill -9 $pid_vertx  
				 	    echo "vertx服务停止"
				 	    # 停顿5秒钟
						sleep 5s
				 	    # 停止后 并向远程服务器发送请求 告知服务器服务停止成功
				 	    curl -d "ipaddr=$ipaddr&name=$vertx_name&status=7" $urlSuccess
				 else
		   				echo "没有符合的条件--------------"
				 fi	    
		    else 
				   #如果==3 就需要启动服务
				   if [ $EE = $starting ];
				 	then
				 	    # 启动服务
				 	    echo "vertx服务启动中"
				 	    # 进入vretx服务目录
						cd /acme_dbgo/vertx/vertx-server-win-test/bin/

						# 启动服务
						./vertx_local.sh


						# 停顿5秒钟
						sleep 5s
						curl -d "ipaddr=$ipaddr&name=$vertx_name&status=6" $urlSuccess
				  else
		             echo "没有符合的条件"
		          fi
		 fi
		done

else
	if [ $EE = $starting ];
				 	then
				 	    # 启动服务
				 	    echo "vertx服务启动中"
				 	    # 进入vretx服务目录
						cd /acme_dbgo/vertx/vertx-server-win-test/bin/

						# 启动服务
						./vertx_local.sh

						# 停顿5秒钟
						sleep 5s
						curl -d "ipaddr=$ipaddr&name=$vertx_name&status=6" $urlSuccess
				  else
		             echo "没有符合的条件"
		          fi
fi		
echo "------------完成vertx服务操作项end-------------------"




















# 获取mysql服务
echo "------------进入mysql服务操作项start-------------------"


echo $mysql_status
echo $mysql_status | cut -d '"' -f2
echo $mysql_status| cut -d '"' -f2  > aaa.txt
FF=$(cat aaa.txt) 
echo $FF

pid_mysqls=$(ps -ef|grep mysql|grep -v grep|awk '{print$2}')
echo "[mysql进程id]  "$pid_mysqls
#  当进程id有多个时候的需要循环遍历  遍历kill  进程
 if [ $pid_mysqls ];
   then

		for pid_mysql in $pid_mysqls
		do
		 if [ $pid_mysql ];
		   then
				 # 如果==4 就需要停止服务
				 if [ $FF = $stoping ];
				 	then
				 	    kill -9 $pid_mysqls  
				 	    echo "mysql服务停止"
				 	    # 停顿5秒钟
						sleep 5s
				 	    # 停止后 并向远程服务器发送请求 告知服务器服务停止成功
				 	    curl -d "ipaddr=$ipaddr&name=$mysql_name&status=7" $urlSuccess
				 else
		   				echo "没有符合的条件--------------"
				 fi	    
		    else 
				   #如果==3 就需要启动服务
				   if [ $FF = $starting ];
				 	then
				 	    # 启动服务
				 	    echo "mysql服务启动中"
				 	    #  启动mysql 脚本  TODO


						# 停顿5秒钟
						sleep 5s
						curl -d "ipaddr=$ipaddr&name=$mysql_name&status=6" $urlSuccess
				  else
		             echo "没有符合的条件"
		          fi
		 fi
		done

else
		 #如果==3 就需要启动服务
				   if [ $FF = $starting ];
				 	then
				 	    # 启动服务
				 	    echo "mysql服务启动中"
				 	    #  启动mysql 脚本  TODO


						# 停顿5秒钟
						sleep 5s
						curl -d "ipaddr=$ipaddr&name=$mysql_name&status=6" $urlSuccess
				  else
		             echo "没有符合的条件"
		          fi
fi		
echo "------------完成mysql服务操作项end-------------------"










# # 获取nginx服务
# num_nginx=0
# pid_nginxs=$(ps -ef|grep nginx|grep -v grep|awk '{print$2}')
# echo "nginx进程id  "$pid_nginxs
#  当进程id有多个时候的需要循环遍历  遍历kill  进程
# for pid_nginx in $pid_nginxs
# do
#  if test $[pid_nginx] -gt $[num_nginx]
#     then
#          #kill -9 $pid_nginx echo "nginx服务停止"
#          # kill -9 $pid_nginx echo "nginx服务停止"
# 		 # if redis server is running so you can send message to romete server
# 		 echo "---------------------nginx 服务运行中-------------------------"
# 	# to do something .....
# 	sleep 1s
#     else 
#     	 #echo "nginx服务未启动。。。"
#     	 echo "nginx 服务未启动或者没有该服务。。。"
#     	 # to do something ....
#  fi
# done









#可参照：http://www.voidcn.com/blog/Vindra/article/p-4917667.html
#一、get请求 
#curl "http://www.baidu.com"  如果这里的URL指向的是一个文件或者一幅图都可以直接下载到本地
#curl -i "http://www.baidu.com"  显示全部信息
#curl -l "http://www.baidu.com" 只显示头部信息
#curl -v "http://www.baidu.com" 显示get请求全过程解析
#wget "http://www.baidu.com"也可以
#二、post请求
#curl -d "param1=value1&param2=value2" "http://www.baidu.com"
#三、json格式的post请求
#curl -l -H "Content-type: application/json" -X POST -d '{"phone":"13521389587","password":"test"}' http://domain/apis/users.json
#例如：
#curl -l -H "Content-type: application/json" -X POST -d '{"ver": "1.0","soa":{"req":"123"},"iface":"me.ele.lpdinfra.prediction.service.PredictionService","method":"restaurant_make_order_time","args":{"arg2":"\"stable\"","arg1":"{\"code\":[\"WIND\"],\"temperature\":11.11}","arg0":"{\"tracking_id\":\"100000000331770936\",\"eleme_order_id\":\"100000000331770936\",\"platform_id\":\"4\",\"restaurant_id\":\"482571\",\"dish_num\":1,\"dish_info\":[{\"entity_id\":142547763,\"quantity\":1,\"category_id\":1,\"dish_name\":\"[0xe7][0x89][0xb9][0xe4][0xbb][0xb7][0xe8][0x85][0x8a][0xe5][0x91][0xb3][0xe5][0x8f][0x89][0xe7][0x83][0xa7][0xe5][0x8f][0x8c][0xe6][0x8b][0xbc][0xe7][0x85][0xb2][0xe4][0xbb][0x94][0xe9][0xa5][0xad]\",\"price\":31.0}],\"merchant_location\":{\"longitude\":\"121.47831425\",\"latitude\":\"31.27576153\"},\"customer_location\":{\"longitude\":\"121.47831425\",\"latitude\":\"31.27576153\"},\"created_at\":1477896550,\"confirmed_at\":1477896550,\"dishes_total_price\":0.0,\"food_boxes_total_price\":2.0,\"delivery_total_price\":2.0,\"pay_amount\":35.0,\"city_id\":\"1\"}"}}' http://vpcb-lpdinfra-stream-1.vm.elenet.me:8989/rpc
#ps：json串内层参数需要格式化
# 打印最后一行  sed -n '$p' txt.txt|awk '{print $0}'
# 打印第一行    sed -n '1p' txt.txt|awk '{print $0}'
# 打印IP信息    echo $(ip addr | grep 'state UP' -A2 | tail -n1 | awk '{print $2}' | cut -f1 -d '/')








