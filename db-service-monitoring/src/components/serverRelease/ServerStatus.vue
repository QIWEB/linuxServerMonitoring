<template>
  <div>
    <span class="span_update">服务器数据在<span style="color: red;">{{timingNumber}}</span>秒后进行下一次更新</span>
    <div class="server_details">
      <div class="details_text">
        <label class="label_text">系统运行时间:</label><label>{{serverDetails.uptime}}天</label>
        <br>
        <label class="label_text">cpu模型:</label><label>{{serverDetails.cpuName}}</label>
        <br>
        <label class="label_text">操作系统:</label><label>{{serverDetails.osName}}</label>
        <br>
        <label class="label_text">ip地址:</label><label>{{serverDetails.ip}}</label>
        <br>
        <label class="label_text">cpu速度:</label><label>{{serverDetails.cpuFreq}}</label>
      </div>
      <div class="details_text">
        <label class="label_text">核心:</label><label>{{serverDetails.osKernel}}</label>
        <br>
        <label class="label_text">发送流量:</label><label>{{serverDetails.tx}}</label>
        <br>
        <label class="label_text">接收流量:</label><label>{{serverDetails.rx}}</label>
        <br>
        <label class="label_text">连接数:</label><label>{{serverDetails.connections}}</label>
        <br>
        <label class="label_text">网络接口:</label><label>{{serverDetails.nic}}</label>
      </div>
      <div class="details_text">
        <label class="label_text">登录会话计数:</label><label>{{serverDetails.sessions}}</label>
        <br>
        <label class="label_text">进程计数:</label><label>{{serverDetails.processes}}</label>
      </div>

    </div>
    <div class="select_server">
      <div class="label_server">
        服务器选择：
        <el-select v-model="serve_value" placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </div>
      <div class="label_server">
        <span class="demonstration">日期:</span>
        <el-date-picker
          v-model="YTD"
          type="date"
          placeholder="选择日期" style="width: 140px">
        </el-date-picker>
      </div>
      <div class="label_server">
        开始时间:
        <el-time-select
          v-model="start_time_value"
          :picker-options="{
              start: '00:00',
              step: '01:00',
              end: '23:55'
            }"
          placeholder="选择时间" style="width: 120px">
        </el-time-select>
      </div>
      <div class="label_server">
        结束时间:
        <el-time-select
          v-model="finish_time_value"
          :picker-options="{
              start: '00:00',
              step: '01:00',
              end: '23:55'
            }"
          placeholder="选择时间" style="width: 120px">
        </el-time-select>
      </div>

      <div class="label_server">
        <el-button type="primary" icon="el-icon-search" @click="axiosHttp_data_click">查询</el-button>
      </div>

    </div>
    <div id="myChart"></div>

    <div class="service_process">
      <div style="width: 100%;height:30px;border-bottom:solid 3px #e8f3ff;">
        <span style="color:rgb(33,32,32);margin: 3px 0px 0px 3px">服务器必要进程使用情况</span>
        <a @click="seeMore" href="javascript:void(0)"  style="margin-top:3px;float: right;cursor:hand; text-decoration:none;color:rgb(64, 158, 255) ">{{lookOver}}</a>
      </div>
      <el-table
        :data="serviceProcess"
        style="width: 100%">
        <el-table-column
          type="index"
          :index="indexMethod">
        </el-table-column>
        <el-table-column
          prop="name"
          label="进程名"
          width="180">
        </el-table-column>
       <!-- <el-table-column
          prop="count"
          label="进程数量"
          width="180">
        </el-table-column>-->
        <el-table-column
          prop="cpu"
          label="cpu占用">
        </el-table-column>
        <el-table-column
          prop="mMemory"
          label="内存占用">
        </el-table-column>
        <el-table-column
          prop="user"
          label="用户">
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>
<style>
  .span_update{
    margin: 10px 0px 0px 120px;
    font-weight:bold;
    color: rgb(64, 158, 255);
  }
  #myChart {
    width: 100%;
    height: 400px;
    border: solid 3px #e8f3ff;
    margin: 10px 0px 0px 110px;
    border-radius: 10px;
  }

  .select_server {
    width: 100%;
    height: 50px;
    border: solid 3px #e8f3ff;
    margin: 10px 0px 0px 110px;
    border-radius: 10px;
  }

  .server_details{
    width: 100%;
    height: 200px;
    border: solid 3px #e8f3ff;
    margin: 10px 0px 0px 110px;
    border-radius: 10px;
  }
  .service_process{
    width: 100%;
    /*height: 200px;*/
    border: solid 3px #e8f3ff;
    margin: 10px 0px 0px 110px;
    border-radius: 10px;
  }

  .label_server {
    float: left;
    margin: 5px 0px 0px 14px;
  }

  label {
    margin-left: 10px;
  }

  .label_text {line-height:30px;
    color: rgb(64, 158, 255)
  }
  .details_text{
    float: left;
    margin: 10px 0px 0px 10px;
  }
