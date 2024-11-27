<template>
  <div>
    <h2>这是Ip例子</h2>
    <p>IP 地址: {{ ip }}</p>
    <p>地理位置: {{ location }}</p>
  </div>
</template>

<script setup lang="js">
import { ref } from 'vue';

// 定义响应式数据
const ip = ref('');
const location = ref('');

// 导入 geo-location.js 文件
import './geo-location.js';

// 使用 `window` 来访问 geo-location.js 中的全局变量或函数
window.updateGeoLocation = (newIp, newLocation) => {
  ip.value = newIp;
  location.value = newLocation;
};
</script>

<style scoped></style>
