export interface Task {
    taskId: string,
    projectId: string,
    title: string,
    description: string,
    creationTime: string,
    deadline: string,
    creatorId: string,
    status: string,
    creatorName: string,
    assignName: string,
    assignerId: string,
    projectName: string
}
export interface TaskData {
    taskViews: Task[];
    total: number;
}
export interface Project {
    creationTime: string,
    creatorId: string,
    deadline: string,
    description: string,
    mark: string,
    projectId: string,
    projectName: string,
    projectStatus: string,
    userName: string,
    teamId: string,
}
export interface authorities {
    authorityId: number,
    authorityName: string,
    authorityCode: number
}

export interface PsmFile {
    fileId: string,
    fileName: string,
    filePath: string,
    uploaderId: string,
    uploaderTime: string,
    projectId: string,
    type: string,
    parentId: string,
}

export interface Team {
    teamId: string
    teamName: string
    creatorId: string
    creationTime: string
    projectId: string
    creatorName: string
}
export interface Members {
    teamId: string
    memberId: string
    roleId: string
    roleName: string
    memberName: string
}