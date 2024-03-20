<template>
    <div class="overview">
        <div class="basic_info">
            <p>基本信息</p>
            <div class="basic_pannel">
                <el-form label-width="auto" label-position="top" :model="project">
                    <el-form-item label="负责人">
                        <el-avatar :size="20" style="" class="basic_ava">{{ project.userName }}</el-avatar>
                        <el-text>{{ project.userName }}</el-text>
                    </el-form-item>
                    <el-form-item label="状态">
                        {{ project.projectStatus }}
                    </el-form-item>
                    <el-form-item label="创建时间">
                        <el-input v-model="project.creationTime" readonly></el-input>
                    </el-form-item>
                    <el-form-item label="截止时间">
                        <el-input v-model="project.deadline" readonly></el-input>
                    </el-form-item>
                </el-form>
            </div>
        </div>
        <div class="percent"></div>
    </div>
</template>

<script setup lang="ts">
import { ref,onBeforeMount } from 'vue';
import eventBus from '@/utils/event';
import { useProjectStore } from '@/store/project';
onBeforeMount(() => {
    project.value = projectStore.projectInfo
})
const projectStore = useProjectStore()
let project = ref<any>({
})

eventBus.on('project', (data) => {
    projectStore.projectInfo = data
})
</script>

<style scoped lang="scss">
.overview {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    background-color: #fafafa;
    width: 100%;
    height: 100%;
    
    overflow: auto;
    justify-content: space-evenly;
}

.basic_info {
    
    position: relative;
    border-radius: 2px;
    outline: 1px solid #eee;
    z-index: 1;
    display: block;
    margin-top: 8px;
    width: 25%;
    height: 60%;
    padding: 0 24px;
    background-color: white;

    p {
        text-align: left;
    }
}

.basic_ava {
    margin-right: 5px;
    font-size: 12px;
    background-color: rgba(83, 150, 255,0.7);
}

.basic_pannel {
    .el-form {
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;

        .el-form-item {
            align-items: center;
            padding: 10px;
            padding-right: 0;
            margin-bottom: 15px;
            width: 45%;

            :deep(.el-input) {
                box-shadow: 0 0 0 0;
            }
        }

    }
}

.percent {
    position: relative;
    border-radius: 2px;
    outline: 1px solid #eee;
    z-index: 1;
    display: block;
    width: 70%;
    margin-top: 8px;
    height: 60%;
    background-color: white;
}
</style>