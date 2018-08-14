<template>
  <div class="hello">
    <div style="width:100%;height: 60px;top:0px;background-color:rgb(64,158,255);position:fixed;
       z-index: 999;">
      <header class="el-header">
        <span class="" style="margin-left: 1%; margin-top: 1%;float: left;color: mintcream">服务监测系统</span>
        <span class="demonstration" style="float:right;padding-top:10px;margin-right:1%">
                <el-dropdown trigger="click">
                  <span class="el-dropdown-link" style="color:white">
                    <img src="../assets/img/user.png"
                         style="width: 40px;height: 40px;border-radius: 20px;margin: -5px 0px 10px 10px;float: right;"/>{{userName}}<i
                    class="el-icon-caret-bottom el-icon--right"></i>
                  </span>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item @click.native=""><i class="el-icon-view"></i>个人信息</el-dropdown-item>

                    <el-dropdown-item @click.native="logout"><i
                      class="el-icon-circle-close-outline"></i>退出登录</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
            </span>
      </header>
      <el-header style="margin-top: 60px">
        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
          <el-submenu index="1">
            <template slot="title">服务器发版</template>
            <el-menu-item index="1-1">服务器信息</el-menu-item>
            <el-menu-item index="1-2">新增服务器</el-menu-item>
          </el-submenu>
          <el-menu-item index="2">服务状态情况</el-menu-item>
          <el-submenu index="3">
            <template slot="title">服务器服务进程</template>
            <el-menu-item index="3-1">新增服务进程</el-menu-item>
            <el-menu-item index="3-2">服务进程信息</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-header>
    </div>

    <el-container>
      <el-col :xs="20" :sm="20" :md="20" :lg="20">
        <!--点击菜单 要显示的内容嵌套在这里-->
        <div style="margin-top:130px">
          <router-view ref="router_view"></router-view>
        </div>
      </el-col>
    </el-container>

    <!--添加服务器模态框-->
    <div class="add-data">
      <el-dialog
        title="新增服务器"
        :visible.sync="dialogVisible"
        width="30%">
        <el-col class="el-col-edit">
          服务器归属：
          <el-input placeholder="请选输入服务器归属名" v-model="add_name"></el-input>
        </el-col>
        <el-col class="el-col-edit">
          服务器ip：
          <el-input placeholder="请选输入服务器ip" v-model="add_ip"></el-input>
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
          <el-input placeholder="请选输入备注" v-model="remarks"></el-input>
        </el-col>
        <span slot="footer" class="dialog-footer">
    <!--<el-button @click="dialogVisible = false">取 消</el-button>-->
    <el-button class="el-button-confirm" type="primary" @click="preserve">保存</el-button>
      </span>
      </el-dialog>
    </div>

    <!--添加服务进程模态框-->
    <div class="add-course">
      <el-dialog
        title="新增服务进程"
        :visible.sync="course.dialogVisible"
        width="30%">
        <el-col class="el-col-edit">
          服务进程名：
          <el-input placeholder="请选输入服务进程名" v-model="course.add_name"></el-input>
        </el-col>
        <el-col class="el-col-edit">
          所属服务器ip：
          <el-input placeholder="请选输入服务器ip" v-model="course.add_ip"></el-input>
        </el-col>
        <el-col class="el-col-edit">
          服务进程状态：
          <el-select v-model="course.serve_value" placeholder="请选择">
            <el-option
              v-for="item in course.options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col class="el-col-edit">
          备注：
          <el-input placeholder="请选输入备注" v-model="course.remarks"></el-input>
        </el-col>
        <span slot="footer" class="dialog-footer">
    <el-button class="el-button-confirm" type="primary" @click="course_preserve">保存</el-button>
      </span>
      </el-dialog>
    </div>

    <el-footer>
      <div style="width: 100%;height: 50px;margin-top:10px;border: solid 1px #e8f3ff;text-align:center;">
        <span>大贲科技(上海分公司)</span>
      </div>

    </el-footer>
  </div>
</template>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
 .el-col-edit {
    margin-top: 10px;
    text-align: right;
  }

  .el-input {
    width: 260px;
  }

