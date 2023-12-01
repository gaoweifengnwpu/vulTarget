<template>
  <el-container>
    <el-aside width="200px">
      <el-menu
          :router="true"
          active-text-color="#ffd04b"
          background-color="#545c64"
          class="el-menu-vertical-demo"
          default-active="2"
          text-color="#fff"
      >
        <el-menu-item v-for="(item,i) in navList" :key="i" :index="item.name">
          <span> {{ item.navItem }}</span>
        </el-menu-item>

      </el-menu>
    </el-aside>
    <el-main>
      <router-view/>
    </el-main>
  </el-container>
</template>
<script>
export default {
  data() {
    return {
      navList: [
        {name: '/index/success', navItem: '账户设置'},
        {name: '/index/file', navItem: '上传文件'},
        {name: '/index/user', navItem: '用户列表'},
        {name: '/index/role', navItem: '用户权限管理'},
        {name: '/index/blog', navItem: '写博客'},
        {name: '/index/gb', navItem: '查看博客'},
        {name: '/index/cve', navItem: 'cve'}
      ]
    }
  }
}
</script>