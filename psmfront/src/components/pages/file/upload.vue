<template>
    <el-upload class="upload-demo" drag multiple action :show-file-list="true" :before-upload="beforeUpload"
      :auto-upload="false" :on-change="handleChange" :limit="2" ref="uploadRef">
      <el-icon class="el-icon--upload"><upload-filled /></el-icon>
      <div class="el-upload__text">
        拖拽上传/<em>点击上传</em>
      </div>
      <template #tip>
        <div class="el-upload__tip">
          文件总大小不超过100mb,一次最多上传两个文件
        </div>
      </template>
    </el-upload>
    <el-form-item label="上传项目" v-if="!props.projectId">
      <el-select v-model="projectId">
        <el-option
        v-for="item in projectIds"
        :key="item.projectId"
        :label="item.projectName"
        :value="item.projectId"
      />
      </el-select>
    </el-form-item>
    <el-button size="small" type="primary" @click="upload">点击上传</el-button>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { MyUploadFile } from '@/api/interface';
import { UploadFile } from 'element-plus';
import { useProjectStore } from '@/store/project';
import { uploadFiles } from '@/api/file';
const projectStore = useProjectStore()
const projects = projectStore.projects
const beforeUpload = (file: any) => {
  console.log(file)
}
interface projectIds {
  projectName: string,
  projectId: string
}
const emit =defineEmits(['uploaded'])
const uploadRef = ref<any>(null);
// const progressPercent = ref(0);
let formData = new FormData();
let projectIds: projectIds[] = []
onMounted(async() => {
  projectIds = projects.map(e => ({
    projectName: e.projectName,
    projectId: e.projectId
  }));
  projectId.value = props.projectId!
  
})
const projectId = ref('')
const props = defineProps({
  psmfile: {
    type: Object as ()=> MyUploadFile,
    required: true
  },
  projectId:{
        type: Object as () =>string,
    }
})
// let uploadProgressEvent
const handleChange = (uploadFile: UploadFile) => {
  formData.append('file', uploadFile.raw!);
  console.log(formData.getAll('file'))
}
// const uploadFile = (params: any) => {
//   // uploadProgressEvent = (progressEvent: ProgressEvent) => {
//   //   progressPercent.value = Math.floor((progressEvent.loaded * 100) / progressEvent.total);
//   // }
// }
const upload = async() => {
  props.psmfile.projectId=projectId.value
  formData.append('psmfile',JSON.stringify(props.psmfile))
  await uploadFiles(formData)
  formData = new FormData()
  uploadRef.value?.clearFiles();
  emit('uploaded')
}
</script>
<style scoped lang="scss"></style>
