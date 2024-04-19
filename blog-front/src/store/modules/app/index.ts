import {defineStore} from 'pinia'
import defaultSettings from '@/config/settings.json';
import type {AppState} from "@/store/modules/app/types";

const useAppStore = defineStore('app', {
    state: (): AppState => ({...defaultSettings}),
    actions: {
        updateSettings(partial: Partial<AppState>) {
            this.$patch(partial)
        }
    }
})

export default useAppStore