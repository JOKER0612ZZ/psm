<template>
    <el-header>
        <span>我加入的项目</span>
        <el-button type="primary">+ 新建项目</el-button>
    </el-header>
    <el-main></el-main>
</template>

<script setup lang="ts">

</script>

<style scoped lang="scss">
.el-header{
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    justify-content: space-between;
    align-items: center;
    padding: 0 40px;
    height: 100px;
    span{
        font-size: 20px;
    }
}
</style>