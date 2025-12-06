<script setup lang="ts">
import { ElConfigProvider } from 'element-plus'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
</script>

<template>
  <ElConfigProvider :locale="zhCn">
    <div id="app">
      <h1>美食食谱分享系统</h1>
      <p>前端项目初始化成功！</p>
    </div>
  </ElConfigProvider>
</template>

<style scoped>
#app {
  font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

h1 {
  font-size: 2.5em;
  color: #42b983;
  margin-bottom: 20px;
}

p {
  font-size: 1.2em;
  color: #666;
}
</style>
