import axios from "axios";

export function treeCategory(params: TreeCategoryDTO) {
    return axios.get<TreeCategoryVO[]>("/admin/category", {params})
}

export function createCategory(data: CreateCategoryDTO) {
    return axios.post<CreateCategoryVO>("/admin/category", data)
}

export function updateCategory(categoryId: number, data: UpdateCategoryDTO) {
    return axios.put("/admin/category/" + categoryId, data)
}

export function deleteCategory(categoryId: number) {
    return axios.delete("/admin/category/" + categoryId)
}