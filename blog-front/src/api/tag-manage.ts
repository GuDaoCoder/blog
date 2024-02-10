import axios from "axios";

export function pageTag(params: PageTagDTO) {
    return axios.get<PageVO<PageTagVO>>("/admin/tag", {params})
}