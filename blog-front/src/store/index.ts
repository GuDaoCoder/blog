import {createPinia} from 'pinia'
import useAppStore from './modules/app'
import useBlogPostPreviewStore from './modules/post'

const pinia = createPinia();

export {useAppStore, useBlogPostPreviewStore}

export default pinia