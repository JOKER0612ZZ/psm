import request from '@/utils/request'
import router from '@/router/index'
import { useUserStore } from '@/store/user'
const store = useUserStore()
export const login = (data: any) => {
    request.post('/api/login', {
        userName:data.userName,
        password:data.password
    }).then(res => {
        if (res.success == true) {
            store.setUserInfo(res.data)
            window.sessionStorage.setItem("token", res.data.token)
            router.replace('/home')
        }
    })
}
export const register = (data: any) => {
    request.post('/api/register', {
        userName: data.userName,
        password: data.password,
        email: data.email
    })
}
export const logout = () => {
    request.get('/api/logout').then(res => {
        if (res.success) {
            window.sessionStorage.clear()
            router.replace("/login")
        }
    })
}