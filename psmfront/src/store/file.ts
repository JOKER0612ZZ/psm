import { PsmFile } from "@/api/interface"
import { defineStore } from "pinia"
export const useFileStore = defineStore("file", {
    state: () => ({
        files: null as PsmFile[] | null
    }),
    actions: {
        setFiles(data: PsmFile[]) {
            this.files = data
        },
        getFiles(): PsmFile[] | null {
            return this.files
        }
    },
    persist: {
        key: 'file', //存储名称
        storage: sessionStorage, // 存储方式
    }
})