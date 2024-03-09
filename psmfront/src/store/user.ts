
import { defineStore } from "pinia";

export const useUserStore = defineStore('user', {
    state: () => ({
        userInfo: {
            code: 500,
            data: {
                token: '',
                userName:'',
                userId:''
            },
            message: '',
            success: false
        }
    }),
    actions: {
        setUserInfo(data: any) {
            this.userInfo = data
        },
        getUserInfo():any  {
            return this.userInfo.data
        }
    },
    persist: {
        key: 'user', //存储名称
        storage: sessionStorage, // 存储方式
    },
}
)
