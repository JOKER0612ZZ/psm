<template>
    <div class="file" @click="handleClickOutside">
        <el-main>
            <el-header>
                <upload :psmfile="psmfile" :project-id="projectStore.projectInfo?.projectId!"></upload>
                <el-button @click="backtoLastPage()" v-if="currentId.parentId !== null">返回上一级目录</el-button>
                <el-button @click="backtoFirstPage()" v-if="currentId.parentId !== null">返回根目录</el-button>
                <el-input></el-input>
            </el-header>
            <span>全部任务</span>
            <el-table :data="files" :row-style="rowStyle" @row-click="queryChildenFile">
                <el-table-column label="文件名" prop="fileName">
                    <template #default="{ row }">
                        <div class="file_name" @contextmenu.native="showContextMenu($event, row)">
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
    <contextMenu v-if="showMenu" :style="{ top: `${menuTop}px`, left: `${menuLeft}px` }" @close="closeContextMenu"
        @createFolder="createFolder" @createFile="createFile" />
</template>
<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from 'vue';
import { useProjectStore } from '@/store/project';
import { queryFile } from '@/api/file'
import contextMenu from './contextMenu.vue'; // 导入右键菜单组件
import download from '../pages/file/download.vue';
import upload from '@/components/pages/file/upload.vue'
import { useUserStore } from '@/store/user';
interface idlist {
    parentId: string | null,
    projectId: string,
}
const userStore=useUserStore()
const psmfile = {
    filePath: '',
    uploaderId: userStore.userInfo.userId,
    uploaderTime: '',
    projectId: '',
    type: '',
    parentId: ''
}
// import eventBus from '@/utils/event';
// import upload from '@/components/pages/file/upload.vue';
const projectStore = useProjectStore()
const files = ref<any>()
const menuTop = ref<number>(0); // 右键菜单的位置
const menuLeft = ref<number>(0);
const showMenu = ref<boolean>(false); // 是否显示右键菜单
const lastFileId = ref<idlist[]>([])
const currentId = ref<idlist>({
    parentId: null,
    projectId: projectStore.projectInfo?.projectId!
})
onMounted(async () => {
    files.value = await queryFile(projectStore.projectInfo?.projectId!, null)
    lastFileId.value.push({
        parentId: null,
        projectId: projectStore.projectInfo?.projectId!,
    })
    lastFileChange()
})

const lastFileChange = () => {
    const index: number = files.value[0].filePath.lastIndexOf('/')
    psmfile.filePath = files.value[0].filePath.substring(0, index)
    psmfile.parentId = currentId.value.parentId!
    psmfile.type='file'
    // console.log(files.value[0].filePath)
    // console.log(psmfile.filePath)
}
const queryChildenFile = async (row: any) => {
    if (row.type === 'folder') {
        files.value = await queryFile(projectStore.projectInfo?.projectId!, row.fileId)
        lastFileId.value.push({
            parentId: row.fileId,
            projectId: projectStore.projectInfo?.projectId!
        })
        currentId.value = {
            projectId: projectStore.projectInfo?.projectId!,
            parentId: row.fileId
        }
    }
    lastFileChange()
}
const rowStyle = (_row: any, _index: number) => {
    return {
        height: '60px', // 设置行高
        // 其他样式
    };
};
const backtoLastPage = async () => {
    lastFileId.value.pop()
    currentId.value = lastFileId.value[lastFileId.value.length - 1]
    lastFileChange()
    files.value = await queryFile(currentId.value.projectId, currentId.value.parentId)
}
const backtoFirstPage = async () => {
    files.value = await queryFile(lastFileId.value[0].projectId, lastFileId.value[0].parentId)
    currentId.value = lastFileId.value[0]
    lastFileChange()
    lastFileId.value = []
    lastFileId.value.push({
        parentId: null,
        projectId: projectStore.projectInfo?.projectId!
    })
}
const showContextMenu = (event: MouseEvent, row: any) => {
    event.preventDefault();
    menuTop.value = event.clientY;
    menuLeft.value = event.clientX;
    showMenu.value = true;
    console.log(row)
};

const closeContextMenu = () => {
    showMenu.value = false;
};

const createFolder = async () => {
    // 创建文件夹逻辑
    await createFolder(/* 参数 */);
    // 刷新文件列表
    files.value = await queryFile(projectStore.projectInfo?.projectId!, null);
};

const createFile = async () => {
    // 创建文件逻辑
    await createFile(/* 参数 */);
    // 刷新文件列表
    files.value = await queryFile(projectStore.projectInfo?.projectId!, null);
};

const handleClickOutside = (event: MouseEvent) => {
    const menu = document.querySelector('.context-menu') as HTMLElement | null;
    if (menu && !menu.contains(event.target as HTMLElement)) {
        closeContextMenu();
    }
};

// 添加全局点击事件监听
onMounted(() => {
    document.addEventListener('click', handleClickOutside);
});

// 移除全局点击事件监听
onBeforeUnmount(() => {
    document.removeEventListener('click', handleClickOutside);
});
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

    .el-input {
        position: absolute;
        height: 30px;
        width: 150px;
        right: 40px;
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
