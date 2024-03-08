import axios from "axios";

export function login(data: LoginRequest) {
    return axios.post<LoginResponse>('/login', data)
}

export function logout() {
    return axios.post("/logout");
}