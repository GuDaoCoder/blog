import request from "@/utils/request";
import type {AxiosPromise} from "axios";
import type {LoginRequestType} from "@/types/request-type";
import type {LoginResponseType} from "@/types/response-type";

export function login(data: LoginRequestType): AxiosPromise<LoginResponseType> {
    return request({
        url: "/login",
        method: "post",
        data: data
    });
}
