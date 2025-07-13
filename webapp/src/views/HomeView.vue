<script setup lang="ts">

import { useHead } from '@vueuse/head'
import AppIcon from "@/components/AppIcon.vue";
import { Search } from 'lucide-vue-next';
import { Input } from '@/components/ui/input';
import { useRouter } from 'vue-router'

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

function onSubmit() {
    const route = search.value.trim()
    if (route.length != 0) {
        await router.push({ name: `/site/${route}` })
    }
}
</script>

<template>
    <div id="layout" class="col-format">
        <div></div>
        <div class="col-format">
            <header class="col-format">
                <div class="row-format">
                    <AppIcon color="white"/>
                    <span id="title">GitWriter</span>
                    <div id="version" class="faded-text col-format">
                        beta
                        <div></div>
                    </div>
                </div>
                <span id="greeting" class="faded-text">Share markup with ease!</span>
            </header>

            <div id="search">
                <form @submit.prevent="onSubmit">
                    <Input type="text" v-model="search" placeholder="Search repositories..." />
                    <button type="submit" class="invisible"></button>
                </form>
                <span>
                    <Search id="search-icon" color="white" />
                </span>
            </div>
        </div>
        <footer class="faded-text footer-text col-format">
            <nav class="row-format">
                <a href="/about">about</a>|
                <a href="https://github.com/aeckar/gitwriter/">source</a>
            </nav>
            &copy; Angel Eckardt 2025
        </footer>
    </div>
</template>

<style scoped>
nav, footer {
    gap: 5px;
}



nav a:hover {
    color: white;
    transition: color 0.3s;
}

#layout {
    justify-content: space-between;

    /* color scheme */
    color: white;
    background-color: #27282c;

    /* position at center of page */
    position: absolute;
    height: 100vh;
    width: 100vw;
}

#title {
    sup {
        font-size: 10pt;
    }

    font-weight: bold;
    font-size: 40pt;
}

#greeting {
    padding-bottom: 30px;
    font-size: 12pt;
}

#search-icon {
    width: 1.5rem;
    height: 1.5rem;
    color: white;
}

#search {
    input {
        padding-left: 2.5rem;
    }

    span {
        position: absolute;
        inset: 0 0 0 auto;
        display: flex;
        align-items: center;
        justify-content: center;
        padding-left: 0.5rem;
        padding-right: 0.5rem;
    }

    width: 1000px;
    position: relative;
    max-width: 24rem;
    display: flex;
    align-items: center;
}

#version {
    div {
        height: 45px;
    }

    padding-left: 5px;
    font-size: 12pt;
}
</style>
