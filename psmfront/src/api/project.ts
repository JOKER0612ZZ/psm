import request from '@/utils/request'

export const createProject = (project: any, teamName: string) => {
    request.post(`/project/createProject/${teamName}`, project)
}
export const queryUserProject = async (userId: string) => {
    return await request.post(`/project/queryUserProject/${userId}`).then(res => {
        if (res.success) {
            return res.data
        }
    })
}