import type {AxiosRequestConfig, AxiosResponse} from 'axios';
import axios from 'axios';
import {Notification} from '@arco-design/web-vue';
import {getToken} from "@/utils/auth";
import router from "@/router";

axios.defaults.baseURL = '/api'
axios.defaults.timeout = 5000

// 请求拦截器
axios.interceptors.request.use(
    (config: AxiosRequestConfig) => {
        const token = getToken();
        if (token) {
            if (!config.headers) {
                config.headers = {};
            }
            config.headers['Authorization'] = token;
        }
        return config;
    },
    (error) => {
        // do something
        return Promise.reject(error);
    }
);

// 响应拦截器
axios.interceptors.response.use(
    (response: AxiosResponse<BaseResponse>) => {
        return response.data;
    },
    (error: any) => {
        if (error.response) {
            switch (error.response.status) {
                case 500:
                    Notification.error("服务器异常")
                    break;
                case 404:
                    Notification.error("Not Found")
                    break;
                case 403:
                    Notification.error("Forbidden")
                    break;
                case 401:
                    Notification.error("Unauthorized")
                    router.push({name: 'login'})
                    break;
                case 400:
                    Notification.error(error.response.data.errorMsg || "Error")
                    break;
                default:
                    Notification.error("Error")
                    break;
            }
        } else {
            if (error.message) {
                Notification.error(error.message)
            }
        }
        return Promise.reject(error);
    }
);

