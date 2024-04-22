import request from '@/utils/request'
import {useUserStore} from '@/store/user'
const userId = useUserStore().userInfo.userId
export const getMessages=async()=>{
    return await request.get(`/message/getMessages/${userId}`).then(res=>{return res.data})
}
export const deleteMessageById=(messageId:string)=>{
    request.delete(`/message/deleteMessage/${messageId}`)
}
export const changeReadStatus = (messageId:string)=>{
    request.put(`/message/isRead/${messageId}`)
}