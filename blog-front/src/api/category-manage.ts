import axios from "axios";
import type {CategoryTreeResponseType} from "@/types/response-type";
import type {categoryTreeRequest} from "@/types/request-type";

export function categoryTree(params: categoryTreeRequest) {
    return axios.get<CategoryTreeResponseType[]>("/admin/category", {params})
}