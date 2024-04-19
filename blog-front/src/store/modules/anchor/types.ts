export interface AnchorState {
    el: any,
    anchors: Anchor[]
}

export interface Anchor {
    title: string,
    lineIndex: number,
    indent: number
}