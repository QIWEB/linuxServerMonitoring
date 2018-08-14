<template>
  <div>
    <span class="span_update">服务器数据在<span style="color: red;">{{timingNumber}}</span>秒后进行下一次更新</span>
    <div class="screening_conditions ">
      <div class="service_selection">
        服务器选择:
        <el-select v-model="serve_value" placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </div>
      <div class="service_selection">
        <el-col class="el-col-select">
          服务进程名:
          <el-input style="width: 170px;" placeholder="请选输入服务进程名" v-model="course_name"></el-input>
        </el-col>
      </div>
      <div class="service_selection">
        服务进程状态:
        <el-select v-model="course_value" placeholder="请选择">
          <el-option
            v-for="item in course_options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </div>
      <div class="service_selection">
        <el-button type="primary" icon="el-icon-search" @click="axiosHttp_data">查询</el-button>
      </div>
    </div>
    <div class="batch_update">
      选择服务进程状态:
      <el-select v-model="update_course_value" placeholder="请选择">
        <el-option
          v-for="item in course_options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <el-button type="primary" icon="el-icon-edit" @click="setstate">批量更新服务进程状态</el-button>
    </div>
    <!--表格数据-->
    <div class="left-data">
      <el-table
        :data="tableData"
        border
        type="index"
        style="width: 100%"
        @selection-change="handleSelectionChange">
        <el-table-column
          fixed=""
          type="selection"
          label="全选"
          width="100">
        </el-table-column>

        <el-table-column
          fixed
          prop="name"
          label="服务进程名"
          width="200">
        </el-table-column>
        <el-table-column
          prop="ip"
          label="所属服务器ip"
          width="200">
        </el-table-column>
        <el-table-column
          prop="status"
          label="服务进程状态(启动/终止)"
          width="200">
        </el-table-column>
        <el-table-column
          prop="remarks"
          label="备注"
          width="200">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="200">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row)" type="primary" icon="el-icon-edit" circle></el-button>
            <el-button @click="deleteClick(scope.row)" type="danger" icon="el-icon-delete" circle></el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!--编辑数据模态框-->
    <div class="edit-data">
      <el-dialog
        title="编辑服务进程"
        :visible.sync="edit.dialogVisible"
        width="30%"
        :before-close="handleClose">
        <el-col class="el-col-edit">
          服务器进程名：
          <el-input v-model="edit.edit_name" placeholder="请输入服务器进程名"></el-input>
        </el-col>
        <el-col class="el-col-edit">
          所属服务器ip：
          <el-input v-model="edit.edit_ip" placeholder="请输入服务器ip"></el-input>
        </el-col>
        <el-col class="el-col-edit">
          服务器状态：
          <el-select v-model="edit.serve_value" placeholder="请选择">
            <el-option
              v-for="item in course_options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col class="el-col-edit">
          备注：
          <el-input v-model="edit.edit_remarks" placeholder="请输入备注"></el-input>
        </el-col>
        <span slot="footer" class="dialog-footer">
    <!--<el-button @click="dialogVisible = false">取 消</el-button>-->
    <el-button class="el-button-confirm" type="primary" @click="preserve">保存</el-button>
      </span>
      </el-dialog>
    </div>

  </div>
</template>
<style>

  .left-data {
    width: 100%;
    margin-left: 110px;
    margin-top: 5px;
  }

  .screening_conditions {
    width: 99.8%;
    height: 60px;
    border: solid 1px #e8f3ff;
    margin: 10px 0px 0px 110px;
  }

  .service_selection {
    float: left;
    margin: 10px 0px 0px 5px;
  }

  .batch_update {
    margin: 5px 0px 0px 110px;
  }
  .span_update{
    margin: 10px 0px 0px 120px;
    font-weight:bold;
    color: rgb(64, 158, 255);
  }
