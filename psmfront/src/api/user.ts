import request from '@/utils/request'
// import router from '@/router/index'
import { useUserStore } from '@/store/user'
import type { FormInstance } from 'element-plus'
const store = useUserStore()
const userId = JSON.parse(JSON.stringify(store.userInfo.data.userId))
export const queryUser = () => {
    request.get('/user/queryUser/' + store.userInfo.data.userName)
}
export const updateUserNameById = (userName: string): any => {
    return request.get(`/user/updateUserNameById/${userId}/${userName}`).then(res => {
        if (res.success) {
            store.setUserInfo(res)
            return store.userInfo.data
        }
    })
}
export const updateEmailById = (email: string): any => {
    return request.get(`/user/updateEmailById/${userId}/${email}`).then(res => {
        if (res.success) store.setUserInfo(res)
        return store.userInfo.data
    })
}
export const updateNickNameAndGenderById = (nickname: string, gender: string) => {
    const url = `/user/updateNickNameAndGenderById/${userId}/${nickname}/${gender}`;
    return request.get(url).then(res => {
        if (res.success) store.setUserInfo(res)
        return store.userInfo.data
    })
}
export const updatePassWordById = (oldPassword: string, newPassword: string, FormEl: FormInstance | undefined) => {
    request.get(`/user/updatePasswordById/${userId}/${oldPassword}/${newPassword}`).then(res => {
        if (res.success) {
            store.setUserInfo(res)
            FormEl?.resetFields()
        }
    })
}

