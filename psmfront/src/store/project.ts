
import { defineStore } from "pinia";
import { Project } from "@/api/interface"
export const useProjectStore = defineStore('project', {
    state: () => ({
        projectInfo: null as Project | null,
    }),
    actions: {
        setProjectInfo(data: any) {
            this.projectInfo = data
        },
        getProjectInfo(): any {
            return this.projectInfo
        }
    },
    persist: {
        key: 'project', //存储名称
        storage: sessionStorage, // 存储方式
    }
})
