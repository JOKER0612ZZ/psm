<template>
    <el-header>
        <span>全部项目</span>
        <create></create>
    </el-header>
    <el-main>
        <el-table :data="projects" style="width: 100%" @row-click="goProjectDetail" v-if="stepStore.currentStep===1">
            <el-table-column prop="projectName" label="项目名称" sortable />
            <el-table-column prop="mark" label="标识" />
            <el-table-column prop="projectStatus" label="项目状态"/>
            <el-table-column prop="creationTime" label="创建时间"></el-table-column>
            <el-table-column prop="deadline" label="截止日期"></el-table-column>
            <el-table-column prop="userName" label="负责人"></el-table-column>
        </el-table>
        <el-table :data="createProjects" style="width: 100%" @row-click="goProjectDetail" v-if="stepStore.currentStep===2">
            <el-table-column prop="projectName" label="项目名称" sortable />
            <el-table-column prop="mark" label="标识" />
            <el-table-column prop="projectStatus" label="项目状态"/>
            <el-table-column prop="creationTime" label="创建时间"></el-table-column>
            <el-table-column prop="deadline" label="截止日期"></el-table-column>
        </el-table>
        <el-table :data="joinProjects" style="width: 100%" @row-click="goProjectDetail" v-if="stepStore.currentStep===3">
            <el-table-column prop="projectName" label="项目名称" sortable />
            <el-table-column prop="mark" label="标识" />
            <el-table-column prop="projectStatus" label="项目状态"/>
            <el-table-column prop="creationTime" label="创建时间"></el-table-column>
            <el-table-column prop="deadline" label="截止日期"></el-table-column>
        </el-table>
    </el-main>
</template>

<script setup lang="ts">
import create from './create.vue'
import { ref,onMounted ,onBeforeMount} from 'vue';
import { queryUserProject } from '@/api/project';
import { useUserStore } from '@/store/user';
import { useStepStore } from '@/store/step';
import eventBus from '@/utils/event';
import { useRouter } from "vue-router"
const router = useRouter()
const userStore = useUserStore()
const stepStore = useStepStore()
interface Project{
    creationTime:string,
    creatorId:string,
    deadline:string,
    description:string,
    mark:string,
    projectId:string,
    projectName:string,
    projectStatus:string,
    userName:string
}
let projects = ref<Project[]>([])
let createProjects = ref<Project[]>([])
let joinProjects = ref<Project[]>([])
eventBus.on('step', (data) => {
    stepStore.currentStep = data;
});
onBeforeMount(async()=>{
    projects.value=  await queryUserProject(userStore.userInfo.data.userId)
    createProjects.value = projects.value.filter(project => project.creatorId === userStore.userInfo.data.userId)
    joinProjects.value = projects.value.filter(project => project.creatorId !== userStore.userInfo.data.userId)
    console.log(projects.value)
})
const goProjectDetail = (row:any) =>{
    eventBus.emit('project',row)
    console.log(row)
    eventBus.emit('projectDetails',true)
    router.push(`/home/project/details/${row.mark}`)
}

// filterNotmetchant(){
//             if(this.userInfo.userType=="商家"){
//                 let list =this.productInfoList.filter(product=>product.userId !== this.userInfo.userId)

//                 this.productInfoList=list
//             }else if(this.userInfo.userType=="普通用户"){

//                 let list =this.productInfoList.filter(product=>product.productStatus ==1)
                
//                 this.productInfoList=list

//             }
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