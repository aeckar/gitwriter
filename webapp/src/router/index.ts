import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import DocumentView from '../views/DocumentView.vue'
import NotFoundView from "@/views/NotFoundView.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: HomeView,
        },
        {
            path: '/:user/:repo',
            name: 'document',
            component: DocumentView,
        },
        {
            path: '/:path(.*)*',
            name: 'unknown',
            component: NotFoundView,
        }
    ],
})

export default router
