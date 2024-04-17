import axios from "axios";

const prefix = "/portal/categories";

export function blogSearchCategoryTree(params: SearchCategoryTreeRequest) {
    return axios.get<CategoryTreeResponse[]>(prefix, {params})
}
