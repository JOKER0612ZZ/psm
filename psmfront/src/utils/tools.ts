import eventBus from './event'
import { useProjectStore } from '@/store/project'
import { addRecords } from '@/api/project'
import router from '@/router'
const projectStore = useProjectStore()
export const projectMain = ()=>{
    eventBus.emit('projectDetails',false)
}

export const teamMain = ()=>{
    eventBus.emit('teamDetails',false)
}
export const goProjectDetail = (row:any) =>{
    console.log(row.length)
    projectStore.setProjectInfo(row)
    eventBus.emit('projectDetails',true)
    addRecords(row)
    router.push(`/home/project/details/${row.mark}`)
}