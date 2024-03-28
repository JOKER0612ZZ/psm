<template>
    <div class="myHeader">
        <span>
            <svg t="1710422761537" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
                p-id="3493" width="30" height="30" @click="goDetails">
                <path
                    d="M39.424 529.92v356.352c0 57.088 46.336 103.424 103.424 103.424h553.216c57.088 0 103.424-46.336 103.424-103.424V529.92H39.424zM696.064 211.2H142.848c-57.088 0-103.424 46.336-103.424 103.424V468.48h759.808v-153.856c0.256-57.088-46.336-103.424-103.168-103.424zM215.04 407.552c-18.688 0-33.792-15.104-33.792-33.792 0-18.688 15.104-33.792 33.792-33.792s33.792 15.104 33.792 33.792c0 18.432-15.104 33.792-33.792 33.792z m430.08-8.192H319.232c-15.616 0-28.16-12.544-28.16-28.16s12.544-28.16 28.16-28.16H645.12c15.616 0 28.16 12.544 28.16 28.16s-12.544 28.16-28.16 28.16z"
                    fill="#5396FF" p-id="3494"></path>
                <path
                    d="M874.752 897.024h-30.72V262.912c0-50.432-40.96-91.392-91.392-91.392H178.432v-16.64c0-57.088 46.08-103.168 103.168-103.168h578.56c63.232 0 114.432 51.2 114.432 114.432v630.784c0 55.296-44.8 100.096-99.84 100.096z"
                    fill="#5396FF" p-id="3495"></path>
            </svg>
            <el-icon size="20">
                <ArrowRightBold />
            </el-icon>
        </span>
        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" :ellipsis="false">
            <el-menu-item index="1">
                <router-link :to="`/home/project/details/${project.mark}/view`" style="display: block; height: 100%;">
                    概览
                </router-link>
            </el-menu-item>
            <el-menu-item index="2">
                <router-link :to="`/home/project/details/${project.mark}/demand`" style="display: block; height: 100%;">
                    需求
                </router-link>
            </el-menu-item>

            <el-menu-item index="3">设置</el-menu-item>
            <el-menu-item index="4">
                <router-link :to="`/home/project/details/${project.mark}/projectFile`" style="display: block; height: 100%;">
                    文件
                </router-link>
            </el-menu-item>
        </el-menu>
    </div>
    <div class="myBody">
        <router-view></router-view>
    </div>
</template>

<script setup lang="ts">
import { ref, onBeforeMount } from 'vue';
import eventBus from '@/utils/event';
import { useProjectStore } from '@/store/project';
import { useRouter } from 'vue-router';
import { projectMain } from '@/utils/tools';
onBeforeMount(() => {
    project.value = projectStore.projectInfo
})
const router = useRouter()
const projectStore = useProjectStore()
let project = ref<any>({
})

eventBus.on('project', (data) => {
    projectStore.projectInfo = data
})
const activeIndex = ref('1')
const goDetails = () => {
    projectMain()
    router.replace('/home/project')
}
</script>

<style scoped lang="scss">
@import url(../../../css/index.css);

.myHeader {
    svg {
        &:hover {
            opacity: 0.5;
        }
    }
}

.el-menu-demo {
    height: 100%;
}
</style>