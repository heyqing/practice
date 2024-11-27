<script setup>
import * as THREE from "three"; // 导入three
import { OrbitControls } from "three/examples/jsm/controls/OrbitControls.js"; //导入轨道控制器
import { GUI } from "three/examples/jsm/libs/lil-gui.module.min.js" //导入lil.gui
import * as TWEEN from "three/examples/jsm/libs/tween.module"

//创建场景
const scene = new THREE.Scene();

//创建相机(视角，宽高比，近平面，远平面)
const camera = new THREE.PerspectiveCamera(45, window.innerWidth / window.innerHeight, 0.1, 1000);

//创建渲染器
const renderer = new THREE.WebGLRenderer();
//设置渲染大小
renderer.setSize(window.innerWidth, window.innerHeight);
//添加至body
document.body.appendChild(renderer.domElement);

//创建几何体
const geometry = new THREE.BoxGeometry(1, 1, 1);

//创建材质
const material = new THREE.MeshBasicMaterial({ color: 0x00ff00 });

//创建网格
const cube = new THREE.Mesh(geometry, material);

//将网格添加至场景中
scene.add(cube);

//设置相机位置
camera.position.z = 5;
camera.position.y = 2;
camera.position.x = 2;
camera.lookAt(0, 0, 0);

//添加世界坐标辅助器
const axesHelper = new THREE.AxesHelper(5);
scene.add(axesHelper);

//创建轨道控制器
const orbitControls = new OrbitControls(camera, renderer.domElement);
//设置带阻尼效果
orbitControls.enableDamping = true;
//设置阻尼系数
orbitControls.dampingFactor = 0.05;
//设置自动旋转
orbitControls.autoRotate = true;

// 创建间隔动画
const tween = new TWEEN.Tween(geometry.position);
tween.to({ x: 4 }, 5000);
tween.start();

//定义渲染函数
function animate() {
  orbitControls.update();
  requestAnimationFrame(animate);
  // cube.rotation.x += 0.01;
  // cube.rotation.y += 0.01;
  //渲染
  renderer.render(scene, camera);
  // tween.update();
}
animate();

//监听窗口变化
window.addEventListener("resize", () => {
  //重置渲染器宽高比
  renderer.setSize(window.innerWidth, window.innerHeight);
  //重置相机宽高比
  camera.aspect = window.innerWidth / window.innerHeight;
  //更新相机投影矩阵
  camera.updateProjectionMatrix();
})
</script>

<template>
  <div>

  </div>
</template>

<style>
* {
  margin: 0;
  padding: 0;
}

body {
  width: 100vw;
  height: 100vh;
}

canvas {
  display: block;
  position: fixed;
  left: 0;
  top: 0;
  width: 100vw;
  height: 100vh;
}
</style>
