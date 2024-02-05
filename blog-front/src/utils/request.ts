import type {AxiosResponse, InternalAxiosRequestConfig} from "axios";
import axios from "axios";
import {Notification} from '@arco-design/web-vue';

const service = axios.create({
    baseURL: '/api',
    timeout: 50000,
    headers: {"Content-Type": "application/json;charset=utf-8"},
});

// 请求拦截器
service.interceptors.request.use(
    (config: InternalAxiosRequestConfig) => {
        return config;
    },
    (error: any) => {
        return Promise.reject(error);
    }
);

// 响应拦截器
service.interceptors.response.use(
    (response: AxiosResponse) => {
        // 响应数据为二进制流处理(Excel导出)
        if (response.data instanceof ArrayBuffer) {
            return response;
        }
        return response.data;
    },
    (error: any) => {
        if (error.response) {
            debugger
            switch (error.response.status) {
                case 500:
                    Notification.error("服务器异常")
                case 404:
                    Notification.error("Not Found")
                case 403:
                    Notification.error("Forbidden")
                case 401:
                    Notification.error("Unauthorized")
                case 400:
                    Notification.error(error.response.data.errorMsg || "Error")
            }
        }
        return Promise.reject(error);
    }
);

// 导出 axios 实例
export default service;