</style>
<script>
  import AxiosURL from '../axios/axiosUrl'//引用全局url地址对象
  export default {
    data () {
      return {
        userName:'',//登录用户名
        activeIndex: '1-1',
        dialogVisible: false,
        add_name: '',
        add_ip: '',
        serve_value: '',
        remarks: '',
        //服务器状态下拉框选项数据
        options: [{value: 'OK', label: '是'}, {value: 'notOK', label: '否'}],
        course: {
          //服务进程状态下拉框数据
          options: [{'value': '0', 'label': '无操作'}, {'value': '1', 'label': '启动进程服务'}, {
            'value': '2',
            'label': '终止进程服务'
          }],
          serve_value: '',//服务器状态选项值
          dialogVisible: false,//进程状态模态框开关
          add_name: '',//服务器进程名
          add_ip: '',//所属服务器ip
          remarks: '',//备注
        }

      }
    },
    mounted () {
      this.userName=localStorage.getItem('accessToken');//存储登录用户
      this.handleSelect('1-1')
    },
    methods: {
      course_preserve: function () {//保存服务进程添加
        //验证参数
        if (this.course.add_name == '' || this.course.add_ip == '' || this.course.serve_value == '') {
          this.$message({
            type: 'warning',
            message: '请完整填写服务进程信息！'
          })
          return
        }
        let params = {}
        params.name = this.course.add_name
        params.ip = this.course.add_ip
        params.status = this.course.serve_value
        params.remarks = this.course.remarks
        this.$post(AxiosURL.saveServiceProcess(), params)
          .then((response) => {
            if (response.success == true) {
              if(response.code=="0"){
                this.$message({message: response.msg, type: 'error'});
                return;
              }
              this.course.dialogVisible = false
              this.$refs.router_view.axiosHttp_data()//调用子页面函数，刷新数据
              this.$router.push('/ProcessList');//跳转相应页面
              //清除添加框数据
              this.course.add_name = ''
              this.course.add_ip = ''
              this.course.serve_value = ''
              this.course.remarks = ''
              this.$message({message: '添加成功!', type: 'success'})
            } else {
              this.$message({
                type: 'info',
                message: response.msg
              })
            }
          }).catch(function (error) {
              this.$message({
                type: 'info',
                message: '数据请求错误！'
              })
          console.log('数据请求错误：' + error)
        })
      },
      preserve: function () { //保存服务器添加
        //验证参数
        if (this.add_name == '' || this.add_ip == '' || this.serve_value == '') {
          this.$message({
            type: 'warning',
            message: '请完整填写服务器信息！'
          })
          return
        }
        let params = {}
        params.serverOwnership = this.add_name
        params.ip = this.add_ip
        params.isOK = this.serve_value
        params.remarks = this.remarks
        this.$post(AxiosURL.saveFabanURL(), params)
          .then((response) => {
            if (response.success == true) {
              this.dialogVisible = false
              this.$refs.router_view.axiosHttp_data()//调用子页面函数，刷新数据
              this.$router.push('/Serverdetails');//数据刷新后跳转到相应页面
              //清除添加框数据
              this.add_name = ''
              this.add_ip = ''
              this.serve_value = ''
              this.remarks = ''
              this.$alert('添加成功,新增服务器Token值:' + response.token, '提示', {
                confirmButtonText: '确定',
              })
            } else {
              this.$message({
                type: 'info',
                message: response.msg
              })
            }
          }).catch(function (error) {
          console.log('数据请求错误：' + error)
        })
      },
      handleSelect: function (key) {// 菜单跳转
        switch (key) {
          case '1-1':
            this.$router.push('/Serverdetails')
            //this.breadcrumbItems = ['导航一']
            break
          case '1-2':
            this.dialogVisible = true//开启新增服务器模态框
            break
          case '2':
            this.$router.push('/ServerStatus')
            break
          case '3-1':
            this.course.dialogVisible = true//开启新增服务进程模态框
            break
          case '3-2':
            this.$router.push('/ProcessList')
            break
        }
      },//退出登录
      logout: function () {
        var _this = this
        this.$confirm('确认退出吗?', '提示', {
          type: 'warning'
        }).then(() => {
          localStorage.removeItem('accessToken')
          _this.$router.push('/')
        }).catch(() => {

        })
      }
    }
  }
</script>

