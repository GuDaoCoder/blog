<script lang="ts" setup>
import ContentCard from '@/components/ContentCard/index.vue'
import OverviewQuantityCard from "@/components/OverviewQuantityCard/index.vue"
import statisticApi from "@/api/statistic"
import {onMounted, ref} from "vue";
import type {QuantityStatisticsResponse} from "@/api/statistic/types";

const overviewStatistics = ref<QuantityStatisticsResponse[]>([])

onMounted(() => {
  fetchOverviewStatistic();
})

const fetchOverviewStatistic = async () => {
  const {data} = await statisticApi.statisticOverview()
  overviewStatistics.value = data;
}
</script>

<template>
  <Container>
    <content-card>
      <div class="overview-quantity-wrapper">
        <a-row :gutter="24">
          <a-col v-for="item in overviewStatistics" :key="item.name" :span="8">
            <overview-quantity-card :label="item.label" :name="item.name"
                                    :quantity="item.quantity"/>
          </a-col>
        </a-row>

      </div>

    </content-card>
  </Container>
</template>

<style lang="scss" scoped>
.overview-quantity-wrapper {

}
</style>