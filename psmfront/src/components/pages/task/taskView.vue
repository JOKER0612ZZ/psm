<template>
    <el-header>
        <span>{{ title }}</span>
    </el-header>
    <el-main>
        

        
    </el-main>
</template>

<script setup lang="ts">
import { defineProps } from 'vue'
import { Task } from '@/api/interface'
defineProps({
    title: {
        type: Object as () => string,
        require: true
    },
    taskList: {
        type: Object as () => Task[],
        require: true
    }
})

</script>

<style scoped lang="scss">
.el-header {
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    justify-content: space-between;
    align-items: center;
    padding: 0 40px;
    height: 100px;

    span {
        font-size: 20px;
    }
}
</style>