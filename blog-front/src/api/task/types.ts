export interface TaskSearchForm {
    taskName?: string,
    status?: string
}

export interface TaskSearchRequest extends PageRequest {
    taskName?: string,
    status?: string
}

export interface TaskResponse {
    taskId: number,
    taskName: string,
    status: string,
    log?: string;
    beginDateTime?: string;
    endDateTime?: string;
}