import {useUserStore} from '@/store/user'

const userStore = useUserStore()

const projectAuthorities = userStore.userInfo.projectAuthorities

export const hasProjectAuthority=(authority_name:string,projectId:string)=>{
    return projectAuthorities.some(projectAuthority =>
        projectAuthority.projectId === projectId && 
        projectAuthority.authorities.some(authority => authority.authorityName === authority_name)
    )
}
// export const hasProjectAuthority = (authorityName: string, projectId: string): boolean => {
//     // 从用户存储中获取项目权限信息
//     const projectAuthorities = userStore.userInfo.projectAuthorities;
//     console.log(projectId)
//     // 使用 Array.prototype.some() 方法检查是否存在匹配的权限
//     const hasAuthority = projectAuthorities.some(projectAuthority => {
//         console.log(`Checking projectId: ${projectAuthority.projectId} with authorityName: ${authorityName}`);
//         const matchProjectId = projectAuthority.projectId === projectId
//         console.log(`Matched projectId: ${matchProjectId}`);
//         const hasAuthorityName = projectAuthority.authorities.some(authority => {
//             console.log(`Checking authority: ${authority.authorityName}`);
//             const matchAuthorityName = authority.authorityName === authorityName;
//             console.log(`Matched authorityName: ${matchAuthorityName}`);
//             return matchAuthorityName;
//         });
//         console.log(`Has authorityName: ${hasAuthorityName}`);
//         return matchProjectId && hasAuthorityName;
//     });

//     console.log(`Final result: ${hasAuthority}`);

//     return hasAuthority;
// };