import {type App} from "vue";
import {dict} from "@/utils/dict";

export default {
    install:(app:App) => {
        app.config.globalProperties.$dict = dict
    }
}