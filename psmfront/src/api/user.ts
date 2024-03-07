import request from '@/utils/request'
// import router from '@/router/index'
import { useUserStore } from '@/store/user'

const store = useUserStore()
export const queryUser = () =>{
    request.get('/user/queryUser/'+store.userInfo.data.userName)
}