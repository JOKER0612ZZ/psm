<template>
    <el-upload class="upload-demo" drag action :show-file-list="true" :auto-upload="false" :on-change="handleChange"
        ref="uploadRef">
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">
            拖拽上传/<em>点击上传</em>
        </div>
        <template #tip>
            <div class="el-upload__tip">
                文件大小不超过50mb
            </div>
        </template>
    </el-upload>
    <el-button type="primary" @click="upload">提交任务</el-button>
</template>

<script setup lang="ts">
import { ref, watchEffect } from 'vue'
import { ElMessage, UploadFile } from 'element-plus';
import { MyUploadFile } from '@/api/interface';
import { uploadTaskFile } from '@/api/file';
import eventBus from '@/utils/event';
const props = defineProps({
    psmfile: {
        type: Object as () => MyUploadFile,
        required: true
    },
    taskId: {
        type: String,
        required: true
    },

})
const uploadRef = ref<any>(null);
// const progressPercent = ref(0);
let formData = new FormData();
const effectFile = ref<MyUploadFile>()
watchEffect(() => {
    effectFile.value = props.psmfile
});
const emit=defineEmits()

const handleChange = (uploadFile: UploadFile) => {
    formData.append('file', uploadFile.raw!);
    const file:any = formData.get('file')

    // 获取文件的大小，单位是字节
    const fileSize = file.size;
    console.log('fileSize: ', fileSize);
}
const upload = () => {

    // 如果 formData 中没有文件，则直接返回，不执行上传操作
    if (!formData.has('file')) {
        ElMessage.warning('没有可以上传的文件')
        return;
    }
    const file:any = formData.get('file')

    // 获取文件的大小，单位是字节
    const fileSize = file.size;

    // 如果文件太大（超过50MB），可以返回并给出警告
    if (fileSize > 50 * 1024 * 1024) {
        ElMessage.warning('文件大小不能超过50MB')
        formData = new FormData()
        uploadRef.value?.clearFiles();
        return;
    }
    
    
    formData.append('psmfile', JSON.stringify(props.psmfile))
    uploadTaskFile(formData, props.taskId).then(() => {
        eventBus.emit('refreshAgent')
    })
    formData = new FormData()
    uploadRef.value?.clearFiles();
    emit('uploaded')
}
</script>
<style scoped lang="scss"></style>
