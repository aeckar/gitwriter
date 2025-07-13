import './assets/styles.css'

import { createApp } from 'vue'
import { createHead } from '@vueuse/head'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'

createApp(App)
    .use(createHead())
    .use(createPinia())
    .use(router)
    .mount('#app')
