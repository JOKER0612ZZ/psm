import { useUserStore } from '@/store/user'
import request from '@/utils/request'
const userId = useUserStore().userInfo.userId

export const queryTeamsByUserId = async (userId: string) => {
    return request.get(`/team/queryByUserId/${userId}`).then(res => {
        return res.data
    })
}

export const queryTeamMembers = async (teamId: string) => {
    return request.get(`/team/queryTeamMembers/${teamId}`).then(res => {
        return res.data
    })
}

export const queryProjectMembers = async (projectId: string) => {
    return await request.get(`/team/queryProjectMembers/${projectId}`).then(res => {
        return res.data
    })
}
export const joinTeam = async (projectId: string) => {
    request.put(`/team/joinTeam/${projectId}/${userId}`)
}
export const removeMember = (teamId:string,memberId:string)=>{
    return request.delete(`/team/removeMember/${teamId}/${memberId}`)
}
