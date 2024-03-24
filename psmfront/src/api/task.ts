import request from '@/utils/request'
import { Task } from '@/api/interface'
import eventBus from '@/utils/event'
export const queryTasksByProjectId= async (projectId:string,pageNum:number,pageSize:number)=>{
    return await request.get(`/task/queryTasksByProjectId/${projectId}/${pageNum}/${pageSize}`).then(res=>{
        if(res.success){
            return res.data
        }
    })
}
export const updateTask =(task:Task)=>{
    request.post(`/task/updateTask/${task.projectId}`,task).then(res=>{
        if(res.success)
        eventBus.emit('taskChange')
    })
}
