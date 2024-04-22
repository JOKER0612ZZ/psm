import eventBus from './event'
import { useProjectStore } from '@/store/project'
import router from '@/router'
import { Message } from '@/api/interface'
import { getCurrentDate } from './dateUtils'
const projectStore = useProjectStore()
export const projectMain = () => {
    eventBus.emit('projectDetails', false)
}

export const teamMain = () => {
    eventBus.emit('teamDetails', false)
}
export const goProjectDetail = (row: any) => {
    projectStore.setProjectInfo(row)
    eventBus.emit('projectDetails', true)
    router.push(`/home/project/details/${row.mark}`)
}

export const isRead = (messages: Message[]): boolean => {
    if (messages) {
        return messages.some(message => message.isRead === 0)
    }
    else return true
}

export const downloadExcel = (data: any) => {
    const byteCharacters = atob(data);
    const byteNumbers = new Array(byteCharacters.length);
    for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
    const byteArray = new Uint8Array(byteNumbers);
    const blob = new Blob([byteArray], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    const date = getCurrentDate()
    a.download = `${date}.xlsx`;
    document.body.appendChild(a);
    a.click();
    setTimeout(() => {
        window.URL.revokeObjectURL(url);
        document.body.removeChild(a);
    }, 0);
}