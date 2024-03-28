import request from '@/utils/request'
import { useProjectStore } from '@/store/project'
const projectStore = useProjectStore()
export const createProject = (project: any, teamName: string) => {
    request.post(`/project/createProject/${teamName}`, project)
}
export const queryUserProject = async (userId: string) => {
    return await request.post(`/project/queryUserProject/${userId}`).then(res => {
        if (res.success) {
            projectStore.setProjeces(res.data)
            return res.data
        }
    })
}