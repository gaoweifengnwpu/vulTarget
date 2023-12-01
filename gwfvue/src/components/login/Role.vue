<template>
  <el-tabs>
    <el-tab-pane label="角色管理">
      <el-form :model="Role" status-icon :rules="rules" ref="Role" label-width="100px" class="demo-ruleForm">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="Role.roleName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色描述" prop="roleDescription">
          <el-input v-model="Role.roleDescription" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('Role')">提交</el-button>
          <el-button @click="resetForm('Role')">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="tableData.slice((currentPage-1)*pagesize,currentPage*pagesize)" stripe style="width: 100%">
        <el-table-column prop="roleName" label="角色名"/>
        <el-table-column prop="roleDescription" label="角色描述"/>
        <el-table-column fixed="right" label="操作">
          <template #default="scope">
            <el-button @click="getData111(scope.$index, scope.row)"
            >删除角色
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[1, 10, 20, 40]"
            :page-size="pagesize"
            layout="total, sizes,prev, pager, next"
            :total="tableData.length"
            prev-text="上一页"
            next-text="下一页">
        </el-pagination>
      </div>
    </el-tab-pane>
    <el-tab-pane label="权限管理">选项卡3的内容</el-tab-pane>
  </el-tabs>
</template>

<script>


export default {
  data() {
    var validateRoleName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入角色名称'));
      } else {
        if (this.Role.roleName !== '') {
          this.$refs.Role.validateField('roleName');
        }
        callback();
      }
    };
    var validateRoleDescription = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入角色描述'));
      } else {
        if (this.Role.roleDescription !== '') {
          this.$refs.Role.validateField('roleDescription');
        }
        callback();
      }
    };
    return {
      currentPage: 1, //默认显示页面为1
      pagesize: 10, //    每页的数据条数
      tableData: [],
      Role: {
        roleName: '',
        roleDescription: '',
      },
      rules: {
        roleName: [
          {validator: validateRoleName, trigger: 'blur'}
        ],
        roleDescription: [
          {validator: validateRoleDescription, trigger: 'blur'}
        ]
      }
    }
  },
  mounted() {
    this.getData();
  },
  methods: {
    getData() {
      this.$http.get('/roles/getAllRole').then(response => {
        console.log("获取全部角色");
        console.log(response.data);
        this.tableData = response.data.data;
      }, response => {
        console.log("error");
      });
    },
    //每页下拉显示数据
    handleSizeChange: function (size) {
      this.pagesize = size;
      /*console.log(this.pagesize) */
    },
    //点击第几页
    handleCurrentChange: function (currentPage) {
      this.currentPage = currentPage;
      /*console.log(this.currentPage) */
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$http.post("/roles/insertRole", {
            roleName: this.Role.roleName,
            roleDescription: this.Role.roleDescription
          }).then(res => {
            if (res.data.success) {
              this.$message.info(res.data.message);
              this.getData();
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
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}

</script>
