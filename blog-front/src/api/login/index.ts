import axios from "axios";
import type {LoginRequest, LoginResponse} from "@/api/login/types"

export const login = (data: LoginRequest) => {
    return axios.post<LoginResponse>('/api/login', data)
}

export const logout = () => {
    return axios.post("/api/logout");
}

export default {login, logout}