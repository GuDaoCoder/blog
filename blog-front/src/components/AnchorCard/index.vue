<script lang="ts" setup>
import {computed} from "vue";
import type {Anchor, AnchorState} from "@/store/modules/anchor/type";
import useAnchorStore from "@/store/modules/anchor";

const useAnchor = useAnchorStore();
const anchorState = computed((): AnchorState => useAnchor.$state);
const handleAnchorClick = (anchor: Anchor) => {
  const {lineIndex} = anchor;
  const heading = anchorState.value.el.$el.querySelector(`[data-v-md-line="${lineIndex}"]`);
  if (heading) {
    anchorState.value.el.scrollToTarget({
      target: heading,
      scrollContainer: window,
      top: 80
    });
  }
}
</script>

<template>
  <a-card :bordered="false" hoverable title="目录">
    <div
        v-for="anchor in anchorState.anchors "
        :style="{ padding: `10px 0 10px ${anchor.indent * 20}px` }"
        @click="handleAnchorClick(anchor)"
    >
      <a class="pointer">{{ anchor.title }}</a>
    </div>
  </a-card>
</template>

<style lang="scss" scoped>

</style>