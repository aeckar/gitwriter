<script setup lang="ts">
import { useHead } from '@vueuse/head'
import AppIcon from "@/components/AppIcon.vue";
import { Search } from 'lucide-vue-next';
import { Input } from '@/components/ui/input';
import { useRouter } from 'vue-router'
import { ref } from 'vue'

useHead({
    title: 'GitWriter',
    link: [
        {
            rel: 'icon',
            type: 'image/png',
            href: 'favicon.png',
        },
    ]
})

const router = useRouter()
const search = ref('')

async function onSubmit() {
    const route = search.value.trim()
    if (route.length != 0) {
        console.log(route)
        await router.push(`/pages/${route}`)
    }
}
</script>

<template>
    <div class="flex-column page-layout">
        <div class="flex-column main-content">
            <header class="flex-column">
                <div class="flex-row">
                    <AppIcon height="60px" width="60px" color="white"/>
                    <span class="title">GitWriter</span>
                    <span class="faded version">beta</span>
                </div>
                <span class="faded subtitle">Share markup with ease!</span>
            </header>
            <div class="relative w-full max-w-sm items-center">
                <form v-on:submit.prevent="onSubmit">
                    <Input v-model="search" name="search" type="text" placeholder="Search..." class="pl-10" />
                    <button type="submit" class="invisible"></button>
                </form>
                <span class="absolute start-0 inset-y-0 flex items-center justify-center px-2">
                    <Search class="size-6 text-muted-foreground" color="white"/>
                </span>
            </div>
        </div>
        <footer class="footer footer-text flex-column appender">
            <nav class="flex-row navigation">
                <a href="/about" class="glow-white">about</a>
                |
                <a href="https://github.com/aeckar/gitwriter/" class="glow-white">source</a>
            </nav>
            &copy; Angel Eckardt 2025
        </footer>
    </div>
</template>

<style scoped>
/* Guide: Use 'rem' for elements relative to font size, otherwise use % */

.page-layout {
    color: white;
    background-color: #17171a;
}

.main-content {
    flex: 1;    /* send footer to bottom */
}

.title {
    margin-left: 0.5rem;    /* away from logo */
    font-weight: bold;
    font-size: 40pt;
}

.version {
    margin: 0 0 1.8rem 0.5rem;  /* superscript, away from title */
    font-size: 15pt;
}

.subtitle {
    margin-bottom: 10%;    /* away from search bar */
    font-size: 12pt;
}

.navigation, .footer {
    gap: 0.5rem;
}

.footer {
    color: #aaaaaa;
    font-family: monospace;
    padding-top: 0.8rem;   /* equalize top, bottom padding */
    width: 100vw;
}
</style>
