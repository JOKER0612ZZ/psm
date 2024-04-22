<template>
    <div>
        <el-button plain type="primary" @click="visible = true"><el-icon>
                <Plus />
            </el-icon>新建项目</el-button>
        <el-dialog v-model="visible" :show-close="false" width="500" @close="close">
            <template #header="{ titleId, titleClass }">
                <div class="dialog_header">
                    <h4 :id="titleId" :class="titleClass">创建项目</h4>
                    <el-icon @click="close()">
                        <Close />
                    </el-icon>
                </div>
            </template>
            <div class="dialog_body" v-if="stepStore.currentStep === 1">
                <el-form label-position="top" size="large" :model="projectForm" ref="projectFormRef" 
                 :rules="ProjectFormRules">
                    <el-form-item label="项目名称" prop="projectName" >
                        <el-input type="text" v-model="projectForm.projectName" maxlength="255"
                            show-word-limit></el-input>
                    </el-form-item>
                    <el-form-item label="项目标识" prop="mark">
                        <el-input type="text" v-model="projectForm.mark"></el-input>
                    </el-form-item>
                    <el-form-item label="截止时间" prop="deadline" >
                        <el-date-picker v-model="projectForm.deadline" type="datetime" style="width: 100%;" />
                    </el-form-item>
                    <el-form-item label="项目描述" prop="description">
                        <el-input type="textarea" v-model="projectForm.description" maxlength="255" show-word-limit
                            :rows="2"></el-input>
                    </el-form-item>
                </el-form>
                <div class="dialog_footer">
                    <el-button type="primary" @click="nextStep">
                        下一步
                    </el-button>
                </div>
            </div>
            <div class="dialog_body" v-if="stepStore.currentStep === 2">
                <el-form label-position="top" size="large" ref="teamFormRef" :model="teamForm" :rules="teamFormRules">
                    <el-form-item label="团队名称" prop="teamName">
                        <el-input type="text" v-model="teamForm.teamName" maxlength="255" show-word-limit></el-input>
                    </el-form-item>
                </el-form>
                <div class="dialog_footer">
                    <el-button @click="prevStep">
                        上一步
                    </el-button>
                    <el-button type="primary" @click="submit(projectForm, teamForm.teamName)">确认</el-button>
                </div>
            </div>
        </el-dialog>
    </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue';
import { useStepStore } from '@/store/step';
import { useUserStore } from '@/store/user';
import { createProject } from '@/api/project';
import { FormInstance, FormRules } from 'element-plus';
const visible = ref(false)
const stepStore = useStepStore()
const userStore = useUserStore()
const emit = defineEmits()
const projectForm = reactive({
    projectName: '',
    mark: '',
    deadline: '',
    description: '',
    creatorId: JSON.parse(JSON.stringify(userStore.userInfo.userId))
})
const teamForm = reactive({
    teamName:''
})
const projectFormRef = ref<FormInstance>()
const teamFormRef = ref<FormInstance>()
const timeValidator = (_rule: any, value: any, callback: any) => {
    console.log(value==='')
    if (value === '') {
        callback(new Error('截止日期不能为空'))
    } else {
        const selectedTime = new Date(value).getTime(); // 获取所选时间的时间戳
        const currentTime = Date.now(); // 获取当前时间的时间戳
        if (selectedTime < currentTime) {
            console.log(111)
            callback(new Error('截止日期不能早于当前时间'));
            console.log(222)
        } else {
            callback();
        }
    }
}
const validProjectName = (_rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('请输入项目名'))
    }else{
        callback()
    }
}
const validMark = (_rule: any, value: any, callback: any) => {
    // 使用正则表达式校验是否为大小写字母组合
    const pattern = /^[a-zA-Z]+$/;
    if (value === '') {
        callback(new Error('请输入项目标识符'));
    }else if(value.length>10){
        callback(new Error('标识符最大只允许10位'))
    }else if (!pattern.test(value)) {
        callback(new Error('标识符必须是英文字母'));
    } else {
        callback();
    }
}
const validTeamName = (_rule: any, value: any, callback: any)=>{
    if (value === '') {
        callback(new Error('请输入团队名称'))
    }else{
        callback()
    }
}
const ProjectFormRules = reactive<FormRules<typeof projectForm>>({
    projectName: [{ required: true, validator: validProjectName, trigger: 'blur' }],
    mark: [{ required: true, validator: validMark, trigger: 'blur' }],
    deadline: [{ required: true, trigger: 'change', validator: timeValidator }],
})
const teamFormRules = reactive<FormRules<typeof teamForm>>({
    teamName:[{required:true,validator:validTeamName,trigger:'blur'}]
})
const nextStep = () => {
    projectFormRef.value?.validate((valid: boolean) => {
        if (valid) {
            stepStore.nextStep()
        }
    })
}
const prevStep = () => {
    stepStore.prevStep()
}

const submit = (project: any, teamName: string) => {
    teamFormRef.value?.validate((valid:boolean)=>{
        if(valid){
            const teamEncode = encodeURIComponent(teamName)
            createProject(project,teamEncode).then(res=>{
                if(res.success) userStore.setUserInfo(res.data)
                emit('inserted')
            })
            close()
        }
    })
}
const resetForm = () => {
    projectFormRef.value?.resetFields()
    teamForm.teamName=''
}
const close = () => {
    resetForm()
    visible.value = false
    stepStore.currentStep = 1
}


</script>

<style scoped lang="scss">
:deep(.el-dialog) {
    margin: 40px auto;
    width: 450px;
    height: 80%;
    padding: 0;
    border-radius: 5px;
}

:deep(.required-label > label::after) {
    content: "*";
    color: #ff7575;
    margin-left: 4px;
    position: relative;
    top: 0;
}


.dialog_header {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    padding: 0 20px;
    min-height: 56px;
    height: 56px;
    border-top-left-radius: .3rem;
    border-top-right-radius: .3rem;
}

.el-dialog__title {
    font-size: 1rem;
    font-weight: 400;
}

.dialog_body {
    padding: .5rem 2rem 1rem;
    overflow-y: auto;
    max-height: 400px;
}

.dialog_footer {
    padding: .5rem 2rem 1rem;
    position: absolute;
    right: 0;
    bottom: 0;
}
</style>