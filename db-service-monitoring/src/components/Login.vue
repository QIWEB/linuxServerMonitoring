<template>
  <div class="finish-wrap">
    <el-form class="form-css">
      <h3 class="title">系统登录</h3>
      <el-form-item>
        <el-input type="text" placeholder="请输入账号" v-model="ruleForm.userName"></el-input>
      </el-form-item>
      <el-form-item prop="checkPass">
        <el-input type="password" placeholder="请输入密码" v-model="ruleForm.password"></el-input>
      </el-form-item>
      <el-checkbox class="remember" v-model="ruleForm.checked" @change="memoryUser">记住账户</el-checkbox>
      <el-form-item style="width:100%;">
        <el-button :loading="ruleForm.loading" type="primary" style="width:100%;" @click="logining">登录</el-button>
        <!--<el-button @click.native.prevent="handleReset2">重置</el-button>-->
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  export default {
    data () {
      return {
        ruleForm: {
          userName: '', //用户名
          password: '',  //密码
          loading: false, //控制登录请求等待
          checked:false //记住账户
        }
      }
    },mounted () {//初始加载需要调用的函数
      let checked=localStorage.getItem("checked");//获取checked
      if(checked=='true'){
        this.ruleForm.userName=localStorage.getItem("userName");
      }
    },
    methods: {
      memoryUser: function () {//记住账户
        localStorage.setItem("checked",this.ruleForm.checked);//存储记住账户状态
      },
      logining: function () {//登录
        //保存的账号
        var name = this.ruleForm.userName
        //保存的密码
        var pass = this.ruleForm.password
        if (name == '' || name == null) {
          this.$message.error('请输入正确的用户名')
          return
        } else if (pass == '' || pass == null) {
          this.$message.error('请输入正确的密码')
          return
        }
         this.axiosHttp_login();
      },
      axiosHttp_login: function () {
        let _this = this
        this.ruleForm.loading = true
        //封装参数
        let params = {};
        params.userName = this.ruleForm.userName
        params.passWord = this.ruleForm.password
        this.$post(this.$axiosURL.loginURL(), params)
          .then((response) => {
            if (response.success == true) {
              //开始存储登录账户信息 到 localStorage(说明：localStorage生命周期是永久，这意味着除非用户显示在浏览器提供的UI上清除localStorage信息，否则这些信息将永远存在。存放数据大小为一般为5MB,而且它仅在客户端（即浏览器）中保存，不参与和服务器的通信。)
              localStorage.setItem('accessToken', _this.ruleForm.userName)
              localStorage.setItem('userName', _this.ruleForm.userName)
              //console.log("accessToken:"+localStorage.getItem("accessToken"));
              _this.$message({message: '成功登录!', type: 'success'})
              _this.$router.replace('/Index')
            } else {
              _this.ruleForm.loading = false
              _this.$message({
                type: 'error',
                message: '登录是失败，账户或密码错误！'
              })
            }
          }).catch(function (error) {
          _this.ruleForm.loading = false
          _this.$message({
            type: 'warning',
            message: '请求错误！' + error
          })
        })
      }

    }
  }
</script>

<style scoped>
  .form-css {
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #212020;
  }

  .finish-wrap {
    background: url("../assets/img/logo.jpg");
    height: 100%;
    position: fixed; /*脱离文档流，为他添加个fixed属性*/
    width: 100%
  }
  .el-input{
    width: 345px;
  }
</style>
