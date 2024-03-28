<template>
  <div class="context-menu">
    <span class="menu-item">
      <el-icon ><Folder/></el-icon>
      <span>新建文件夹</span>
    </span>
    <span class="menu-item">
      <el-icon><Document/></el-icon>
      <span>新建文件</span>
    </span>
  </div>
</template>

<script setup lang="ts">

</script>

<style scoped lang="scss">
.context-menu {
  z-index: 2000;
  position: fixed;
  background-color: rgba(255, 255, 255, 0.947); /* 更柔和的背景色 */
  border: 1px solid rgba(0, 0, 0, 0.1); /* 更柔和的边框 */
  border-radius: 4px; /* 圆角边框 */
  padding: 8px 0; /* 适当的上下内边距，左右内边距由.menu-item控制 */
  box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.2); /* 更柔和的阴影 */
}

.menu-item {
  display: flex;
  align-items: center;
  text-align: left;
  cursor: pointer;
  padding: 6px 12px; /* 适当的上下内边距，左右内边距 */
  transition: background-color 0.3s ease; /* 添加悬停效果的过渡动画 */
  margin: 0 4px /* 设置菜单项之间的垂直间距 */
}

.menu-item:hover {
  background-color: #bddeff; /* 更暗一些的 hover 背景色 */
  border-radius: 4px; /* 悬停时添加圆角 */
}

.el-icon {
  margin-right: 8px; /* 图标与文本之间的右边距 */
}
</style>
