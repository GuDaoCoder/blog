import axios from "axios";

export function searchTree(params: SearchCategoryTreeRequest) {
    return axios.get<CategoryTreeResponse[]>("/admin/category", {params})
}

export function createCategory(data: CreateCategoryRequest) {
    return axios.post<CategoryResponse>("/admin/category", data)
}

export function updateCategory(categoryId: number, data: UpdateCategoryRequest) {
    return axios.patch(`/admin/category/${categoryId}`, data)
}

export function deleteCategory(categoryId: number) {
    return axios.delete("/admin/category/" + categoryId)
}