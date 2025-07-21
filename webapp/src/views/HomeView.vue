<script setup lang="ts">
import { useHead } from '@vueuse/head'
import AppIcon from "@/components/AppIcon.vue";
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
<div class="flex-col page">
    <div class="flex-col page-content">
        <header class="flex-col">
            <div class="flex-row">
                <AppIcon height="60px" width="60px" color="white"/>
                <span class="title">GitWriter</span>
                <span class="faded version">beta</span>
            </div>
            <span class="faded subtitle">Share markup with ease!</span>
        </header>
        <div class="flex-row">
            <form @submit.prevent="onSubmit">
                <v-text-field
                    width="400px"
                    class="search-bar"
                    v-model="search"
                    label="Search Repositories"
                    prepend-inner-icon="mdi-magnify"
                    clearable
                    variant="outlined"
                />
                <button type="submit" class="invisible"></button>
            </form>

        </div>
    </div>
    <footer class="footer faded fine flex-col v-bar">
        <nav class="flex-row navbar">
            <a href="/about" class="glow-white">about</a>
            |
            <a href="https://github.com/aeckar/gitwriter/" class="glow-white">source</a>
        </nav>
        &copy; Angel Eckardt 2025
    </footer>
</div>
</template>

<style scoped>
/* Guide: Use 'em' for elements relative to font size, otherwise use % */

.page {
    color: white;
    background-color: var(--backdrop-color);
}

.page-content {
    flex: 1;    /* send footer to bottom */
}

.title {
    margin-left: 0.2em;    /* away from logo */
    font-weight: bold;
    font-size: 4em;
}

.version {
    margin: 0 0 1.8em 0.5em;  /* superscript, away from title */
    font-size: 1.5em;
}

.subtitle {
    margin-bottom: 10%;    /* away from search bar */
    font-size: 1.2em;
}

.navbar, .footer {
    gap: 0.5em;
}

.footer {
    padding-top: 0.8em;   /* equalize top, bottom padding */
}
</style>
