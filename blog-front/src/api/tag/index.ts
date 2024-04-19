import axios from "axios";
import type {PortalSearchTagRequest, SearchTagRequest, TagDetailResponse, TagResponse} from "@/api/tag/types"

const ADMIN_PREFIX = "/admin/tags";

const PORTAL_PREFIX = "/portal/tags";

export const searchTag = (params: SearchTagRequest) => {
    return axios.get<PageResponse<TagDetailResponse>>(ADMIN_PREFIX, {params})
}

export function portalSearchTag(params: PortalSearchTagRequest) {
    return axios.get<PageResponse<TagResponse>>(PORTAL_PREFIX, {params})
}

export default {searchTag, portalSearchTag}