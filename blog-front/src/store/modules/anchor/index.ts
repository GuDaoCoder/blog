import {defineStore} from "pinia";
import type {AnchorState} from "@/store/modules/anchor/type";

const useAnchorStore = defineStore("anchor", {
    state: (): AnchorState => <AnchorState>({}),
    actions: {
        updateAnchorState(partial: AnchorState) {
            this.$state = partial
        }
    }
})

export default useAnchorStore;
