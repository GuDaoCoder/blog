import axios from "axios";
import type {TaskResponse, TaskSearchRequest} from "@/api/task/types";

const ADMIN_PREFIX = "/admin/tasks";

export const searchTasks = (params: TaskSearchRequest) => {
    return axios.get<PageResponse<TaskResponse>>(ADMIN_PREFIX, {params})
}

export default {searchTasks}