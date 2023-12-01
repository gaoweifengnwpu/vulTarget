<template>
  <el-table :data="tableData.slice((currentPage-1)*pagesize,currentPage*pagesize)" stripe style="width: 100%">
    <el-table-column prop="author" label="作者"/>
    <el-table-column fixed="right" label="操作">
      <template #default="scope">
        <el-button @click="getData1(scope.$index, scope.row)"
        >查看博客详情
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
  <el-dialog title="文章详情" v-model="dialogTableVisible">
    <div>
      作者:{{ dialogUsername }}
    </div>
    <div v-html="htmlContent"></div>
    <!--    <el-table :data="roleData">-->
    <!--      <el-table-column property="roleName" label="角色名" width="150"></el-table-column>-->
    <!--      <el-table-column property="roleDescription" label="角色描述" width="150"></el-table-column>-->
    <!--    </el-table>-->
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
      roleData: [],
      htmlContent: ""
    }
  },
  mounted() {
    this.getData();
  },
  methods: {
    getData() {
      this.$http.get('/blogs/getblog').then(response => {
        this.tableData = response.data.data;
        console.log(this.tableData);
      }, response => {
        console.log("error");
      });
    }, getData1(index, row) {
      this.dialogTableVisible = true;
      console.log(index);
      console.log(row.username);
      this.dialogUsername = row.author;
      this.htmlContent = row.content;
      console.log(row.roles);
      this.roleData = row.roles;
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