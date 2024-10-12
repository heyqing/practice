import * as THREE from "three"; // 导入three
import { OrbitControls } from "three/examples/jsm/controls/OrbitControls.js"; //导入轨道控制器


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
// const geometry = new THREE.BoxGeometry(1, 1, 1);
const geometry = new THREE.BufferGeometry();
// 创建顶点数据
// const vertices = new Float32Array([
//   -1.0, -1.0, 0.0,
//   1.0, -1.0, 0.0,
//   1.0, 1.0, 0.0
// ])
//使用索引值创建顶点数据-公用顶点
const vertices = new Float32Array([
  -1.0, -1.0, 0.0,
  1.0, -1.0, 0.0,
  1.0, 1.0, 0.0,
  -1.0, 1.0, 0.0
])

//设置顶点属性
geometry.setAttribute("position", new THREE.BufferAttribute(vertices, 3));
//创建索引
const indices = new Uint16Array([0, 1, 2, 2, 3, 0]);
//设置索引
geometry.setIndex(new THREE.BufferAttribute(indices, 1));
//设置两个顶点组，形成两个材质
geometry.addGroup(0, 3, 0);
geometry.addGroup(3, 3, 1);

//创建材质
const material0 = new THREE.MeshBasicMaterial({ color: 0x00ff00 });//绿
const material1 = new THREE.MeshBasicMaterial({ color: 0xff0000 });//红

//创建网格
const cube = new THREE.Mesh(geometry, [material0, material1]);

//将网格添加至场景中
scene.add(cube);

//设置相机位置
camera.position.z = 5;
camera.lookAt(0, 0, 0);

//创建轨道控制器
const orbitControls = new OrbitControls(camera, renderer.domElement);
//设置带阻尼效果
orbitControls.enableDamping = true;
//设置阻尼系数
orbitControls.dampingFactor = 0.05;
//设置自动旋转
orbitControls.autoRotate = true;

//定义渲染函数
function animate() {
  requestAnimationFrame(animate);
  cube.rotation.x += 0.01;
  cube.rotation.y += 0.01;
  //渲染
  renderer.render(scene, camera);
}

animate();