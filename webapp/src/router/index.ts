import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import DocumentView from '../views/DocumentView.vue'
import HttpErrorView from "@/views/HttpErrorView.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: HomeView,
        },
        {
            path: '/site/:user/:repo/:page(.*)*',
            name: 'document',
            component: DocumentView,
        },
        {
            path: '/error',
            name: 'error',
            component: HttpErrorView
        },
        {
            path: '/:path(.*)*',
            name: 'unknown',
            component: HttpErrorView,
        }
    ],
})

export default router
