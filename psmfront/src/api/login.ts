import request from '@/utils/request'
import router from '@/router/index'
import { useUserStore } from '@/store/user'
const store = useUserStore()
export const login = (data: any) => {
    request.post('/api/login', data).then(res=>{
        if(res.success==true){
            store.setUserInfo(res)
            window.sessionStorage.setItem("token",res.data.token)
            router.replace('/home')
        }
    })
}
export const register = (data: any)=>{
    request.post('api/register',{
        userName:data.userName,
        password:data.password,
        email:data.email
    })
}