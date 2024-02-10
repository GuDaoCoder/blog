interface SearchTagForm {
    tagName?: string
}

interface PageTagDTO extends PageDTO {
    tagName?: string
}

interface PageTagVO {
    tagId: string,
    tagName: string,
    enable: boolean,
    updateTime: string
}