</style>
<script>
  export default {
    name: '',
    data () {
      return {
        disk: [],//硬盘
        ram: [],//内存
        cpu: [],//cpu
        serve_value: this.$overall.serve_value_id(),//服务器选择的值，默认值ip：192.168.0.73的服务器id
        options: [],//服务器选择下拉框数据
        YTD: '',//年月日,默认当天日期
        start_time_value: '',
        finish_time_value: '',
        dataArr: [], //曲线图x轴数据
        timingNumber:'',//定时数字
        timer: null,//存储定时器
        serverDetails:{
          //服务器详情对象,此处为后台数据对象字段注释
         /* uptime:'',//系统运行时间（天）
          cpuName:'',//cpu模型（型号）
          osName:'',//操作系统
          ip:'',//ip地址
          cpuFreq:'',//cpu速度
          osKernel:'',//核心
          tx:'',//发送流量（KB）
          rx:'',//接收流量（KB）
          connections:'',//连接数
          nic:'',//网络接口
          sessions:'',//登录会话计数
          processes:'',//进程计数*/
        },
        serviceProcess:[],//服务器进程信息(全部)
        courseCount:10, //进程数据显示条数
        lookOver:'查看更多' //查看进程数据显示的标题
      }
    },
    mounted () {
      this.refreshData()
    },
    computed:{//vue自带计算函数，提升性能,计算数据时建议在此实现
    },
    methods: {
      refreshData:function () {//刷新数据
        this.countDown()
        this.axiosHttp_ServerOwnership()
        this.axiosHttp_data()
        this.axiosHttp_getServerDetails()
        this.axiosHttp_getServiceProcess()
      },
      seeMore:function () {//进程数据查看更多事件
        this.courseCount=this.courseCount>0?0:10;//触发此方法代表要显示所有数据/部分数据（默认10条）
        this.lookOver=this.lookOver=='查看更多'?'查看部分':'查看更多';
        this.axiosHttp_getServiceProcess()
      },
      indexMethod:function(index) {//生成服务器进程展示表格序号
        return index * 1+1;
      },
      axiosHttp_getServiceProcess:function () {//获取服务器安装服务进程信息
        let _this = this
        let params = new URLSearchParams()
        params.append('token', this.serve_value)
        params.append('count',this.courseCount)
        this.$post(this.$axiosURL.getServiceProcess(),params)
          .then((response) => {
            if (response.success == true) {
              _this.serviceProcess=response.data;
              //console.log('数据:' + JSON.stringify(_this.serviceProcess))
            }
          }).catch(function (error) {
          console.log('数据请求错误：' + error)
        })
      },
      countDown:function(){//倒计时函数
        const TIME_COUNT = this.$overall.time_count();
        if (!this.timer) {
          this.timingNumber = TIME_COUNT;
          this.timer = setInterval(() => {
            if (this.timingNumber > 0 && this.timingNumber <= TIME_COUNT) {
              this.timingNumber--;
            } else {
              clearInterval(this.timer);
              this.timer = null;
              this.refreshData()
            }
          }, 1000)
        }
      },
      axiosHttp_getServerDetails:function () {//获取服务器配置信息（处理计算后）
        let _this = this
        let params = new URLSearchParams()
        params.append('token', this.serve_value)
        this.$post(this.$axiosURL.getServerDetails(),params)
          .then((response) => {
            if (response.success == true) {
                //console.log(response.data);
                console.log("返回数据："+JSON.stringify(response.data));
              _this.serverDetails = response.data;//把后台返回的json对象直接存储到serverDetails对象

              //console.log('ip:' + _this.sd.ip)
            }
          }).catch(function (error) {
          console.log('数据请求错误：' + error)
        })
      },
      axiosHttp_ServerOwnership: function () {//获取服务器选择下拉框数组
        let _this = this
        this.$post(this.$axiosURL.getServerOwnership())
          .then((response) => {
            if (response.success == true) {
              _this.options = response.data
              //console.log('disk:' + response.data)
            }
          }).catch(function (error) {
          console.log('数据请求错误：' + error)
        })
      },
      axiosHttp_data_click:function () {//点击查询按钮加载的曲线图
         this.axiosHttp_getServerDetails()
         this.axiosHttp_getServiceProcess()
        let _this = this//存储当前vue对象
        //处理转换日期为字符串 格式：2018-06-20
        this.YTD=this.YTD==null?new Date():this.YTD;
        let month = this.YTD.getMonth() + 1 < 10 ? '0' + (this.YTD.getMonth() + 1) : this.YTD.getMonth() + 1
        let day = this.YTD.getDate() < 10 ? '0' + this.YTD.getDate() : this.YTD.getDate()
        let date = this.YTD.getFullYear() + '-' + month + '-' + day

        if(this.start_time_value==''){
          //获取当前时间时 往前推1小时 做为开始时间 小时数不够两位补0
          let f_h=Number(this.YTD.getHours())-1< 10 ? '0' + ((Number(this.YTD.getHours())-1).toString()) : Number(this.YTD.getHours())-1;
          let f_m=this.YTD.getMinutes()< 10 ? '0' + this.YTD.getMinutes() : this.YTD.getMinutes();
          this.start_time_value=f_h+":"+f_m;
        }

        if(this.finish_time_value=='') {
          //获取当前时间时分 做为结束时间 小时数不够两位补0
          let s_h = this.YTD.getHours() < 10 ? '0' + this.YTD.getHours() : this.YTD.getHours();
          let s_m = this.YTD.getMinutes() < 10 ? '0' + this.YTD.getMinutes() : this.YTD.getMinutes();
          this.finish_time_value = s_h + ":" + s_m;
        }
        let params = new URLSearchParams()
        params.append('token', this.serve_value)
        params.append('dateTime', date)
        params.append('startTime', this.start_time_value)
        params.append('finishTime', this.finish_time_value)
        this.$post(this.$axiosURL.getUnilizationRatio(), params)
          .then((response) => {
            if (response.success == true) {
              _this.dataArr = response.data.dataArr
              _this.disk = response.data.disk
              _this.ram = response.data.ram
              _this.cpu = response.data.cpu
              this.drawLine()//调用生成曲线图方法
              //console.log('dataArr:' + _this.dataArr)
            } else {
              _this.dataArr = []
              _this.disk = []
              _this.ram = []
              _this.cpu = []
              this.drawLine()//调用生成曲线图方法
            }
          }).catch(function (error) {
          //console.log('数据请求错误：' + error)
        })
      },
      axiosHttp_data: function () {//获取生成曲线图各数据
        // http方式请求后台接口
        // 查看输出数据 可以用 这个  JSON.stringify() 转json
        let _this = this//存储当前vue对象
        //处理转换日期为字符串 格式：2018-06-20
        this.YTD=new Date();
        let month = this.YTD.getMonth() + 1 < 10 ? '0' + (this.YTD.getMonth() + 1) : this.YTD.getMonth() + 1
        let day = this.YTD.getDate() < 10 ? '0' + this.YTD.getDate() : this.YTD.getDate()
        let date = this.YTD.getFullYear() + '-' + month + '-' + day

          //获取当前时间时 往前推1小时 做为开始时间 小时数不够两位补0
          let f_h=Number(this.YTD.getHours())-1< 10 ? '0' + ((Number(this.YTD.getHours())-1).toString()) : (Number(this.YTD.getHours())-1).toString();
          let f_m=this.YTD.getMinutes()< 10 ? '0' + this.YTD.getMinutes() : this.YTD.getMinutes();
          this.start_time_value=f_h+":"+f_m;


          //获取当前时间时分 做为结束时间 小时数不够两位补0
          let s_h = this.YTD.getHours() < 10 ? '0' + this.YTD.getHours() : this.YTD.getHours();
          let s_m = this.YTD.getMinutes() < 10 ? '0' + this.YTD.getMinutes() : this.YTD.getMinutes();
          this.finish_time_value = s_h + ":" + s_m;

        //console.log('时:' + f_h+" 分："+this.YTD.getMinutes())
        let params = new URLSearchParams()
        params.append('token', this.serve_value)
        params.append('dateTime', date)
        params.append('startTime', this.start_time_value)
        params.append('finishTime', this.finish_time_value)
        this.$post(this.$axiosURL.getUnilizationRatio(), params)
          .then((response) => {
            if (response.success == true) {
              //console.log('dataArr:' + response.data.dataArr)
              _this.dataArr = response.data.dataArr
              _this.disk = response.data.disk
              _this.ram = response.data.ram
              _this.cpu = response.data.cpu
              this.drawLine()//调用生成曲线图方法
            } else {
              _this.dataArr = []
              _this.disk = []
              _this.ram = []
              _this.cpu = []
              this.drawLine()//调用生成曲线图方法
            }
          }).catch(function (error) {
          //console.log('数据请求错误：' + error)
        })
      },
      drawLine () {
        let _this = this//存储vue当前对象
        // 基于准备好的dom，初始化echarts实例
        let myChart = this.$echarts.init(document.getElementById('myChart'))
        // 绘制图表
        myChart.setOption({
          title: {
            text: '服务器CPU,磁盘,内存占用情况'
          },
          tooltip: {
            /*trigger: 'axis',*/
            //自定义鼠标悬浮曲线图点详情
            formatter: function (params, ticket, callback) {
              return '时间：' + _this.dataArr[params.dataIndex] + '<br />' +
                params.seriesName + '占用：' + params.value + '%'
            }
          },
          legend: {
            data: ['CPU', '磁盘', '内存']
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          toolbox: {
            feature: {
              saveAsImage: {}
            }
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: this.dataArr
          },
          yAxis: {
            type: 'value',
            axisLabel: {
              show: true,
              interval: 'auto',
              formatter: '{value} %'
            },
            show: true
          },
          series: [
            {
              name: 'CPU',
              type: 'line',
              stack: '百分比',
              data: this.cpu
            },
            {
              name: '磁盘',
              type: 'line',
              stack: '百分比',
              data: this.disk
            },
            {
              name: '内存',
              type: 'line',
              stack: '百分比',
              data: this.ram
            }
          ]
        })
      }
    }
  }
</script>
