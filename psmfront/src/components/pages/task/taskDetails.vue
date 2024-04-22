<template>
    <el-header>
        <span>{{ task.title }}</span>
    </el-header>
    <el-main>
        <div class="viewtask">
            <div class="item">
                <span>项目状态：<normal-status :status="task.status"></normal-status></span>
                <span>创建者：{{ task.creatorName }}</span>
                <span>负责人：{{ task.assignName }}</span>
            </div>
            <div class="item">
                <div class="time">
                    创建时间：<el-date-picker type="datetime" v-model="task.creationTime" :readonly="true"/>
                </div>
                <div class="time">
                    截止时间：<el-date-picker type="datetime" v-model="task.deadline" :readonly="true"/>
                </div>
            </div>
        </div>
        <task-upload :psmfile="file!" :task-id="task.taskId"></task-upload>
    </el-main>
</template>

<script setup lang="ts">
import { onBeforeMount, ref } from 'vue';
import { MyUploadFile, PsmFile, Task } from '@/api/interface'
import taskUpload from './taskUpload.vue';
import normalStatus from '../common/normalStatus.vue';
import { useUserStore } from '@/store/user';
import { useFileStore } from '@/store/file';
import { queryFileByUserId } from '@/api/file';
import { getCurrentDate } from '@/utils/dateUtils';
const files = useFileStore()
let parentFile: PsmFile
let file =ref<MyUploadFile>();
onBeforeMount(async () => {
    files.setFiles(await queryFileByUserId())
    parentFile = files.files?.find(file => file.projectId === props.task.projectId)!
    console.log(parentFile.filePath+'/'+props.task.title+' '+getCurrentDate())
    file.value = {
        filePath: parentFile.filePath+'/'+props.task.title+' '+getCurrentDate(),
        uploaderId: userId,
        uploaderTime: '',
        projectId: props.task.projectId,
        type: 'file',
        parentId: parentFile.fileId,
    }
})
const userId = useUserStore().userInfo.userId
const props = defineProps({
    task: {
        type: Object as () => Task,
        required: true
    }
})




</script>

<style scoped lang="scss">
.el-header {
    display: flex;
    height: 40px;
    align-items: center;

    span {
        font-size: 20px;
        font-weight: bold;
    }
}

.viewtask {
    display: flex;
    flex-direction: column;

    .item {
        display: flex;
        flex-direction: row;
        width: 100%;
        margin-top: 10px;
        margin-bottom: 10px;
        align-items: center;
        span {
            width: 25%;
        }

        .time {
            width: 48%;
        }
    }
}
</style>