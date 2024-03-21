import request from '@/utils/request'

export const queryTasksByProjectId= async (projectId:string,pageNum:number,pageSize:number)=>{
    return await request.get(`/task/queryTasksByProjectId/${projectId}/${pageNum}/${pageSize}`).then(res=>{
        if(res.success){
            return res.data
        }
    })
}
