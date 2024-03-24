import { authorities } from "@/api/interface";
import { defineStore } from "pinia";

export const useUserStore = defineStore('user', {
    state: () => ({
        userInfo: {
            token: '',
            userName: '',
            username: '',
            userId: '',
            gender: '',
            password: '',
            nickname: '',
            projectAuthorities:[
                {
                    projectId :'',
                    authorities: <authorities[]>[{

                    }]
                }
            ],
        }
    }),
    actions: {
        setUserInfo(data: any) {
            this.userInfo = data
        },
        getUserInfo(): any {
            return this.userInfo
        }
    },
    persist: {
        key: 'user', //存储名称
        storage: sessionStorage, // 存储方式
    },
}
)
