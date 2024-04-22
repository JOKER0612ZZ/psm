import request from '@/utils/request'
import { useUserStore } from '@/store/user'
import { downloadExcel } from '@/utils/tools'
import { PsmFile } from './interface'
const userStore = useUserStore()
const userId = userStore.userInfo.userId
export const queryFile = async (projectId: string, parentId: string | null) => {
    const res = await request.post(`/file/queryFile`, {
        projectId: projectId,
        parentId: parentId
    })
    if (res.success) return res.data
}
export const queryFileByFileId = async (fileId: string) => {
    const res = await request.get(`/file/queryFileByFileId/${fileId}`)
    if (res.success) return res.data
}
export const downloadFile = async (data: any) => {
    const res = await request.download(`/file/downloadFile/${userId}`, data)
    const url = window.URL.createObjectURL(res);
    const a = document.createElement('a');
    a.style.display = 'none';
    a.href = url;
    a.download = data.fileName;
    document.body.appendChild(a);
    a.click();
    window.URL.revokeObjectURL(url);
    document.body.removeChild(a);
}
export const uploadFiles = (data: any) => {
    return request.upload('/file/uploadFile', data)
}
export const uploadTaskFile = (data: FormData, taskId: string) => {
    return request.upload(`/file/uploadTaskFile/${taskId}`, data)
}
export const queryFileByUserId = async () => {
    return await request.get(`/file/queryFileByUserId/${userId}`).then(res => {
        return res.data
    })
}
export const getOperations = async (projectId: string) => {
    request.get(`/operation/getOperations/${projectId}`).then(res => {
        downloadExcel(res.data)
    })
}
export const queryByTaskId = async(taskId:number)=>{
    return await request.get(`/file/queryByTaskId/${taskId}`).then(res=>{
        return res.data
    })
}
export const deleteFile = async(projectId:string,file:PsmFile)=>{
    return request.post(`/file/deleteFile/${projectId}`,file)
}
