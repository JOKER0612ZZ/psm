
import { defineStore } from "pinia";

export const useUserStore = defineStore('user', {
    state: () => ({
        userInfo: {
            code: 500,
            data: {
                token: '',
                userName:'',
                
            },
            message: '',
            success: false
        }
    }),
    actions: {
        setUserInfo(data: any) {
            this.userInfo = data
        }
    },
    getters: {
        getUserInfo(state): any  {
            return state.userInfo
        }
    },
    persist: {
        key: 'user', //存储名称
        storage: sessionStorage, // 存储方式
    },
}
)