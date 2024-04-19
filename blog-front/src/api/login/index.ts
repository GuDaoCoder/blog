import axios from "axios";
import type {LoginRequest, LoginResponse} from "@/api/login/types"

export const login = (data: LoginRequest) => {
    return axios.post<LoginResponse>('/login', data)
}

export const logout = () => {
    return axios.post("/logout");
}

export default {login, logout}