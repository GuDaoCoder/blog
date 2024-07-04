import axios from "axios";
import type {AdminUserRequest, AdminUserResponse} from "@/api/admin-user/types";

const ADMIN_PREFIX = "/api/admin/adminUsers";

export const list = () => {
    return axios.get<AdminUserResponse[]>(ADMIN_PREFIX)
}

export const saveOrUpdate = (data: AdminUserRequest) => {
    return axios.post(`${ADMIN_PREFIX}/saveOrUpdate`, data)
}

export default {list, saveOrUpdate}