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

#version {
    div {
        height: 45px;
    }

    padding-left: 5px;
    font-size: 12pt;
}
</style>
