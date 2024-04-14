import type {AxiosResponse} from "axios"
import axios from "axios";

export function searchTree(params: SearchCategoryTreeRequest): Promise<AxiosResponse<CategoryTreeResponse[]>> {
    return axios.get<CategoryTreeResponse[]>("/admin/category", {params})
}