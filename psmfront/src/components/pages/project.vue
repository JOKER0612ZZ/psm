<template>
    <div class="myHeader"></div>
    <div class="myBody">
        <div class="aside">
            <span>项目</span>
            <div class="aside_menu">
                <router-link to="/home/project/totalPro">
                    全部项目
                </router-link>
                <router-link to="/home/project/createPro">
                    我创建的项目
                </router-link>
                <router-link to="/home/project/joinPro">
                    我加入的项目
                </router-link>
            </div>
        </div>
        <div class="main">
            <router-view></router-view>
        </div>
    </div>
</template>

<script setup lang="ts">

</script>

<style scoped lang="scss">
@import url('../../css/index.css');
</style>