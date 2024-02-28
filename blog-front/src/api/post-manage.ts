import axios from "axios";

export function pagePost(params: PagePostDTO) {
    return axios.get<PageVO<PagePostVO>>("/admin/post", {params})
}

export function createPost(data: CreatePostDTO) {
    return axios.post<CreatePostVO>("/admin/post", data)
}

export function updatePost(postId: string, data: UpdatePostDTO) {
    return axios.put("/admin/post/" + postId, data)
}