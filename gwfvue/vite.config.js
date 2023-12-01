import {fileURLToPath, URL} from 'node:url'

import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [vue(),], resolve: {
        alias: {
            '@': fileURLToPath(new URL('./src', import.meta.url))
        }
    },
    // server: {
    //     host: '127.0.0.1', port: 8888, proxy: {
    //         '/users': {
    //             target: 'http://192.168.108.129:8081',//代理的地址
    //             changeOrigin: true, // rewrite: path => path.replace(/^\/api/, '')
    //         }, '/files': {
    //             target: 'http://192.168.108.129:8081',//代理的地址
    //             changeOrigin: true, // rewrite: path => path.replace(/^\/api/, '')
    //         }, '/blogs': {
    //             target: 'http://192.168.108.129:8081',//代理的地址
    //             changeOrigin: true, // rewrite: path => path.replace(/^\/api/, '')
    //         }, '/roles': {
    //             target: 'http://192.168.108.129:8081',//代理的地址
    //             changeOrigin: true, // rewrite: path => path.replace(/^\/api/, '')
    //         }
    //     }
    //
    // },
    build: {
        outDir: 'dist', assetsDir: '', sourcemap: false, minify: true,
    }
})
