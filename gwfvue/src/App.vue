<script setup>
import Login from "@/components/login/Login.vue";
</script>
<!--在使用 <script setup> 的单文件组件中，导入的组件可以直接在模板中使用，无需注册：-->


<template>
  <div id="app">
    <router-view/>
  </div>

</template>
<style lang="css">
#app {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}
</style>