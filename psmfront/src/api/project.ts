import request from '@/utils/request'
import { useProjectStore } from '@/store/project'
import { useUserStore } from '@/store/user'
import { Project, ResultProject } from './interface'
const projectStore = useProjectStore()
const projectId = projectStore.projectInfo?.projectId
const userId = useUserStore().userInfo.userId
export const createProject = (project: any, teamName: string) => {
   return request.post(`/project/createProject/${teamName}`, project)
}
export const queryUserProject = async () => {
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
export const active=(projectId:string)=>{
    request.get(`/project/active/${projectId}/${userId}`)
}
export const getActive=async(projectId:string)=>{
    return await request.get(`/project/getActive/${projectId}`).then(res=>{
        return res.data
    })
}
export const setPlan = (data:FormData)=>{
    return request.upload(`/project/setPlan/${projectId}`,data)
} 
export const getPlan = (projectId:string)=>{
    return request.get(`/project/getPlan/${projectId}`).then(res=>{
        return res.data
    })
} 
export const updateProject = (project:ResultProject)=>{
    return request.post(`/project/updateProject/${project.projectId}`,project)
}