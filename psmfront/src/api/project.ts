import request from '@/utils/request'
import { useProjectStore } from '@/store/project'
import { useUserStore } from '@/store/user'
import { Project } from './interface'
const projectStore = useProjectStore()
const userId = useUserStore().userInfo.userId
export const createProject = (project: any, teamName: string) => {
    request.post(`/project/createProject/${teamName}`, project)
}
export const queryUserProject = async (userId: string) => {
    return await request.post(`/project/queryUserProject/${userId}`).then(res => {
        if (res.success) {
            projectStore.setProjects(res.data)
            return res.data
        }
    })
}
export const addRecords = (data:Project)=>{
    request.post(`/project/addRecords/${userId}`,data)
}

export const getRecords = async() =>{
    return request.get(`/project/getRecords/${userId}`).then(res=>{
        return res.data
    })
}

export const getProgress =async(projectId:string)=>{
    return request.get(`/project/getProgress/${projectId}`).then(res=>{
        return res.data
    })
}