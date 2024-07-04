import axios from "axios";
import type {GitRepositoryRequest, GitRepositoryResponse} from "@/api/git-repository/types";

const ADMIN_PREFIX = "/api/admin/gitRepositories";

export const list = () => {
    return axios.get<GitRepositoryResponse[]>(ADMIN_PREFIX)
}

export const save = (data: GitRepositoryRequest) => {
    return axios.post<number>(ADMIN_PREFIX, data)
}

export default {list, save}