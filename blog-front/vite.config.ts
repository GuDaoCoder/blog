import {ConfigEnv, defineConfig, loadEnv, UserConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'
// 自动增加css前缀
import autoprefixer from 'autoprefixer'
// 引入svg插件
import {createSvgIconsPlugin} from 'vite-plugin-svg-icons'

// https://vitejs.dev/config/
export default defineConfig(({ mode }: ConfigEnv): UserConfig => {
    const env = loadEnv(mode, process.cwd());
    return {
        plugins: [
            vue(),
            // svg图标插件
            createSvgIconsPlugin({
                iconDirs: [path.resolve(process.cwd(), 'src/assets/icons')],
                symbolId: 'icon-[dir]-[name]',
            })
        ],
        css: {
            postcss: {
                plugins: [
                    // 不同浏览器自动添加css前缀
                    autoprefixer({
                        overrideBrowserslist: ['Chrome > 40', 'ff > 31', 'ie 11'],
                    })
                ]
            }
        },
        resolve: {
            alias: {
                // 相对路径别名
                '@': path.resolve("./src")
            }
        },
        server: {
            proxy: {
                '/api': {
                    target: env.VITE_APP_BASE_API,
                    changeOrigin: true,
                    rewrite: (path) => path.replace(/^\/api/, ''),
                },
            }
        }
    }

})
