<template>
  <div>
    <div style="margin-left: 110px" @click="setstate">
      <span>更新发版服务状态:</span>
      <el-switch
        v-model="state_value"
        active-text="是"
        inactive-text="否">
      </el-switch>
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
          prop="serverOwnership"
          label="服务器归属"
          width="200">
        </el-table-column>
        <el-table-column
          prop="ip"
          label="服务器ip"
          width="200">
        </el-table-column>
        <el-table-column
          prop="isOK"
          label="服务器状态(是否发版)"
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
        title="编辑服务器状态"
        :visible.sync="dialogVisible"
        width="30%"
        :before-close="handleClose">
        <el-col class="el-col-edit">
          服务器归属：
          <el-input v-model="edit_serverOwnership" placeholder="请输入服务器归属名"></el-input>
        </el-col>
        <el-col class="el-col-edit">
          服务器ip：
          <el-input v-model="edit_ip" placeholder="请输入服务器ip"></el-input>
        </el-col>
        <el-col class="el-col-edit">
          服务器状态：
          <el-select v-model="serve_value" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col class="el-col-edit">
          备注：
          <el-input v-model="edit_remarks" placeholder="请输入备注"></el-input>
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

  .el-button-confirm {
    width: 365px;
    margin-top: 10px;
  }

</style>
<script>
  import data from '../../assets/data.json'
  export default {
    data () {
      return {
        //下拉框选项数据
        options: [{value: 'OK', label: '是'}, {value: 'notOK', label: '否'}],
        // 表格数据
        tableData: [],
        //默认每页数据量
        pageSize: 10,
        //当前页
        currentPage: 1,
        //数据总数
        totalCount: 100,
        dialogVisible: false, //控制编辑模态框显示 true:显示，false：隐藏
        state_value: false,  //设置服务器状态开关
        serve_value: '', //编辑框使用的服务器状态值
        edit_serverOwnership: '',//编辑框使用
        edit_remarks: '',//编辑框使用
        edit_ip: '', //编辑框使用
        edit_id: '',
        multipleSelection: [] //勾选的数据
      }
    }, mounted () {//初始加载需要调用的函数
      //根据全局isOK值获取服务器开关状态
      this.state_value = localStorage.getItem('isOK') == 'OK' ? true : false
      this.axiosHttp_data()
    }, methods: {
      handleSelectionChange: function (val) {//全选
        this.multipleSelection = val
        //console.log("选择0："+JSON.stringify(this.multipleSelection));
      },
      axiosHttp_data: function () {
        let _this = this
        // http方式请求后台接口
        // 查看输出数据 可以用 这个  JSON.stringify() 转json
        this.$post(this.$axiosURL.serverURL())
          .then((response) => {
            _this.transitionState(response.data)
            //console.log("返回数据："+JSON.stringify(response));
          }).catch(function (error) {
          console.log('数据请求错误：' + error)
        })
      },
      transitionState: function (responseData) {//转换数据值
        for (let item of responseData) {
          if (item.isOK == 'OK') {
            item.isOK = '是'
          } else if (item.isOK == 'notOK') {
            item.isOK = '否'
          } else {
            item.isOK = '异常'
          }
        }
        //console.log("循环数据："+JSON.stringify(responseData));
        this.tableData = responseData
      },
      handleClick: function (row) { //编辑
        this.dialogVisible = true
        this.serve_value = row.isOK == '是' ? 'OK' : 'notOK'
        this.edit_serverOwnership = row.serverOwnership
        this.edit_ip = row.ip
        this.edit_remarks = row.remarks
        this.edit_id = row.id
      },
      handleClose: function (done) {
        done()//关闭编辑模态框
      },
      preserve: function () {//保存修改
        //存储vue当前对象,因为在$post请求里面直接用this并不代表本页面vue对象
        let _this = this
        //验证参数非空
        if (this.edit_serverOwnership == '' || this.edit_ip == '' || this.serve_value == '') {
          this.$message({
            type: 'warning',
            message: '请完整填写服务器信息！'
          })
          return
        }
        //封装参数
        let params = {}
        params.id = this.edit_id
        params.serverOwnership = this.edit_serverOwnership
        params.ip = this.edit_ip
        params.isOK = this.serve_value
        params.remarks = this.edit_remarks
        //console.log('参数数据：' + JSON.stringify(params))
        this.$post(this.$axiosURL.updateFabanURL(), params).then((response) => {
          if (response.success == true) {
            _this.dialogVisible = false //关闭编辑窗口
            _this.axiosHttp_data()//刷新数据
            _this.$message({
              type: 'success',
              message: '更新成功!'
            })
          } else {
            _this.$message({
              type: 'info',
              message: '更新失败'
            })
          }
        }).catch(function (error) {
          _this.$message({
            type: 'warning',
            message: '请求错误！' + error
          })
        })

      },
      axiosHttp_updateAll: function () {//批量更新服务器状态（是否发版）
        //存储vue当前对象,因为在$post请求里面直接用this并不代表本页面vue对象
        let _this = this
        let isOK = this.state_value == true ? 'OK' : 'notOK'
        //全局存储更新服务器开关状态
        localStorage.setItem('isOK', isOK)
        //debugger;
        //封装参数
        var params = {}
        params.isOK = isOK
        params.fabanList = _this.multipleSelection
        this.$post(this.$axiosURL.updateAllURL(), params)
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
                message: '更新失败'
              })
            }
          }).catch(function (error) {
          _this.$message({
            type: 'warning',
            message: '请求错误！' + error
          })
        })
      },
      setstate: function () {//设置服务器状态
        //验证 是否选取数据
        if (this.multipleSelection.length == 0) {
          this.$message({
            type: 'warning',
            message: '请勾选数据！'
          })
          this.state_value = this.state_value == true ? false : true// 回复之前状态
          return
        }
        this.$confirm('此操作将更新勾选的服务器状态, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.axiosHttp_updateAll()
        }).catch(() => {
          this.state_value = this.state_value == true ? false : true//进入取消 回复之前状态
          this.$message({
            type: 'info',
            message: '已取消更新'
          })
        })
      },
      deleteClick: function (row) {//删除提示
        this.$confirm('此操作将删除服务器信息, 是否继续?', '提示', {
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
      axiosHttp_delete:function(row){//删除请求
        //存储vue当前对象,因为在$post请求里面直接用this并不代表本页面vue对象
        let _this = this
        var params = {};
        params.id=row.id;
        this.$post(this.$axiosURL.deleteFaban(),params)
          .then((response)=>{
            if(response.success==true) {
              _this.axiosHttp_data()//刷新数据
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
            }else{
              this.$message({
                type: 'info',
                message: '删除失败'
              })
            }
          }).catch(function (error){
          this.$message({
            type: 'warning',
            message: '请求错误！' + error
          })
        })
      }

    }
  }
</script>
