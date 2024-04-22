<template>
    <div class="overview">
        <div class="basic_info">
            <div style="display: flex;flex-direction: row;align-items: center;justify-content: space-between;">
                <p>基本信息</p>
                <el-icon @click="edit" size="20" class="edit" v-if="userInfo.userId === projectInfo?.creatorId">
                    <Edit />
                </el-icon>
            </div>
            <div class="basic_pannel">
                <el-form label-width="auto" label-position="top" :model="project">
                    <el-form-item label="负责人">
                        <el-avatar :size="20" style="" class="basic_ava">{{ project.userName }}</el-avatar>
                        <el-text>{{ project.userName }}</el-text>
                    </el-form-item>
                    <el-form-item label="状态">
                        <normal-status :status="resultProject.projectStatus" v-if="isEdit"></normal-status>
                        <project-status :status="resultProject.projectStatus" :status-width="'100px'" v-else
                            @changed="updateStatus"></project-status>
                    </el-form-item>
                    <el-form-item label="创建时间">
                        <el-date-picker v-model="resultProject.creationTime" type="datetime" :readonly="isEdit"
                            @change="reviseProject()"></el-date-picker>
                    </el-form-item>
                    <el-form-item label="截止时间">
                        <el-date-picker v-model="resultProject.deadline" type="datetime"
                            :readonly="isEdit" @change="reviseProject()"></el-date-picker>
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
        <div class="ganter">
            <div class="operation" v-if="userInfo?.userId === projectInfo?.creatorId">
                <el-upload :on-change="handleChange" :http-request="handleUpload" ref="uploadRef">
                    <template #trigger>
                        <el-button type="primary">导入</el-button>
                    </template></el-upload>
                <el-button class="download"><a :href="panel"
                        download="计划模板">下载模板</a></el-button>
            </div>
            <div class="ganterChart" ref="ganterChart" v-if="showGanter"></div>
            <h3 v-else>暂无数据</h3>
        </div>
    </div>

</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeMount, reactive, } from 'vue';
import { useProjectStore } from '@/store/project';
import { getPlan, getProgress, queryUserProject, setPlan, updateProject } from '@/api/project'
import { queryTaskStatus } from '@/api/task'
import echarts from '@/utils/echarts';
import { ECOption } from '@/utils/echarts';
import { ElMessage, UploadFile } from 'element-plus';
import { useUserStore } from '@/store/user';
import normalStatus from '../pages/common/normalStatus.vue';
import projectStatus from '../pages/common/projectStatus.vue';
import { Project, ResultProject } from '@/api/interface';
import panel from '@/assets/template/plan-template.xlsx';
const projectStore = useProjectStore()
const userInfo = useUserStore().userInfo
const projectInfo = projectStore.projectInfo
const projectId = projectStore.projectInfo?.projectId
const resultProject: ResultProject = reactive({
    creationTime: new Date(projectInfo?.creationTime!),
    creatorId: projectInfo?.creatorId!,
    deadline: new Date(projectInfo?.deadline!),
    description: projectInfo?.description!,
    mark: projectInfo?.mark!,
    projectId: projectInfo?.projectId!,
    projectName: projectInfo?.projectName!,
    projectStatus: projectInfo?.projectStatus!
})
const updateStatus = (data: any) => {
    resultProject.projectStatus = data
    reviseProject()
}
const reviseProject = () => {
    updateProject(resultProject).then(async()=>{
        const projectDatas:Project[] = await queryUserProject()
        const project:any = projectDatas.find(p=>p.projectId===projectId)
        useProjectStore().setProjectInfo(project)
    })
}
const percent = ref(0)
const columndata = ref<any>()
let columnxAxis: string[] = []
let columnyAxisData: number[] = []
const chart = ref<HTMLElement | null>(null)
const ganterChart = ref<HTMLElement | null>(null)
let formData = new FormData()
const uploadRef = ref<any>()
const isEdit = ref(true)

