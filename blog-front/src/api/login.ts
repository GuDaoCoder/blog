import axios from "axios";
import type {BaseRequestType, LoginRequestType} from "@/types/request-type";
import type {LoginResponseType} from "@/types/response-type";

export function login(data: LoginRequestType) {
    return axios.post<LoginResponseType>('/login', data)
}

export function logout() {
    return axios.post<BaseRequestType>("/logout");
}