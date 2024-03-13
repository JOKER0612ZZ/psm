import request from '@/utils/request'

export const createProject=(project:any,teamName:string)=>{
    request.post(`/project/createProject/${teamName}`,project)
}