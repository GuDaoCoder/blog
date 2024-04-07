import {createPinia} from 'pinia'
import useAppStore from './modules/app'
import useAnchorStore from './modules/anchor'

const pinia = createPinia();

export {useAppStore, useAnchorStore}

export default pinia