<template>
    <div class="myHeader"></div>
    <div class="myBody">
        <div class="aside">
            <span>项目</span>
            <div class="aside_menu">
                <a @click="pageOne">全部项目</a>
                <a @click="pageTwo">我创建的项目</a>
                <a @click="pageThree">我加入的项目</a>
            </div>
        </div>
        <div class="main">
            <show-project></show-project>
        </div>
    </div>
</template>

<script setup lang="ts">
import showProject from '@/components/pages/project/showProject.vue'
import eventBus from '@/utils/event';
const pageOne = ()=>{
    eventBus.emit("step",1)
}
const pageTwo = ()=>{
    eventBus.emit("step",2)
}
const pageThree = ()=>{
    eventBus.emit("step",3)
}

</script>

<style scoped lang="scss">
@import url('../../css/index.css');
</style>