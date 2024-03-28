<template>
    <div>
        <el-button plain type="primary" @click="visible=true">添加任务</el-button>
        <el-dialog v-model="visible" class="task_update" title="新增任务">
            <el-form ref="taskForm" :model="task" label-width="auto" label-position="top" :rules="formRules">
                <el-form-item label="标题"  :show-message="formStatus.title">
                    <el-input v-model="task.title"></el-input>
                </el-form-item>
                <el-form-item label="负责人" prop="assignName" :show-message="formStatus.assignName">
                    <el-select></el-select>
                </el-form-item>
                <el-form-item label="状态" prop="status" :show-message="formStatus.status">
                    <el-input v-model="task.status"></el-input>
                </el-form-item>
                <el-form-item label="描述" prop="description" :show-message="formStatus.description">
                    <el-input v-model="task.description"></el-input>
                </el-form-item>
                <el-form-item label="创建时间" prop="creationTime" :show-message="formStatus.creationTime">
                    <el-date-picker v-model="task.creationTime" type="datetime" style="width: 100%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="截止时间" prop="deadline" :show-message="formStatus.deadline">
                    <el-date-picker v-model="task.deadline" type="datetime" style="width: 100%;"></el-date-picker>
                </el-form-item>
                <el-form-item label="所属项目" prop="projectId" v-if="!projectId">
                    <el-select>
                    </el-select>    
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submit">确认</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script setup lang="ts">
import { Task } from '@/api/interface'
import { ref } from 'vue'
defineProps({
    projectId:{
        type: Object as () =>string,
    }
})
const formRules = {
    title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
    status: [{ required: true, message: '请输入状态', trigger: 'blur' }],
    description: [{ required: true, message: '请输入描述', trigger: 'blur' }],
    creationTime: [{ required: true, message: '请选择创建时间', trigger: 'change' }],
    deadline: [{ required: true, message: '请选择截止时间', trigger: 'change' }]
};
const visible = ref(false)
const task = ref<Task>({
    taskId: '',
    projectId: '',
    title: '',
    description: '',
    creationTime: '',
    deadline: '',
    creatorId: '',
    status: '',
    userName: '',
    assignName: '',
    projectName:''
});

const taskForm = ref<any>();
const formStatus = {
    title: false,
    assignName: false,
    status: false,
    description: false,
    creationTime: false,
    deadline: false
}
const submit = async () => {
    // taskForm.value.validate(async (valid: boolean) => {
    //     if (valid) {
    //         updateTask(task.value!)
    //         visible.value=false
    //     } else {
    //         // 表单验证未通过，显示错误消息
    //         if (taskForm.value && taskForm.value.fields) {
    //             for (const key in formStatus) {
    //                 formStatus[key] = !!taskForm.value.fields[key]?.validateState;
    //             }
    //         }

    //     }
    // })
}
</script>

<style scoped lang="scss">
:deep(.task_update) {
    width: 500px;
    margin: 50px auto;
}

.el-form {
    display: flex;
    flex-direction: column;
    width: 90%;
    margin: 0 auto;

    .el-button {
        position: relative;
        left: 80%;
        width: 100px;
    }
}
</style>