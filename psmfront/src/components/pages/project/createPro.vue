<template>
    <el-header>
        <span>我创建的项目</span>
        <create></create>
    </el-header>
    <el-main></el-main>
</template>

<script setup lang="ts">
import create from './create.vue'
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