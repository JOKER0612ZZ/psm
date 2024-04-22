import request from '@/utils/request'
import { Task } from '@/api/interface'
import eventBus from '@/utils/event'
import { useUserStore } from '@/store/user'
import { useProjectStore } from '@/store/project'
const userId = useUserStore().userInfo.userId
const projectId = useProjectStore().projectInfo?.projectId
export const queryTasksByProjectId = async (projectId: string, pageNum: number, pageSize: number) => {
    return await request.get(`/task/queryTasksByProjectId/${projectId}/${pageNum}/${pageSize}`).then(res => {
        if (res.success) {
            return res.data
        }
    })
}

export const queryTasksByProjectIdMyAssign = async (projectId: string, assignerId: string, pageNum: number, pageSize: number) => {
    return await request.get(`/task/queryTasksByProjectIdMyAssign/${projectId}/${assignerId}/${pageNum}/${pageSize}`).then(res => {
        if (res.success) {
            return res.data
        }
    })
}
export const queryTasksByProjectIdMyCreate = async (projectId: string, creatorId: string, pageNum: number, pageSize: number) => {
    return await request.get(`/task/queryTasksByProjectIdMyCreate/${projectId}/${creatorId}/${pageNum}/${pageSize}`).then(res => {
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

export const queryTaskStatus = async (projectId: string) => {
    return request.get(`/task/queryTaskStatus/${projectId}`).then(res => { return res.data })
}
export const assignTask = (projectId: string, taskId: string, assignerId: string) => {
    return request.put(`/task/assignTask/${projectId}/${taskId}/${assignerId}`)
}
export const updateTaskStatus = (projectId: string, taskId: string, status: string) => {
    status = encodeURIComponent(status);
    return request.put(`/task/updateTaskStatus/${projectId}/${taskId}/${status}`)
}
export const createTask = (projectId: string, task: any, assignerId: string) => {
    return request.post(`/task/insertTask/${projectId}/${assignerId}`, task)
}
export const queryVerifyTask = async (projectId: string) => {
    return await request.get(`/task/queryVerifyTasks/${projectId}/${userId}`).then(res => {
        return res.data
    })
}
export const deleteTask = (projectId: string, taskId: string) => {
    return request.delete(`/task/deleteTask/${projectId}/${taskId}`)
}
export const queryTaskByConditon = async (projectId: string, condition: string) => {
    return request.get(`/task/searchTask/${projectId}/${condition}`)
}
export const queryStatusByUserId = async (userId: string)=>{
    return await request.get(`/task/queryStatusByUserId/${projectId}/${userId}`).then(res=>{
        return res.data
    })
}