import type {PostResponse} from "@/api/post/types";

export interface PostListProp {
    items: PostResponse[] | []
}