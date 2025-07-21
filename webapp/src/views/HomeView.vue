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
<div class="display-col layout">
    <div class="display-col page-content">
        <header class="display-col">
            <div class="display-row">
                <AppIcon height="60px" width="60px" color="white"/>
                <span class="title">GitWriter</span>
                <span class="text-fade text-mono version">beta</span>
            </div>
            <span class="text-fade text-mono subtitle">Share markup with ease!</span>
        </header>
        <div class="display-row">
            <form @submit.prevent="onSubmit">
                <!-- https://vuetifyjs.com/en/api/v-text-field/#props -->
                <VTextField
                    width="30vw"
                    min-width="400px"
                    class="search-bar"
                    v-model="search"
                    label="Search Repositories"
                    prepend-inner-icon="mdi-magnify"
                    clearable
                    variant="filled"
                />
                <button type="submit" v-show="false"></button>
            </form>
        </div>
    </div>
    <footer class="footer text-fade text-fine text-mono display-col bar">
        <nav class="display-row navbar">
            <a href="/about" class="text-glow-white">about</a>
            |
            <a href="https://github.com/aeckar/gitwriter/" class="text-glow-white">source</a>
        </nav>
        &copy; Angel Eckardt 2025
    </footer>
</div>
    <!-- todo get rid of highlight on autocomplete, -->
    <!-- todo remove later -->
    <div id="related" class="display-col">
        Popular Sites
        <ul>
            <li><a class="text-fade text-glow-white" href="/pages/aeckar/index-md-sample">aeckar/index-md-sample</a></li>
            <li><a class="text-fade text-glow-white" href="/pages/aeckar/gitwriter-sample">aeckar/gitwriter-sample</a></li>
            <li><a class="text-fade text-glow-white" href="/pages/aeckar/site-toml">aeckar/site-toml</a></li>
        </ul>

    </div>
</template>

<style scoped>


/* todo remove */
#related {
    position: absolute;
    top: 25vh;
    left: 75vw;
    width: 300px;
    height: 400px;
    background-color: var(--bg-dark);
    justify-content: space-evenly;
    color: white;

    /* https://kovart.github.io/dashed-border-generator/ */
    background-image: url("data:image/svg+xml,%3csvg width='100%25' height='100%25' xmlns='http://www.w3.org/2000/svg'%3e%3crect width='100%25' height='100%25' fill='none' rx='50' ry='50' stroke='%23F6F8FAFF' stroke-width='4' stroke-dasharray='6%2c 14' stroke-dashoffset='0' stroke-linecap='square'/%3e%3c/svg%3e");
    border-radius: 50px;
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

.v-field {
    border-radius: 50px !important;
}

.navbar, .footer {
    gap: 0.5em;
}

.footer {
    padding: 0.8em 0 0.8em 0;
}
</style>
