<template>
  <el-container>
    <el-header> Welcome {{ msg }}</el-header>
    <el-main>
      <el-form :rules="fieldRules"
               :model="loginForm"
               ref="loginForm"
      >
        <el-form-item prop="password">
          <el-input placeholder="请输入新密码" v-model="loginForm.password" show-password clearable></el-input>
        </el-form-item>
        <el-form-item style="margin-bottom: 60px">
          <el-button
              style="width: 100%"
              type="primary"
              :loading="loading"
              @click.native.prevent="handleLogin('loginForm')"
          >更新密码
          </el-button>
        </el-form-item>
        <el-form-item>
          <el-button
              style="width: 100%"
              type="primary"
              :loading="loading"
              @click.native.prevent="handleLogout"
          >注销
          </el-button>
        </el-form-item>
      </el-form>
    </el-main>
  </el-container>


</template>
<script>
import {getCurrentInstance} from "vue"

// // 创建可以访问内部组件实例的实例
// const internalInstance = getCurrentInstance()
// const internalData = internalInstance.appContext.config.globalProperties
// const sessionid = internalData.$cookies.get("sessionid") // 后面的为之前设置的cookies的名字
export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入新密码'));
      } else {
        if (this.loginForm.password !== '') {
          this.$refs.loginForm.validateField('password');
        }
        callback();
      }
    };
    return {
      loginForm: {
        password: ""
      },
      msg: this.$cookies.get("sessionid"),
      fieldRules: {
        password: [
          {validator: validatePass, trigger: 'blur'},
        ]
      }
    }
  },
  methods: {
    handleLogin(loginForm) {
      this.$refs[loginForm].validate((valid) => {
        if (valid) {
          this.$http.post("/users/update", {
            password: this.loginForm.password
          }).then(res => {
            if (res.data.success) {
              this.$message.info(res.data.message);
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
    handleLogout() {
      this.$http.get("/users/logout", {}).then(res => {
        console.log("get请求的res:", res.data)
        this.$message.info(res.data.message);
        this.$router.push('/lo')
      })

    }
  }
}
</script>