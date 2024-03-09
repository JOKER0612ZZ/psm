import request from '@/utils/request'
// import router from '@/router/index'
import { useUserStore } from '@/store/user'

const store = useUserStore()
export const queryUser = () => {
    request.get('/user/queryUser/' + store.userInfo.data.userName)
}
export const updateUserNameById = (userName: string): any => {
    return request.get('/user/updateUserNameById/' + store.userInfo.data.userId + '/' + userName).then(res => {
        if (res.success) {
            store.setUserInfo(res)
            console.log(store.userInfo.data)
            return store.userInfo.data
        }
    })
}
export const updateEmailById = (email: string): any => {
    return request.get(`/user/updateEmailById/${store.userInfo.data.userId}/${email}`).then(res => {
        if (res.success) store.setUserInfo(res)
        return store.userInfo.data
    })
}
export const updateNickNameAndGenderById = (nickname: string, gender: string) => {
    const url = `/user/updateNickNameAndGenderById/${store.userInfo.data.userId}/${nickname}/${gender}`;
    return request.get(url).then(res => {
        if (res.success) store.setUserInfo(res)
        return store.userInfo.data
    })
}