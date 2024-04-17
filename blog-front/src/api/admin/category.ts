import type {AxiosResponse} from "axios"
import axios from "axios";

const prefix = "/admin/categories";

export function searchTree(params: SearchCategoryTreeRequest): Promise<AxiosResponse<CategoryTreeResponse[]>> {
    return axios.get<CategoryTreeResponse[]>(prefix, {params})
}