const edit = () => {
    if (isEdit.value === true) {
        isEdit.value = false
    } else {
        isEdit.value = true
    }


}
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
            color: '#53c8fa',
            label: {
                show: true,
                position: 'top'
            },
        }
    ]
};
interface Plan {
    phaseName: string,
    startTime: Date,
    endTime: Date
}
let startTime: string[] = []
let endTime: string[] = []
let phaseName: string[] = []
let plan: Plan[] = []
let barChart
const showGanter = ref(false)
onMounted(async () => {
    columndata.value = await queryTaskStatus(project.value.projectId)

    for (const key in columndata.value) {
        columnxAxis.push(key)
        columnyAxisData.push(columndata.value[key])
    }
    barChart = echarts.init(chart.value)
    barChart.setOption(option)

});
const loadGanterData = async () => {
    plan = await getPlan(projectStore.projectInfo?.projectId!)
    console.log('plan: ', plan);
    if (plan) {
        showGanter.value = true
        startTime = plan.map(item => new Date(item.startTime).toISOString().substring(0, 10));
        endTime = plan.map(item => new Date(item.endTime).toISOString().substring(0, 10));
        phaseName = plan.map((item: Plan) => item.phaseName)
    }

}
onBeforeMount(async () => {
    project.value = projectStore.projectInfo
    percent.value = await getProgress(project.value.projectId)
    await loadGanterData()
    intitGanter()
})
let project = ref<any>({})
const intitGanter = () => {
    const optionGanter = {
        title: {
            text: `项目计划甘特图`,
        },
        tooltip: {
            trigger: 'axis',
            formatter(params: { data: any; }[]) {
                if (params[1].data && params[0].data) {
                    return `<div>开始时间：${params[0].data}</div>` + `<div>结束时间：${params[1].data}</div>`
                } else {
                    return ''
                }
            },
            axisPointer: {
                type: 'shadow'
            }
        },
        xAxis: {
            type: 'time',
            position: 'top', // x 轴位置
            axisTick: {
                show: false
            },
            axisLine: {
                show: false
            },
            splitLine: {
                show: true
            }
        },
        yAxis: {
            inverse: true,
            axisTick: {
                show: false
            },
            axisLine: {
                show: false
            },
            data: phaseName
        },
        series: [
            {
                name: '开始时间',
                type: 'bar',
                stack: 'duration',
                itemStyle: {
                    color: '#fff'
                },
                zlevel: -1,
                z: 9,
                data: startTime
            },
            {
                name: '结束时间',
                type: 'bar',
                stack: 'duration',
                itemStyle: {
                    borderColor: '#fff',
                    borderWidth: 1
                },
                zlevel: -1,
                data: endTime
            },
        ],
        Animation: true
    }

    const ganter = echarts.init(ganterChart.value)
    ganter.setOption(optionGanter)
}
const handleChange = (uploadFile: UploadFile) => {
    if (uploadFile.raw?.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet') {
        formData.append('plan', uploadFile.raw!);
        console.log(formData.getAll('plan'))
    } else {
        ElMessage.error('只能上床excel文件')
    }
}
const handleUpload = () => {
    setPlan(formData).then(async () => {
        await loadGanterData()
        intitGanter()
    })
    formData = new FormData()
    uploadRef.value?.clearFiles();
    return false
}
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
    max-width: 1290px;
}

.edit {
    &:hover {
        color: #53c8fa;
    }
}

.basic_info {

    position: relative;
    border-radius: 2px;
    outline: 1px solid #eee;
    z-index: 1;
    display: block;
    margin-top: 8px;
    width: 31%;
    height: 55%;
    padding: 10px 24px;
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
    padding: 10px 10px;
    width: 59%;
    margin-top: 8px;
    height: 55%;
    background-color: white;
}

.ganter {
    position: relative;
    z-index: 1;
    display: block;
    outline: 1px solid #eee;
    margin: 20px;
    padding: 10px 10px;
    width: 100%;
    margin-top: 8px;
    background-color: white;

    .ganterChart {
        height: 500px;
    }

    .operation {
        display: flex;
        flex-direction: row;
        justify-content: space-around;

        .download {
            margin-left: 40px;
            padding: 0;

            a {
                display: block;
                padding: 8px 15px;
                width: 100%;
                height: 100%;
                color: inherit;
                text-decoration: none;
            }
        }
    }
}
</style>