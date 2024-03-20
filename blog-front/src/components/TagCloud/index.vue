<template>
  <div>
    <svg :height="height" :width="width" style="margin: auto">
      <a v-for="(tag, index) in tags" :key="`tag-${index}`" class="fontA">
        <text
            :id="tag.id"
            :fill-opacity="(400 + tag.z) / 600"
            :font-size="16 * (600 / (600 - tag.z))"
            :x="tag.x"
            :y="tag.y"
            @click="clickToPage(tag.text.id,tag.text.name)"
            @mousemove="listenerMove($event)"
            @mouseout="listenerOut($event)"
        >
          {{ tag.text.name }}
        </text>
      </a>
    </svg>
  </div>
</template>

<script setup>
import {computed, onMounted, ref} from "vue";
import {useRouter} from 'vue-router'

const router = useRouter()

const width = ref(240); //svg宽度
const height = ref(200); //svg高度
const tagsNum = ref(0); //标签数量
const RADIUS = ref(60); //球的半径
const speedX = ref(Math.PI / 360 / 1.5); //球一帧绕x轴旋转的角度
const speedY = ref(Math.PI / 360 / 1.5); //球-帧绕y轴旋转的角度
const tags = ref([]);

const data = ref([]);

const timer = ref(null);
const CX = computed(function () {
  //球心x坐标
  return width.value / 2 - 40;
});
const CY = computed(function () {
  //球心y坐标
  return height.value / 2;
});


// 初始化数据
function initData() {
  let tagsa = [];
  tagsNum.value = data.value.length;
  console.log(tagsNum.value);
  for (let i = 0; i < data.value.length; i++) {
    let tag = {};
    let k = -1 + (2 * (i + 1) - 1) / tagsNum.value;
    let a = Math.acos(k);
    let b = a * Math.sqrt(tagsNum.value * Math.PI); //计算标签相对于球心的角度
    tag.text = data.value[i];
    tag.x = CX.value + RADIUS.value * Math.sin(a) * Math.cos(b); //根据标签角度求出标签的x,y,z坐标
    tag.y = CY.value + RADIUS.value * Math.sin(a) * Math.sin(b);
    tag.z = RADIUS.value * Math.cos(a);
    tag.id = i; // 给标签添加id
    tagsa.push(tag);
  }
  tags.value = tagsa; //让vue替我们完成视图更新
}

// 纵向旋转
const rotateX = (angleX) => {
  var cos = Math.cos(angleX);
  var sin = Math.sin(angleX);
  for (let tag of tags.value) {
    var y1 = (tag.y - CY.value) * cos - tag.z * sin + CY.value;
    var z1 = tag.z * cos + (tag.y - CY.value) * sin;
    tag.y = y1;
    tag.z = z1;
  }
};
// 横向旋转
const rotateY = (angleY) => {
  var cos = Math.cos(angleY);
  var sin = Math.sin(angleY);
  for (let tag of tags.value) {
    var x1 = (tag.x - CX.value) * cos - tag.z * sin + CX.value;
    var z1 = tag.z * cos + (tag.x - CX.value) * sin;
    tag.x = x1;
    tag.z = z1;
  }
};
// 运动函数
const runTags = () => {
  if (typeof timer === "number") {
    clearInterval(timer);
    timer.value = null;
  }
  timer.value = setInterval(() => {
    rotateX(speedX.value);
    rotateY(speedY.value);
  }, 17);
};
// 监听移入事件
const listenerMove = (e) => {
  if (e.target.id) {
    clearInterval(timer.value);
  }
};
// 监听移出事件
const listenerOut = (e) => {
  if (e.target.id) {
    runTags();
  }
};
// 点击事件

const clickToPage = (id, name) => {
  console.log('跳转 id' + id)
  router.push({path: '/tag/list', query: {id: id, name: name}})
}
onMounted(() => {
  data.value = ["java", "css"];
  initData();

  runTags();
});
</script>

<style lang="scss" scoped>
.fontA {
  fill: rgb(90, 188, 250);
  font-weight: bold;
}

.fontA:hover {
  fill: plum;
  cursor: pointer;
}
</style>

