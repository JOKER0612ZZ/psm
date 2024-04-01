import request from '@/utils/request'

export const queryTeamsByUserId = async (userId: string) => {
    return request.get(`/team/queryByUserId/${userId}`).then(res => {
        return res.data
    })
}

export const queryTeamMembers = async (teamId:string) =>{
    return request.get(`/team/queryTeamMembers/${teamId}`).then(res => {
        return res.data
    })
}