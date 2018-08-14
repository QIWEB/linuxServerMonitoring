# 定时任务发版脚本
# 逻辑：定时像中央服务器发送请求，并对返回结果进行判断

FABANMA="发版吗？"
url="192.168.0.41:9999/xx/50";
#downloadUrl="http://11.24.201.36:9090/ky_test/acme.war"
echo $FABANMA
echo "地址--------------->"$url
#curl -i -X POST -H "'Content-type':'application/json'" -d '{"ATime":"'$atime'","BTime":"'$btime'"}' $url
result=$(curl -d "FABANMA='$FABANMA'" $url)
#将结果写道一个文件中去
echo $result|awk 'NF{a=$0}END{print a}' >> xx.txt
#获取结果最后一行 一般结果是中央服务器返回的数据结果 OK/notOK
OK_notOK=$(awk 'NF{a=$0}END{print a}' xx.txt|cut -d " " -f5)
echo "OK_notOK的结果是----------------->"$OK_notOK
# 逻辑判断是否要发版
if [ "OK" == $OK_notOK ]
then	
	echo "要发版，请注意发版！！！"
		# 发版脚本  目前只停止tomcat进程 
		#  1.停止tomcat
		echo "停止tomcat服务开始。。。。。"
		num_tomcat=0

		pid_tomcats=$(ps -ef|grep tomcat|grep -v grep|awk '{print$2}')

		echo "tomcat进程id  "$pid_tomcats

		#  当进程id有多个时候的需要循环遍历  遍历kill  进程
		for pid_tomcat in $pid_tomcats
		do
		 if test $[pid_tomcat] -gt $[num_tomcat]
		    then
		         kill -9 $pid_tomcat echo "tomcat服务停止"
		    else echo "tomcat服务未启动。。。"
		 fi
		done
		# 切换目录到webapps
		# 2.备份linux服务器acme.war包  并删除原来的acme*
		cd /tools/acme_backup
		zip -r acme_$(date +"%Y-%m-%d-%H-%M").zip /tools/tomcat/apache-tomcat-8.5.29/webapps/acme/

		cd /tools/tomcat/apache-tomcat-8.5.29/webapps

		echo "压缩文件等待2秒钟.................."

		sleep 2s

		rm -rf acme*


		# 3.下载acme.war包  ky地址是 http://11.24.201.36:9090/ky/acme.war

		mkdir acme 
		cd /tools/tomcat/apache-tomcat-8.5.29/webapps/acme
		#wget http://11.24.201.36:8080/ky/acme.war

		echo "-------------downloading start---------------"

		wget http://192.168.0.41:8080/bs/acme.war

		echo "------------downloading  end-----------------"
		#cp acme.war /tools/tomcat/apache-tomcat-8.5.29/webapps
		mv acme.war acme.zip
		unzip acme.zip

		echo "解压文件等待2秒钟.................."

		sleep 2s

		rm -rf acme.zip

		# 4.启动tomcat解压acme.war包  并等待5秒 并停止tomcat进程
		cd /tools/tomcat/apache-tomcat-8.5.29/bin
		./startup.sh

		# 5. 停止tomcat 
		echo "tomcat服务启动等待10秒钟.................."

		sleep 10s


		# 停止tomcat
		echo "停止tomcat服务开始。。。。。"
		num_tomcatt=0

		pid_tomcatts=$(ps -ef|grep tomcat|grep -v grep|awk '{print$2}')

		echo "tomcatt进程id  "$pid_tomcatts

		#  当进程id有多个时候的需要循环遍历  遍历kill  进程
		for pid_tomcatt in $pid_tomcatts
		do
		 if test $[pid_tomcatt] -gt $[num_tomcatt]
		    then
		         kill -9 $pid_tomcatt echo "tomcat服务停止"
		    else echo "tomcat服务未启动。。。"
		 fi
		done

		echo "停止tomcat服务等待5s----------------------------------"

		sleep 5s


		# 6. 删掉之前的配置文件
		cd /tools/tomcat/apache-tomcat-8.5.29/webapps/acme/WEB_INF/classes/
		rm -rf application-test.properties*


		# 7. 替换原来的war中的配置文件  (2个文件)

		cd /tools/tomcat/apache-tomcat-8.5.29/webapps/serverSetting

		cp application-test.properties* /tools/tomcat/apache-tomcat-8.5.29/webapps/acme/WEB-INF/classes/


		# 8. 启动tomcat 
		cd /tools/tomcat/apache-tomcat-8.5.29/bin
		nohup ./startup.sh &


		# 9. 查看启动日志
		tail -f /tools/tomcat/apache-tomcat-8.5.29/logs/catalina.out

elif [ "notOK" == $OK_notOK ]	
then	
	echo "不要发版，请注意不要发版！！！"
	exit;
else
    echo "没有符合的条件"
    # 退出当前shell脚本
    exit;
fi		


