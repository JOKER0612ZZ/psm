<template>
    <el-button type="primary" plain @click="visible=true">下载</el-button>
    <div class="download">
        <el-dialog v-model="visible" :append-to-body="true" title="下载窗口" center width="300">
            <div class="down-body">
                <el-input :readonly="true" v-model="file.fileName"></el-input>
            </div>
                
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="visible = false">取消</el-button>
                    <el-button type="primary" @click="download(file)">
                        下载
                    </el-button>
                </div>
            </template>
        </el-dialog>
    </div>

</template>

<script setup lang="ts">
import { ref } from 'vue';
import { downloadFile } from '@/api/file';
const visible = ref(false)
defineProps({
    file: {
        type: Object,
        required: true
    }
})
const download = (file:any)=>{
    visible.value=false
    console.log(file)
    downloadFile(file)
}
</script>

<style scoped lang="scss">
.el-dialog{
    border-radius:25px;
}
.dialog-footer{
    position: relative;
    margin-top:10px;
    left:40px;
    .el-button{
        margin-left:10px;
    }
}
</style>