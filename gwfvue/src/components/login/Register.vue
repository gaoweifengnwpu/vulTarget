<template>
  <el-container>
    <el-header class="header">Header</el-header>
    <el-main class="main">

      <el-form :rules="rules"
               class="register-form"
               :model="Register"
               ref="Register"
      >
        <h1>注册账号</h1>
        <el-form-item prop="username">
          <el-input placeholder="请输入用户名" v-model="Register.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" placeholder="请输入密码" v-model="Register.password" show-password
                    autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="checkPass">
          <el-input type="password" placeholder="请输入确认密码" v-model="Register.checkPass" show-password
                    autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item style="width:100%;">
          <el-button
              style="width: 60%"
              type="primary"
              :loading="loading"
              @click.native.prevent="handleRegister('Register')"
          >注册
          </el-button>
          <el-button
              style="width: 30%"
              type="primary"
              :loading="loading"
              @click.native.prevent="handleLogin"
          >登录
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
        if (this.Register.username !== '') {
          this.$refs.Register.validateField('username');
        }
        callback();
      }
    };
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.Register.password !== '') {
          this.$refs.Register.validateField('password');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.Register.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      Register: {
        username: "",
        password: "",
        checkPass: ""

      },
      rules: {
        username: [
          {validator: validateUsername, trigger: 'blur'}
        ],
        password: [
          {validator: validatePass, trigger: 'blur'}
        ],
        checkPass: [
          {validator: validatePass2, trigger: 'blur'}
        ]
      }
    };
  },

  methods: {
    handleRegister(form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          this.$http.post("/users/register", {
            username: this.Register.username,
            password: this.Register.password
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

              this.$message.info(res.data.message);
              // console.log(this.$cookies.get("sessionid"))
              //   this.userLogin(res.data);
              // this.$message.success(`${res.data.message}`)
              // 登录成功 跳转至首页
              // this.$route.push({name:'Home'})

              this.$router.push('/lo')
            } else {
              this.$message.error(res.data.message);
              // this.$message.error(`${res.data.message}`);
              return false
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
      // this.$http.post("http://192.168.201.40:8888/register", {
      //   username: this.Register.username,
      //   password: this.Register.password
      // }).then(res => {
      //   if (res.data.success) {
      //     // 删除之前的cookies
      //     // if (this.$cookies.isKey("sessionid")) {
      //     //   this.$cookies.remove("sessionid")
      //     // }
      //     // 设置cookies的日期为一个月
      //     // this.$cookies.config("1m")
      //     // 设置cookies
      //     // console.log(document.cookie)
      //
      //     this.$message.info(res.data.message);
      //     // console.log(this.$cookies.get("sessionid"))
      //     //   this.userLogin(res.data);
      //     // this.$message.success(`${res.data.message}`)
      //     // 登录成功 跳转至首页
      //     // this.$route.push({name:'Home'})
      //
      //     this.$router.push('/lo')
      //   } else {
      //     this.$message.error(res.data.message);
      //     // this.$message.error(`${res.data.message}`);
      //     return false
      //   }
      // })

    },
    handleLogin() {
      this.$router.push('/lo')
    }
  }
};
</script>
<style lang="css" scoped>
.register-form {
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