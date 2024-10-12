import * as THREE from "three"; // 导入three
import { OrbitControls } from "three/examples/jsm/controls/OrbitControls.js"; //导入轨道控制器
import { GLTFLoader } from "three/examples/jsm/loaders/GLTFLoader.js"
import { log } from "three/webgpu";


//创建场景
const scene = new THREE.Scene();


//创建GLTFLoader
const loader = new GLTFLoader();
loader.load('/artist.glb', function (gltf) {
  console.log(gltf);
  scene.add(gltf.scene);
})

const directionalLight = new THREE.DirectionalLight(0xffffff, 1.0);
directionalLight.position.set(80, 100, 50);
scene.add(directionalLight);
directionalLight.intensity = 3.0;

const axesHelper = new THREE.AxesHelper(200);
scene.add(axesHelper);

const w = window.innerWidth;
const h = window.innerHeight;
const camera = new THREE.PerspectiveCamera(30, w / h, 1, 3000);
camera.position.set(400, 400, 400);
camera.lookAt(0, 0, 0);

const renderer = new THREE.WebGLRenderer({
  antialias: true
});
renderer.setPixelRatio(window.devicePixelRatio);
renderer.setSize(w, h);
document.body.appendChild(renderer.domElement);

function render() {
  renderer.render(scene, camera);
  requestAnimationFrame(render);
}
render();

//创建轨道控制器
const orbitControls = new OrbitControls(camera, renderer.domElement);
//设置带阻尼效果
orbitControls.enableDamping = true;
//设置阻尼系数
orbitControls.dampingFactor = 0.05;
//设置自动旋转
orbitControls.autoRotate = true;