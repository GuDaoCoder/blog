import axios from "axios";

export function blogSearchCategoryTree(params: SearchCategoryTreeRequest) {
    return axios.get<CategoryTreeResponse[]>("/blog/categories", {params})
}
