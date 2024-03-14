
import { defineStore } from "pinia";

export const useProjectStore = defineStore('project',{
    state: () => ({
        projectInfo:{},
    }),
    actions: {
        setUserInfo(data: any) {
            this.projectInfo = data
        },
        getUserInfo():any  {
            return this.projectInfo
        }
    },
    
})