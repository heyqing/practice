<script setup lang="ts">
import { visitorId } from './components/fingerprintjs'
</script>
<template>
  <div>
    <div class="finger">
      <h2>浏览器指纹 : {{ visitorId }}</h2>
    </div>
  </div>
</template>
<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}

.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}

.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
