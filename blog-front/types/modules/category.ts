interface CategorySearchForm {
    categoryName?: string,
    enabled?: boolean
}

interface CategoryCreateForm {
    categoryId?: string,
    categoryName?: string,
    parentCategoryId?: string,
    parentCategoryName?: string
    enabled?: boolean,
}

interface TreeCategoryDTO {
    categoryName?: string,
    enabled?: boolean
}

interface TreeCategoryVO {
    categoryId: string,
    categoryName: string,
    parentId: string,
    fullId: string,
    orderNo: number,
    updateTime: string,
    level: number,
    enabled: boolean,
    children?: TreeCategoryVO[]
}

interface CreateCategoryDTO {
    categoryName?: string,
    parentCategoryId?: string,
    enabled?: boolean
}

interface CreateCategoryVO {
    categoryId?: string
}

interface UpdateCategoryDTO {
    categoryName?: string,
    enabled?: boolean
}