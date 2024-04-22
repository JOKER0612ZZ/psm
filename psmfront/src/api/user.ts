import request from '@/utils/request'
// import router from '@/router/index'
import { useUserStore } from '@/store/user'
import type { FormInstance } from 'element-plus'
const store = useUserStore()
const userId = JSON.parse(JSON.stringify(store.userInfo.userId))
export const queryUser = () => {
    request.get('/user/queryUser/' + store.userInfo.userName)
}
export const updateUserNameById = (userName: string): any => {
    return request.get(`/user/updateUserNameById/${userId}/${userName}`).then(res => {
        if (res.success) {
            store.userInfo.username = res.data.userName
            store.userInfo.userName = res.data.userName
            return store.userInfo
        }
    })
}
export const updateEmailById = (email: string): any => {
    return request.get(`/user/updateEmailById/${userId}/${email}`).then(res => {
        if (res.success) store.setUserInfo(res)
        return store.userInfo
    })
}
export const updateNickNameAndGenderById = (nickname: string, gender: string) => {
    const url = `/user/updateNickNameAndGenderById/${userId}/${nickname}/${gender}`;
    return request.get(url).then(res => {
        if (res.success) { 
            store.userInfo.nickname = res.data.nickname
            store.userInfo.gender = res.data.gender
        }
        return store.userInfo
    })
}
export const updatePassWordById = (oldPassword: string, newPassword: string, FormEl: FormInstance | undefined) => {
    request.get(`/user/updatePasswordById/${userId}/${oldPassword}/${newPassword}`).then(res => {
        if (res.success) {
            store.userInfo.password = res.data.password
            FormEl?.resetFields()
        }
    })
}
export const searchUser=(condition:string)=>{
    condition = encodeURIComponent(condition)
    return request.get(`/user/searchUser/${condition}`).then(res=>{
        return res.data
    })
}
export const queryUserByUserId=async(userId:string)=>{
    return await request.get(`./user/queryUserByUserId/${userId}`).then(res=>{
        return res.data
    })
}