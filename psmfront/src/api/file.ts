import request from '@/utils/request'
import { useUserStore } from '@/store/user'
const userStore = useUserStore()
const userId = userStore.userInfo.userId
export const queryFile = async (projectId: string, parentId: string | null) => {
    const res = await request.post(`/file/queryFile`, {
        projectId: projectId,
        parentId: parentId
    })
    if (res.success) return res.data
}
export const queryFileByFileId = async (fileId: number) => {
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
export const uploadFiles = (data:any) =>{
    request.upload('/file/uploadFile',data).then(res=>{
        console.log(res)
    })
}