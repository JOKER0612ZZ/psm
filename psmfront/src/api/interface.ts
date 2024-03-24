export interface Task {
    taskId: string,
    projectId: string,
    title: string,
    description: string,
    creationTime: string,
    deadline: string,
    creatorId: string,
    status: string,
    userName: string,
    assignName: string
}
export interface TaskData {
    taskViews: Task[];
    total: number;
}
export interface Project{
    creationTime:string,
    creatorId:string,
    deadline:string,
    description:string,
    mark:string,
    projectId:string,
    projectName:string,
    projectStatus:string,
    userName:string
}
export interface authorities{
    authorityId:number,
    authorityName:string,
    authorityCode:number
}