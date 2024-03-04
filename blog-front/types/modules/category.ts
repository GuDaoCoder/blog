interface CategorySearchForm {
    categoryName?: string,
    enabled?: boolean
}

interface CategoryCreateForm {
    categoryId?: number,
    categoryName?: string,
    parentCategoryId?: number,
    parentCategoryName?: string
    enabled?: boolean,
}

interface TreeCategoryDTO {
    categoryName?: string,
    enabled?: boolean
}

interface TreeCategoryVO {
    categoryId: number,
    categoryName: string,
    parentId: number,
    fullId: string,
    orderNo: number,
    updateTime: string,
    level: number,
    enabled: boolean,
    children?: TreeCategoryVO[]
}

interface CreateCategoryDTO {
    categoryName?: string,
    parentCategoryId?: number,
    enabled?: boolean
}

interface CreateCategoryVO {
    categoryId?: number
}

interface UpdateCategoryDTO {
    categoryName?: string,
    enabled?: boolean
}