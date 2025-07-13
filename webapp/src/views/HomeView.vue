<script setup lang="ts">

import { useHead } from '@vueuse/head'
import AppIcon from "@/components/AppIcon.vue";
import { Search } from 'lucide-vue-next';
import { Input } from '@/components/ui/input';

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
</script>

<template>
    <div id="layout">
        <header>
            <AppIcon color="black"/>
            <span id="title">GitWriter</span>
        </header>
        <span id="greeting">Share your markup with ease!</span>
        GitWriter is a
        <div class="relative w-full max-w-sm items-center">
            <Input id="search" type="text" placeholder="Search repositories..." class="pl-10 border-#27282c" />
            <span class="absolute start-0 inset-y-0 flex items-center justify-center px-2">
                <Search class="size-6 text-muted-foreground" color="#27282c"/>
            </span>
        </div>
        <footer>

        </footer>
    </div>
</template>

<style scoped>
header {
    display: inline-flex;
    flex-direction: row;
    gap: 10px;
}

#title {
    font-size: 30pt;
}

#layout {
    display: flex;
    flex-direction: column;
    position: absolute;
    height: 100vh;
    width: 100vw;
    justify-content: center;
    align-items: center;
}

#greeting {
    font-size: 30pt;
}

#search {
    border-color: #27282c;
}
</style>
