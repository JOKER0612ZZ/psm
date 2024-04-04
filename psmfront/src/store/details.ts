import { defineStore } from "pinia"
export const useDetailStore = defineStore("details",{
    state:()=>({
        projectDetails:false,
        teamDetails:false
    }),
    persist:{
        key: 'details', //存储名称
        storage: sessionStorage, // 存储方式
    }
})