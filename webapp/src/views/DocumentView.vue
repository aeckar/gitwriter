<script setup lang="ts">
import AppIcon from '@/components/AppIcon.vue'
import { useError } from '@/stores/stores.ts'
import GithubIcon from '@/components/GithubIcon.vue'
import { useRoute, useRouter } from 'vue-router'
import { computed, onMounted, ref } from 'vue'
import type { Page } from '@/lib/types.ts'
import { useHead } from '@vueuse/head'
import PlaceholderBox from '@/components/document/PlaceholderBox.vue'

const router = useRouter()
const route = useRoute()
const error = useError()
const { user, repo } = route.params // todo set timeouts for async actions
const page = ref<Page | null>(null)

const tabName = computed(() => {
    if (page.value == null) {
        return ""
    }
    console.log(page.value)
    return `${page.value.document.title} - <todo>`
})

useHead({   // todo for icon and favicon, send raw github image urls, ((do this after presentation))
    title: tabName, // todo from parser, extract first heading,if present -- otherwise append with " - Untitled"
    link: [
        {
            rel: 'icon',    // todo change all
            type: 'image/png',
            href: 'favicon.png',
        },
    ]
})

onMounted(async () => {
    let response: Response
    const serverUrl = `http://localhost:8080/pages/${user}/${repo}/${route.params.page}`    // todo rm localhost
    try {
        response = await fetch(serverUrl)
    } catch (err) {
        error.cause = `Failed to retrieve external resource '${serverUrl}'` // todo instead of this, just alert and also change error page to just number and statustext
        if (err instanceof TypeError) {
            error.message = err.message
        }
        await router.push({ name: "error" })
        return
    }
    if (!response.ok) {
        error.cause = `${response.status}: ${response.statusText}`
        await router.push({ name: "error" })
    }
    page.value = await response.json()
})
</script>

<template>
    <!-- Guard components requiring page data with 'v-if="page"' until server responds. -->
    <div id="layout">
        <nav id="navigation" class="appender flex-row">
            <div id="title-container" class="flex-row">
                <div id="icon">
                    <a v-if="page" :href="`/pages/${user}/${repo}/`" target="_blank">
                        <AppIcon width="40px" height="40px" color="white"/><!-- todo replace with icon later -->
                    </a>
                    <PlaceholderBox v-else width="40px" height="40px"/>
                </div>
                <div id="title">
                    <h1 v-if="page">
                        {{ page.document.title }}
                    </h1>
                    <PlaceholderBox v-else width="250px" height="40px"/>
                </div>
            </div>
            <a v-if="page" :href="`https://github.com/${user}/${repo}/`">
                <GithubIcon id="github-icon" width="40px" height="40px" color="white"/>
            </a>
            <div v-else>

            </div>
        </nav>
        <div id="content">
            <nav id="content-dir">

            </nav>
            <div id="content-body" class="flex-column" style="color:black; font-size: 40pt;">
                <div id="content-text">
                    <div v-if="page" v-html="page.content">
                    </div>
                    <div v-else>

                    </div>
                </div>
                <footer id="copyright" class="faded-text footer-text">
                    Generated using GitWriter &copy; Angel Eckardt 2025
                </footer>
            </div>
            <nav id="content-nav"></nav>
        </div>
    </div>
</template>

<style scoped>
#layout {
    #navigation {
        justify-content: space-between;
    }
    #title {
        margin-left: 5px;
        font-size: 20pt;
        color: white;
        font-weight: bold;
    }
    #icon {
        margin: 10px 0 10px 100px;
    }
    #content-dir {

    }
    #content-nav {

    }
    #content-body {
        min-height: 100vh;
    }
    #content-text {
        flex: 0.5;
        font-size: 12pt;
    }
    #github-icon {
        margin-right: 10px;
    }
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    height: 100vh;
    width: 100vw
}
</style>
