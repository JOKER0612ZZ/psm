<template>
    <div style="display: inline-block;">
        <div v-if="status == '未分配'" class="unditribute">{{ status }}</div>
        <div v-if="status == '进行中'" class="afoot">{{ status }}</div>
        <div v-if="status == '审核中'" class="in-review">{{ status }}</div>
        <div v-if="status == '已完成'" class="completed">{{ status }}</div>
        <div v-if="status == '已延期'" class="delayed">{{ status }}</div>
        <div v-if="status == '已截止'" class="expired">{{ status }}</div>
    </div>
</template>

<script setup lang="ts">
defineProps({
    status: {
        type: String,
        required: true
    }
})


</script>

<style scoped lang="scss">
.unditribute {
    background-color: #909399;
    color: #fff;
    border-radius: 10px;
    width: 65px;
    text-align: center;
}

.afoot {
    background-color: #409EFF;
    color: #fff;
    border-radius: 10px;
    width: 65px;
    text-align: center;
}

.in-review {
    background-color: #E6A23C;
    color: #fff;
    border-radius: 10px;
    margin: 5px;
    width: 65px;
    text-align: center;
}

.completed {
    background-color: #67C23A;
    color: #fff;
    border-radius: 10px;
    width: 65px;
    text-align: center;
}

.delayed {
    background-color: #F56C6C;
    color: #fff;
    border-radius: 10px;
    width: 65px;
    text-align: center;
}

.expired {
    background-color: #c45656;
    color: #fff;
    border-radius: 10px;
    width: 65px;
    text-align: center;
}
</style>