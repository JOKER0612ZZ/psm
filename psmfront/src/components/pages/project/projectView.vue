<template>
    <el-header>
        <span>{{ title }}</span>
        <create-project></create-project>
    </el-header>
    <el-main>
        <el-table :data="projects" style="width: 100%" @row-click="goProjectDetail" v-if="page===1">
            <el-table-column prop="projectName" label="项目名称" sortable />
            <el-table-column prop="mark" label="标识" />
            <el-table-column prop="projectStatus" label="项目状态"/>
            <el-table-column prop="creationTime" label="创建时间"></el-table-column>
            <el-table-column prop="deadline" label="截止日期"></el-table-column>
            <el-table-column prop="userName" label="负责人"></el-table-column>
        </el-table>
        <el-table :data="createProjects" style="width: 100%" @row-click="goProjectDetail" v-if="page===2">
            <el-table-column prop="projectName" label="项目名称" sortable />
            <el-table-column prop="mark" label="标识" />
            <el-table-column prop="projectStatus" label="项目状态"/>
            <el-table-column prop="creationTime" label="创建时间"></el-table-column>
            <el-table-column prop="deadline" label="截止日期"></el-table-column>
        </el-table>
        <el-table :data="joinProjects" style="width: 100%" @row-click="goProjectDetail" v-if="page===3">
            <el-table-column prop="projectName" label="项目名称" sortable />
            <el-table-column prop="mark" label="标识" />
            <el-table-column prop="projectStatus" label="项目状态"/>
            <el-table-column prop="creationTime" label="创建时间"></el-table-column>
            <el-table-column prop="deadline" label="截止日期"></el-table-column>
        </el-table>
    </el-main>
</template>

<script setup lang="ts">
import createProject from './createProject.vue'
import { ref ,onBeforeMount,defineProps} from 'vue'
import { queryUserProject } from '@/api/project'
import { useUserStore } from '@/store/user'
import { useProjectStore } from '@/store/project'
import { useRouter } from "vue-router"
import {Project} from "@/api/interface"
import eventBus from '@/utils/event'
const router = useRouter()
const userStore = useUserStore()
const projectStore = useProjectStore()
defineProps({
    title:{
        type: Object as () => string,
        required: true 
    },
    page:{
        type: Object as () => number,
        required: true 
    }
})
let projects = ref<Project[]>([])
let createProjects = ref<Project[]>([])
let joinProjects = ref<Project[]>([])
onBeforeMount(async()=>{
    projects.value=  await queryUserProject(userStore.userInfo.data.userId)
    createProjects.value = projects.value.filter(project => project.creatorId === userStore.userInfo.data.userId)
    joinProjects.value = projects.value.filter(project => project.creatorId !== userStore.userInfo.data.userId)
    console.log(projects.value)
})
const goProjectDetail = (row:any) =>{
    projectStore.setProjectInfo(row)
    eventBus.emit('projectDetails',true)
    router.push(`/home/project/details/${row.mark}`)
}

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