import axios from "axios";
import type {QuantityStatisticsResponse} from "@/api/statistic/types";

const ADMIN_PREFIX = "/api/admin/statistics";

export const statisticOverview = () => {
    return axios.get<QuantityStatisticsResponse[]>(`${ADMIN_PREFIX}/statisticOverview`)
}

export default {statisticOverview}