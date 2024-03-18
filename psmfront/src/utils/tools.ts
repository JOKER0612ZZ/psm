import eventBus from './event'
export const projectMain = ()=>{
    eventBus.emit('projectDetails',false)
}