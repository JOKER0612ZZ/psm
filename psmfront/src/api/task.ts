import request from '@/utils/request'
import { Task } from '@/api/interface'
import eventBus from '@/utils/event'
export const queryTasksByProjectId = async (projectId: string, pageNum: number, pageSize: number) => {
    return await request.get(`/task/queryTasksByProjectId/${projectId}/${pageNum}/${pageSize}`).then(res => {
        if (res.success) {
            return res.data
        }
    })
}
export const updateTask = (task: Task) => {
    request.post(`/task/updateTask/${task.projectId}`, task).then(res => {
        if (res.success)
            eventBus.emit('taskChange')
    })
}
export const queryTasksByProjectIdList = async (projectIdList: number[]) => {
    const res = await request.post(`/task/queryTasksByProjectIdList`, projectIdList)
    if (res.success) {
        return res.data
    }
}

export const queryTaskStatus = async(projectId:string)=>{
    return request.get(`/task/queryTaskStatus/${projectId}`).then(res=>{return res.data})
}
export const assignTask  = async(projectId:string,taskId:string,assignerId:string)=>{
    request.put(`/task/assignTask/${projectId}/${taskId}/${assignerId}`)
}