<template>
  <el-form :rules="rules"
           class="login-form"
           :model="loginForm"
           ref="loginForm"
  >
    <h1>cveID</h1>
    <el-form-item prop="username">
      <el-input placeholder="请输入用户名" v-model="loginForm.username" clearable></el-input>
    </el-form-item>
    <el-form-item>
      <el-button
          type="primary"
          :loading="loading"
          @click.native.prevent="getData('loginForm')"
          style="width: 60%"
      >查询
      </el-button>
    </el-form-item>
  </el-form>
  <el-table :data="tableData" stripe style="width: 100%">
    <el-table-column prop="cve" label="cve"/>
    <el-table-column fixed="right" label="操作">
      <template #default="scope">
        <el-button @click="getData1(scope.$index, scope.row)"
        >查看详情
        </el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-dialog title="用户详情" v-model="dialogTableVisible">
    <div>
      用户名:{{ dialogUsername }}
    </div>
    <el-table :data="roleData">
      <el-table-column property="roleName" label="角色名" width="150"></el-table-column>
      <el-table-column property="roleDescription" label="角色描述" width="150"></el-table-column>
    </el-table>
  </el-dialog>
</template>
<script>
export default {
  data() {
    return {
      dialogTableVisible: false,
      dialogUsername: "",
      tableData: [],
      roleData: [],
      loginForm: {
        username: ""
      }
    }
  },
  methods: {
    getData() {
      this.$http.post('/blogs/cve', {
        cveID: this.loginForm.username,
      }).then(response => {
        console.log(response.data);
        this.tableData = response.data.vulnerabilities;
        // console.log(this.tableData);
      }, response => {
        console.log("error");
      });
    }, getData1(index, row) {
      this.dialogTableVisible = true;
      console.log(index);
      console.log(row.username);
      this.dialogUsername = row.username;
      console.log(row.roles);
      this.roleData = row.roles;
    }
  }
}

</script>