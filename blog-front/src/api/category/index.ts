import axios from "axios";
import type {CategoryTreeResponse, PortalQueryCategoryTreeRequest, QueryCategoryTreeRequest} from "@/api/category/types"

const ADMIN_PREFIX = "/admin/categories";

const PORTAL_PREFIX = "/portal/categories";

export const queryCategoryTree = (params: QueryCategoryTreeRequest) => {
    return axios.get<CategoryTreeResponse[]>(ADMIN_PREFIX, {params})
}

export function portalQueryCategoryTree(params: PortalQueryCategoryTreeRequest) {
    return axios.get<CategoryTreeResponse[]>(PORTAL_PREFIX, {params})
}

export default {queryCategoryTree, portalQueryCategoryTree}