</style>
<script>
  export default {
    data () {
      return {
        tableData: [],
        multipleSelection: [], //勾选的数据
        options: [], //服务器选择下拉框数据
        timingNumber:'',//定时数字
        timer: null,//存储定时器
        serve_value: this.$overall.serve_value_ip(),//服务器选择的值，默认值ip：192.168.0.73
        course_name: '',//进程名，查询条件使用
        update_course_value: '',//进程名 ，批量更新进程状态使用
        course_options: [
          {'value': '0', 'label': '无操作'},
          {'value': '1', 'label': '启动进程服务'},
          {'value': '2', 'label': '终止进程服务'},
          {'value': '5', 'label': '异常'},
        ],//服务进程状态下拉框
        course_value: '',//服务状态选择值
        edit: {//编辑服务进程属性
          dialogVisible: false,//编辑模态框开关
          edit_name: '',
          edit_ip: '',
          serve_value: '',
          edit_remarks: '',
          edit_token: ''
        }

      }
    },
    mounted () {//页面加载初始化执行函数
      this.refreshData()
    },
    methods: {//页面所有方法声明区域
      refreshData: function () {//刷新数据
        this.countDown()
        this.axiosHttp_ServerOwnership()
        this.axiosHttp_data()
      },
      countDown: function () {//倒计时函数
        const TIME_COUNT = this.$overall.time_count();
        if (!this.timer) {
          this.timingNumber = TIME_COUNT
          this.timer = setInterval(() => {
            if (this.timingNumber > 0 && this.timingNumber <= TIME_COUNT) {
              this.timingNumber--
            } else {
              clearInterval(this.timer)
              this.timer = null
              this.refreshData()
            }
          }, 1000)
        }
      },
      preserve: function () {//保存编辑
        let _this = this
        //验证参数非空
        if (this.edit.edit_name == '' || this.edit.edit_ip == '' || this.edit.serve_value == '') {
          this.$message({
            type: 'warning',
            message: '请完整填写服务进程信息！'
          })
          return
        }
        //封装参数
        let params = {}
        params.token = this.edit.edit_token
        params.name = this.edit.edit_name
        params.ip = this.edit.edit_ip
        params.status = this.edit.serve_value
        params.remarks = this.edit.edit_remarks
        //console.log('参数数据：' + JSON.stringify(params))
        this.$post(this.$axiosURL.updateServiceProcess(), params)
          .then((response) => {
            if (response.success == true) {
              _this.edit.dialogVisible = false //关闭编辑窗口
              _this.axiosHttp_data()//刷新数据
              _this.$message({
                type: 'success',
                message: '更新成功!'
              })
            } else {
              _this.$message({
                type: 'info',
                message:response.msg
              })
            }
          }).catch(function (error) {
          _this.$message({
            type: 'warning',
            message: '请求错误！'
          })
        })
      },
      axiosHttp_batch_update: function () {//批量更改服务进程状态
        let _this = this
        //封装参数
        var params = {}
        params.processState = _this.update_course_value
        params.serviceProcessList = _this.multipleSelection
        this.$post(this.$axiosURL.batchUpdate(), params)
          .then((response) => {
            if (response.success == true) {
              _this.axiosHttp_data()//刷新数据
              _this.$message({
                type: 'success',
                message: '更新成功!'
              })
            } else {
              _this.$message({
                type: 'info',
                message: response.msg
              })
            }
          }).catch(function (error) {
          _this.$message({
            type: 'warning',
            message: '请求错误！' + error
          })
        })
      },
      axiosHttp_ServerOwnership: function () {//获取服务器选择下拉框数组
        let _this = this
        this.$post(this.$axiosURL.getServerIp())
          .then((response) => {
            if (response.success == true) {
              _this.options = response.data
              //console.log('disk:' + response.data)
            }
          }).catch(function (error) {
          console.log('数据请求错误：' + error)
        })
      },
      axiosHttp_data: function () {//请求加载列表数据
        let _this = this
        let params = {}//封装查询参数
        params.ip = _this.serve_value
        params.name = _this.course_name
        params.status = _this.course_value
        this.$post(this.$axiosURL.getServiceProcessList(), params)
          .then((response) => {
            if (response.success == true) {
              _this.transitionState(response.data)
              //console.log('disk:' + response.data)
            }
          }).catch(function (error) {
          console.log('数据请求错误：' + error)
        })
      },
      transitionState: function (responseData) {//转换数据值
        for (let item of responseData) {
          if (item.status == '0') {
            item.status = '无操作'
          } else if (item.status == '1') {
            item.status = '启动进程服务'
          } else if (item.status == '2') {
            item.status = '终止进程服务'
          } else if (item.status == '3') {
            item.status = '正在启动进程服务...'
          } else if (item.status == '4') {
            item.status = '正在终止进程服务...'
          } else if (item.status == '5') {
            item.status = '异常'
          }else if (item.status == '6') {
            item.status = '进程服务运行中'
          }else if (item.status == '7') {
            item.status = '进程服务已停止'
          }
        }
        //console.log("循环数据："+JSON.stringify(responseData));
        this.tableData = responseData
      },
      handleSelectionChange: function (val) {//全选
        this.multipleSelection = val
        //console.log("选择0："+JSON.stringify(this.multipleSelection));
      },
      handleClick: function (row) {//编辑框赋值
        this.edit.dialogVisible = true
        this.edit.edit_token = row.token
        //转换状态
        switch (row.status) {
          case '无操作':
            this.edit.serve_value = '0'
            break
          case '启动进程服务':
            this.edit.serve_value = '1'
            break
          case '终止进程服务':
            this.edit.serve_value = '2'
            break
          case '正在启动进程服务...':
            this.edit.serve_value = '1'
            break
          case '正在终止进程服务...':
            this.edit.serve_value = '2'
            break
          case '异常':
            this.edit.serve_value = '5'
            break
          case '进程服务运行中':
            this.edit.serve_value = '1'
            break
          case '进程服务已停止':
            this.edit.serve_value = '2'
            break
        }
        this.edit.edit_name = row.name
        this.edit.edit_ip = row.ip
        this.edit.edit_remarks = row.remarks
      },
      handleClose: function (done) {
        done()//关闭编辑模态框
      },
      deleteClick: function (row) {//删除提示
        this.$confirm('此操作将删除服务进程信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.axiosHttp_delete(row)
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      axiosHttp_delete: function (row) {//删除服务进程
        let _this = this
        var params = {}
        params.token = row.token
        this.$post(this.$axiosURL.deleteServiceProcess(), params)
          .then((response) => {
            if (response.success == true) {
              _this.axiosHttp_data()//刷新数据
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
            } else {
              this.$message({
                type: 'info',
                message: '删除失败'
              })
            }
          }).catch(function (error) {
          this.$message({
            type: 'warning',
            message: '请求错误！'
          })
        })
      },
      setstate: function () {
        //批量更新服务进程状态 验证是否勾选数据
        //验证 是否选取数据
        if (this.multipleSelection.length == 0) {
          this.$message({
            type: 'warning',
            message: '请勾选数据！'
          })
          return
        }
        //验证是否选择更新的状态
        if (this.update_course_value == '') {
          this.$message({
            type: 'warning',
            message: '请选择服务进程状态！'
          })
          return
        }
        this.$confirm('此操作将更新勾选的服务进程状态, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.axiosHttp_batch_update()
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消更新'
          })
        })
      }
    }
  }
</script>
