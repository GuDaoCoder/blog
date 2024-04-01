import {defineStore} from "pinia";

const useBlogPostPreviewStore = defineStore("blog-post-preview", {
    state: (): BlogPostResponse => <BlogPostResponse>({}),
    actions: {
        updateBlogPostPreview(partial: Partial<BlogPostResponse>) {
            this.$patch(partial)
        }
    }
})

export default useBlogPostPreviewStore;
