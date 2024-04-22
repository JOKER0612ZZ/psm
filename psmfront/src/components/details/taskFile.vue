<template>
    <div class="file">
        <el-main>
            <el-table :data="files" :row-style="rowStyle">
                <el-table-column label="文件名" prop="fileName">
                    <template #default="{ row }">
                        <div class="file_name">
                            <el-icon v-if="row.type === 'folder'" size="30">
                                <Folder />
                            </el-icon>
                            <el-icon v-if="row.type === 'file'" size="30">
                                <Document />
                            </el-icon>
                            <span>{{ row.fileName }}</span>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column label="创建者" prop="userName"></el-table-column>
                <el-table-column label="创建时间" prop="uploaderTime"></el-table-column>
                <el-table-column label="文件类型" prop="type">
                    <template #default="{ row }">
                        <span v-if="row.type === 'folder'">文件夹</span>
                        <span v-if="row.type === 'file'">文件</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template #default="{ row }">
                        <download :file="row" v-if="row.type === 'file'"></download>
                    </template>
                </el-table-column>
            </el-table>
        </el-main>
    </div>
</template>
<script setup lang="ts">
import { ref, onMounted } from 'vue';
import {  queryByTaskId } from '@/api/file'
import download from '../pages/file/download.vue';
const files = ref<any>()
const props = defineProps({
    taskId:{
        type:Number,
        required:true
    }
})
onMounted(async () => {
    console.log('props.taskId: ', props.taskId);
    files.value = await queryByTaskId(props.taskId)
})
const rowStyle = (_row: any, _index: number) => {
    return {
        height: '60px', 
    };
};
</script>


<style scoped lang="scss">
.file {
    background-color: #fafafa;
    width: 100%;
    height: 100%;
}

.file_name {
    display: flex;
    flex-direction: row;
    align-items: center;

    span {
        display: inline-block;
        margin-left: 10px;
    }
}

.el-header {
    width: 100%;
    background-color: #fff;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;

    .el-input {
        float: left;
        height: 30px;
        width: 150px;
    }

    .el-button {
        margin-left: 20px;
    }
}

.el-main {
    margin: 20px;
    height: 100%;
    padding: none;
    outline: 1px solid #eee;
    z-index: 1;
    background-color: #fff;

    span {
        display: inline-block;
        width: 100%;
        text-align: left;
    }
}

.el-table td.el-table__cell {
    border-bottom: none;
}

.download {
    display: block;
    width: 100px;
    height: 30px;
}

.download:hover {
    cursor: pointer;
    text-decoration: underline;
}
</style>
