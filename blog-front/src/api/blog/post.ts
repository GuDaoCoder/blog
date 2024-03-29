import axios from "axios";

export function searchBlogPosts(params: SearchPostBlogRequest) {
    return axios.get<PageResponse<PostBlogResponse>>("/blog/posts", {params})
}
