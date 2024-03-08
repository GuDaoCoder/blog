interface SearchCategoryForm {
    categoryName?: string,
    enabled?: boolean
}

interface SaveCategoryForm {
    categoryId?: number,
    categoryName?: string,
    parentCategoryId?: number,
    parentCategoryName?: string
    enabled?: boolean,
}

interface SearchCategoryTreeRequest {
    categoryName?: string,
    enabled?: boolean
}

interface CategoryTreeResponse extends CategoryResponse {
    children?: CategoryTreeResponse[]
}

interface CreateCategoryRequest {
    categoryName: string,
    parentCategoryId: number,
    enabled: boolean
}

interface CategoryResponse {
    categoryId: number,
    categoryName: string,
    parentCategoryId: number,
    parentCategoryName: string,
    fullId: string,
    orderNo: number,
    level: number,
    enabled: boolean,
    postCount: number,
    createTime: string,
    updateTime: string
}

interface UpdateCategoryRequest {
    categoryName: string,
    enabled: boolean
}