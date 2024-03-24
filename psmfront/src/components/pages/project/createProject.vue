<template>
    <div>
        <el-button plain type="primary" @click="visible = true"><el-icon>
                <Plus />
            </el-icon>新建项目</el-button>
        <el-dialog v-model="visible" :show-close="false" width="500">
            <template #header="{ titleId, titleClass }">
                <div class="dialog_header">
                    <h4 :id="titleId" :class="titleClass">创建项目</h4>
                    <el-icon @click="close()">
                        <Close />
                    </el-icon>
                </div>
            </template>
            <div class="dialog_body" v-if="stepStore.currentStep === 1">
                <el-form label-position="top" size="large" v-model="projectForm">
                    <el-form-item label="项目名称" prop="projectName" class="required-label">
                        <el-input type="text" v-model="projectForm.projectName" maxlength="255" show-word-limit></el-input>
                    </el-form-item>
                    <el-form-item label="项目标识" prop="mark" class="required-label">
                        <el-input type="text" v-model="projectForm.mark"></el-input>
                    </el-form-item>
                    <el-form-item label="截止时间" prop="deadline" class="required-label">
                        <el-date-picker v-model="projectForm.deadline" type="datetime" style="width: 100%;"/>
                    </el-form-item>
                    <el-form-item label="项目描述" prop="description">
                        <el-input type="textarea" v-model="projectForm.description" maxlength="255" show-word-limit :rows="2"></el-input>
                    </el-form-item>
                </el-form>
                <div class="dialog_footer">
                    <el-button type="primary" @click="nextStep">
                        下一步
                    </el-button>
                </div>
            </div>
            <div class="dialog_body" v-if="stepStore.currentStep === 2">
                <el-form label-position="top" size="large" >
                    <el-form-item label="团队名称" prop="teamName" class="required-label">
                        <el-input type="text" v-model="teamName" maxlength="255" show-word-limit></el-input>
                    </el-form-item>
                </el-form>
                <div class="dialog_footer">
                    <el-button @click="prevStep">
                        上一步
                    </el-button>
                    <el-button type="primary" @click="submit(projectForm,teamName)">确认</el-button>
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
const stepStore = useStepStore()
const userStore = useUserStore()
const projectForm = reactive({
    projectName:'',
    mark:'',
    deadline:'',
    description:'',
    creatorId: JSON.parse(JSON.stringify(userStore.userInfo.userId))
})
const teamName = ref('')
const nextStep = () => {
    stepStore.nextStep()
}
const prevStep = () => {
    stepStore.prevStep()
}
const submit = (project:any,teamName:string)=>{
    createProject(project,teamName)
    close()

}
const close= ()=>{
    visible.value =false
    stepStore.currentStep=1
}

const visible = ref(false)
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