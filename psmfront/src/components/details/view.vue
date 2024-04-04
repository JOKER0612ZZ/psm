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
                        <el-date-picker v-model="project.creationTime" type="datetime"></el-date-picker>
                    </el-form-item>
                    <el-form-item label="截止时间">
                        <el-date-picker v-model="project.deadline" type="datetime"></el-date-picker>
                    </el-form-item>
                </el-form>
                <div class="progress">
                    <h5>进度</h5>
                    <el-progress :stroke-width="20" :percentage="percent" />
                </div>
            </div>
        </div>
        <div class="percent" ref="chart">
        </div>
        <div class="pie"></div>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeMount, } from 'vue';
import { useProjectStore } from '@/store/project';
import { getProgress } from '@/api/project'
import { queryTaskStatus } from '@/api/task'
import echarts from '@/utils/echarts';
import { ECOption } from '@/utils/echarts';
const projectStore = useProjectStore()
const percent = ref(0)
const columndata = ref<any>()
let columnxAxis: string[] = []
let columnyAxisData: number[] = []
const chart = ref<HTMLElement | null>(null);
const option: ECOption = {
    title: {
        text: '需求完成情况'
    },
    xAxis: {
        type: 'category',
        data: columnxAxis,
    },
    yAxis: {
        type: 'value'
    },
    series: [
        {
            data: columnyAxisData,
            type: 'bar',
            color: '#53c8fa'
        }
    ]
};
let barChart
onMounted(async () => {
    columndata.value = await queryTaskStatus(project.value.projectId)
    for (const key in columndata.value) {
        columnxAxis.push(key)
        columnyAxisData.push(columndata.value[key])
    }
    barChart = echarts.init(chart.value)
    barChart.setOption(option)
});
onBeforeMount(async () => {
    project.value = projectStore.projectInfo
    
    percent.value = await getProgress(project.value.projectId)

})
let project = ref<any>({
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
    height: 55%;
    padding:10px 24px;
    background-color: white;

    p {
        text-align: left;
    }
}

.basic_ava {
    margin-right: 5px;
    font-size: 12px;
    background-color: rgba(83, 150, 255, 0.7);
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
            overflow: hidden;

            :deep(.el-input) {
                box-shadow: 0 0 0 0;
            }
        }

    }

    .progress {
        h5 {
            text-align: left;
            margin-top: 10px;
            margin-bottom: 10px;
            padding-left: 5px;
            font-size: 14px;
            font-weight: normal;
        }

        .el-progress {
            width: 180px;

            :deep(.el-progress__text) {
                font-size: 16px !important;
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
    padding:10px 10px;
    width: 65%;
    margin-top: 8px;
    height: 55%;
    background-color: white;
}
</style>