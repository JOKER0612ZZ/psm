<template>
    <div>
        <el-button plain type="primary" @click="visible = true">添加任务</el-button>
        <el-dialog v-model="visible" class="task_update" title="新增任务" @close="close">
            <el-form ref="taskForm" :model="task" label-width="auto" label-position="top" :rules="formRules">
                <el-form-item label="标题" prop="title">
                    <el-input v-model="task.title"></el-input>
                </el-form-item>
                <el-form-item label="负责人" prop="assignName">
                    <el-select v-model="memberId">
                        <el-option v-for="item in members" :key="item.memberId" :label="item.memberName"
                            :value="item.memberId" />
                    </el-select>
                </el-form-item>
                <el-form-item label="描述" prop="description">
                    <el-input v-model="task.description"></el-input>
                </el-form-item>
                <el-form-item label="截止时间" prop="deadline">
                    <el-date-picker v-model="task.deadline" type="datetime" style="width: 100%;"></el-date-picker>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submit">确认</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script setup lang="ts">

import { Members } from '@/api/interface'
import { queryProjectMembers } from '@/api/team';
import { FormInstance, FormRules } from 'element-plus';
import { ref, reactive, onMounted } from 'vue'
import { createTask } from '@/api/task';
import { useUserStore } from '@/store/user';
import { useProjectStore } from '@/store/project';
const projectId = useProjectStore().projectInfo?.projectId
const userId = useUserStore().userInfo.userId
const members = ref<Members[]>([])
const memberId = ref<string>()
onMounted(async () => {
    members.value = await queryProjectMembers(projectId!)
})
defineProps({
    projectId: {
        type: Object as () => string,
        default: ''
    }
})
const validDeadline = (_rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('截止日期不能为空'))
    } else {
        const selectedTime = new Date(value).getTime(); // 获取所选时间的时间戳
        const currentTime = Date.now(); // 获取当前时间的时间戳
        if (selectedTime < currentTime) {
            callback(new Error('截止日期不能早于当前时间'));
        } else {
            callback();
        }

    }
}
const emit = defineEmits(['inserted'])
const formRules = reactive<FormRules>({
    title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
    status: [{ required: true, message: '请输入状态', trigger: 'blur' }],
    deadline: [{ required: true, validator:validDeadline, trigger: 'change' }]
});
const visible = ref(false)
const task = ref({
    projectId: projectId!,
    title: '',
    description: '',
    deadline: '',
    creatorId: userId,
    assignName: ''
});

const taskForm = ref<FormInstance>();
const close = () => {
    taskForm.value?.resetFields()
}
const submit = async () => {
    taskForm.value?.validate(async (valid: boolean) => {
        if (valid) {
            createTask(projectId!, task.value!, memberId.value!).then(() => {
                emit('inserted')
            })
            taskForm.value?.resetFields()
            visible.value = false
        }
    })

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
    margin-top: 20px;

    .el-button {
        position: relative;
        left: 80%;
        width: 100px;
    }
}
</style>