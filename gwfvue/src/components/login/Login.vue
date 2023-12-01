<template>
  <el-container>
    <el-header class="header">Header</el-header>
    <el-main class="main">
      <el-form :rules="rules"
               class="login-form"
               :model="loginForm"
               ref="loginForm"
      >
        <h1>登录</h1>
        <el-form-item prop="username">
          <el-input placeholder="请输入用户名" v-model="loginForm.username" clearable></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input nplaceholder="请输入密码" v-model="loginForm.password" show-password clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
              type="primary"
              :loading="loading"
              @click.native.prevent="handleLogin('loginForm')"
              style="width: 60%"
          >登录
          </el-button>

          <el-button
              style="width: 30%"
              type="primary"
              :loading="loading"
              @click.native.prevent="handleRegister"
          >注册
          </el-button>
        </el-form-item>
      </el-form>
    </el-main>
    <el-footer class="footer">Footer</el-footer>
  </el-container>


</template>


<script>
//
//// 设置cookie
//proxy.$cookies.set('k1', 'v1', '1h')
//// 获取
//console.log(proxy.$cookies.get('k1')) // v1
export default {
  name: "login",
  data() {
    var validateUsername = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输用户名'));
      } else {
        if (this.loginForm.username !== '') {
          this.$refs.loginForm.validateField('username');
        }
        callback();
      }
    };
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.loginForm.password !== '') {
          this.$refs.loginForm.validateField('password');
        }
        callback();
      }
    };
    return {
      loginForm: {
        username: "",
        password: "",
      },
      rules: {
        username: [
          {validator: validateUsername, trigger: 'blur'}
        ],
        password: [
          {validator: validatePass, trigger: 'blur'}
        ]
      }
    };
  },

  methods: {
    handleLogin(loginForm) {
      this.$refs[loginForm].validate((valid) => {
        if (valid) {
          this.$http.post("/users/login", {
            username: this.loginForm.username,
            password: this.loginForm.password
          }).then(res => {
            if (res.data.success) {
              // 删除之前的cookies
              // if (this.$cookies.isKey("sessionid")) {
              //   this.$cookies.remove("sessionid")
              // }
              // 设置cookies的日期为一个月
              // this.$cookies.config("1m")
              // 设置cookies
              // console.log(document.cookie)
              this.$cookies.set("sessionid", res.data.data.username)
              this.$cookies.config('7d', '', '', true)
              localStorage.setItem("sessionid", res.data.data.username)
              // console.log(this.$cookies.get("sessionid"))
              //   this.userLogin(res.data);
              // this.$message.success(`${res.data.message}`)
              // 登录成功 跳转至首页
              // this.$route.push({name:'Home'})

              this.$router.push('/index')
            } else {
              // this.$message.error(`${res.data.message}`);
              this.$message.error(res.data.message);
              return false
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },

    handleRegister() {
      this.$router.push('/reg')
    }
  }
}
</script>
<style lang="css" scoped>
.login-form {
  -webkit-border-radius: 100px;
  border-radius: 20px;
  -moz-border-radius: 40px;
  background-clip: padding-box;
  margin: 100px auto;
  width: 400px;
  padding: 30px 30px 30px 30px;
  background: #c1c9d2;
  border: 1px solid #19c21f;
  label-position: left;
  label-width: 0px;
}

.el-container {
  height: 100%;
  width: 100%;
}

.header {
  background: #19c21f;
  height: 10%;
}

.footer {
  background: #a65b51;
  height: 10%;
}

.main {
  background-image: url("../../assets/1120.jpg");
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center center;
}
</style>