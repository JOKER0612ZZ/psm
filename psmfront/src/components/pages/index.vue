<template>
    <div>
        <el-button type="primary" @click="search()">search</el-button>
    </div>
</template>

<script setup lang="ts">
import { queryUser } from '@/api/user';
const search = () => {
    queryUser()
}
</script>

<style scoped lang="scss">

</style>