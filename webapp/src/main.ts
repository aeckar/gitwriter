import { createApp } from 'vue'
import { createHead } from '@vueuse/head'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import 'vuetify/styles'
import '@mdi/font/css/materialdesignicons.css'

createApp(App)
    .use(createHead())
    .use(createPinia())
    .use(router)
    .use(createVuetify({
        components,
        directives,
        icons: {
            defaultSet: 'mdi',
        },
    }))
    .mount('#app')
