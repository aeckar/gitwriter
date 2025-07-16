<script setup lang="ts">
import { useHead } from '@vueuse/head'
import AppIcon from "@/components/AppIcon.vue";
import { Search } from 'lucide-vue-next';
import { Input } from '@/components/ui/input';
import { useRouter } from 'vue-router'
import { ref } from 'vue'
import HoverText from "@/components/text/HoverText.vue";

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
    <div id="layout" class="flex-column">
        <div id="main-content" class="flex-column">
            <header class="flex-column">
                <div class="flex-row">
                    <AppIcon height="60px" width="60px" color="white"/>
                    <span id="title">GitWriter</span>
                    <div id="version" class="faded-text flex-column">
                        beta
                        <div id="version-sup"></div>
                    </div>
                </div>
                <span id="subtitle" class="faded-text">Share markup with ease!</span>
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
        <footer id="footer" class="footer-text flex-column appender">
            <nav id="navigation" class="flex-row">
                <a href="/about" class="glow-on-hover">
                    <HoverText color="white">
                        about
                    </HoverText>
                </a>
                |
                <a href="https://github.com/aeckar/gitwriter/" class="glow-on-hover">
                    <HoverText color="white">
                        source
                    </HoverText>
                </a>
            </nav>
            &copy; Angel Eckardt 2025
        </footer>
    </div>
</template>

<style scoped>
#layout {
    #main-content {
        #title {
            margin-left: 5px;
            font-weight: bold;
            font-size: 40pt;
        }
        #version {
            margin: 0 0 40px 5px;
            font-size: 15pt;
        }
        #subtitle {
            margin-bottom: 30px;
            font-size: 12pt;
        }
        flex: 1;
    }
    #navigation, #footer {
        gap: 5px;
    }
    #footer {
        color: #aaaaaa;
        font-family: monospace;
        padding-top: 9px;
        width: 100vw;
    }
    justify-content: space-between;
    color: white;
    background-color: #17171a;
    position: absolute;
    height: 100vh;
    width: 100vw;
}
</style>
