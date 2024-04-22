<template>
    <el-header>
        <span>{{ title }}</span>
        <create-project @inserted="loadData"></create-project>
    </el-header>
    <el-main>
        <el-table :data="projects" style="width: 100%" @row-click="goProjectDetail" v-if="page===1" :row-style="rowStyle">
            <el-table-column prop="projectName" label="项目名称" sortable />
            <el-table-column prop="mark" label="标识" />
            <el-table-column prop="projectStatus" label="项目状态"/>
            <el-table-column prop="creationTime" label="创建时间" sortable></el-table-column>
            <el-table-column prop="deadline" label="截止日期" sortable></el-table-column>
            <el-table-column prop="userName" label="负责人"></el-table-column>
        </el-table>
        <el-table :data="createProjects" style="width: 100%" @row-click="goProjectDetail" v-if="page===2" :row-style="rowStyle">
            <el-table-column prop="projectName" label="项目名称" sortable />
            <el-table-column prop="mark" label="标识" />
            <el-table-column prop="projectStatus" label="项目状态"/>
            <el-table-column prop="creationTime" label="创建时间"></el-table-column>
            <el-table-column prop="deadline" label="截止日期"></el-table-column>
        </el-table>
        <el-table :data="joinProjects" style="width: 100%" @row-click="goProjectDetail" v-if="page===3" :row-style="rowStyle">
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
import { ref ,onBeforeMount} from 'vue'
import { useUserStore } from '@/store/user'
import {Project} from "@/api/interface"
import { useProjectStore } from '@/store/project'
import {goProjectDetail} from '@/utils/tools'
import { queryUserProject } from '@/api/project'
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
const rowStyle = (_row: any, _index: number) => {
    return {
        height: '60px', 

    };
};
let projects = ref<Project[]>([])
let createProjects = ref<Project[]>([])
let joinProjects = ref<Project[]>([])
onBeforeMount(async()=>{
    loadData()
    queryUserProject()
})
const loadData=async()=>{
    projects.value=  projectStore.projects
    createProjects.value = projects.value.filter(project => project.creatorId === userStore.userInfo.userId)
    joinProjects.value = projects.value.filter(project => project.creatorId !== userStore.userInfo.userId)
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