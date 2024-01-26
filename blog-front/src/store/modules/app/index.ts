import {defineStore} from 'pinia'
import defaultSettings from '@/config/settings.json';
import type {AppState} from "@/store/modules/app/type";

const useAppStore = defineStore('count', {
    state: (): AppState => ({...defaultSettings}),
    actions: {
        updateSettings(partial: Partial<AppState>) {
            this.$patch(partial)
        }
    }
})

export default useAppStore