import axios from "axios";

export function pagePost(params: PagePostDTO) {
    return axios.get<PageVO<PagePostVO>>("/admin/post", {params})
}