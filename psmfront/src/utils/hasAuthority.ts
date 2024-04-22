import {useUserStore} from '@/store/user'

const userStore = useUserStore()

const projectAuthorities = userStore.userInfo.projectAuthorities
export const hasProjectAuthority=(authority_name:string,projectId:string):boolean=>{
    return projectAuthorities.some(projectAuthority =>
        projectAuthority.projectId === projectId && 
        projectAuthority.authorities.some(authority => authority.authorityName === authority_name)
    )
}
// export const isManager=(projectId:string):boolean=>{
//     return 
// }
