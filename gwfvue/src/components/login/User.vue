<template>
  <el-table :data="tableData.slice((currentPage-1)*pagesize,currentPage*pagesize)" stripe style="width: 100%">
    <el-table-column prop="username" label="用户名"/>
    <el-table-column fixed="right" label="操作">
      <template #default="scope">
        <el-button @click="getData1(scope.$index, scope.row)"
        >查看详情
        </el-button>
        <el-button @click=""
        >重置密码
        </el-button>
        <el-button @click="delUser(scope.$index, scope.row)"
        >删除用户
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
      currentPage: 1, //默认显示页面为1
      pagesize: 5, //    每页的数据条数
      tableData: [],
      dialogTableVisible: false,
      dialogUsername: "",
      roleData: []
    }
  },
  mounted() {
    this.getData();
  },
  methods: {
    getData() {
      this.$http.get('/users/getAlluserRole').then(response => {
        this.tableData = response.data.data;
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
    },
    delUser(index, row) {
      this.$http.post("/users/delUser", {
        username: row.username,
      }).then(res => {
        if (res.data.success) {
          console.log("删除用户成功")
          this.tableData.splice(index, 1)
        }

      })
    },
//每页下拉显示数据
    handleSizeChange: function (size) {
      this.pagesize = size;
      /*console.log(this.pagesize) */
    }
    ,
//点击第几页
    handleCurrentChange: function (currentPage) {
      this.currentPage = currentPage;
      /*console.log(this.currentPage) */
    }

  }
}

</script>