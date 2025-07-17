<script setup lang="ts">
import AppIcon from '@/components/AppIcon.vue'
import { useError } from '@/stores/stores.ts'
import GithubIcon from '@/components/GithubIcon.vue'
import { useRoute, useRouter } from 'vue-router'
import { computed, onMounted, ref } from 'vue'
import type { Page } from '@/lib/types.ts'
import { useHead } from '@vueuse/head'
import PlaceholderBox from '@/components/PlaceholderBox.vue'

const router = useRouter()
const route = useRoute()
const error = useError()
const { user, repo } = route.params
const page = ref<Page | null>(null) // Prefer 'null' over 'undefined' to allow for coalescing

const tabName = computed(() => {
    if (page.value == null) {
        return ""
    }

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

// onMounted(async () => {
//     let response: Response
//     const serverUrl = `http://localhost:8080/pages/${user}/${repo}/${route.params.page}`    // todo remove localhost
//     try {
//         const timeout = setTimeout(() => {
//             throw new Error("Request timed out")
//         }, 15000)
//         response = await fetch(serverUrl)
//         clearTimeout(timeout)
//     } catch (err) {
//         alert(`Network error: Failed to connect to server. Please try again later.`)
//         throw err
//     }
//     if (!response.ok) {
//         error.status = response.status
//         error.statusText = response.statusText
//         await router.push({ name: "error" })
//         return
//     }
//     page.value = await response.json()
// })

const navbarPulse = ["#636468", "#4f5054"]
const contentPulse = ["#eff0f4", "#dbdce0"]
</script>

<template>
<!-- Guide: Components requiring server data must show as placeholder by using 'v-if="page"' -->
<div class="page">
    <nav class="flex-row navbar v-bar">
        <div class="flex-row">
            <a v-if="page" class="doc-icon" :href="`/pages/${user}/${repo}/`">
                <AppIcon width="40px" height="40px" color="white"/><!-- todo replace with icon -->
            </a>
            <!-- This placeholder might not be accurate if user agent font size is changed,
                 but since actual sizes vary depending on font, this is good enough. -->
            <PlaceholderBox v-else class="doc-icon" width="40px" height="40px" :pulse="navbarPulse"/>
            <div class="title">
                <h1 v-if="page">{{ page.document.title }}</h1>
                <PlaceholderBox v-else width="250px" height="40px" :pulse="navbarPulse"/>
            </div>
        </div>
        <a v-if="page" class="github-icon" :href="`https://github.com/${user}/${repo}/`">
            <GithubIcon width="40px" height="40px" color="white"/>
        </a>
        <PlaceholderBox v-else class="github-icon" width="40px" height="40px" :pulse="navbarPulse"/>
    </nav>
    <div v-if="page" class="flex-row document content">
        <div class="content-directory">

        </div>
        <div class="flex-col content-body" style="color:black; font-size: 40pt;">
            <main v-html="page.content" class="content-text"></main>
            <footer class="footer faded fine">
                Generated using GitWriter &copy; Angel Eckardt 2025
            </footer>
        </div>
        <div class="sections">

        </div>
    </div>
    <div v-else class="flex-row load">
        <PlaceholderBox class="load-dir" :pulse="contentPulse" height="80%" width="var(--load-sidebar-width)"/>
        <div class="load-body flex-col">
            <PlaceholderBox class="load-crumbs" :pulse="contentPulse" height="40px" width="var(--load-crumbs-width)"/>
            <PlaceholderBox class="load-text" height="0" width="80%" :pulse="contentPulse"/>
            <footer class="footer faded fine">
                Generated using GitWriter &copy; Angel Eckardt 2025
            </footer>
        </div>
        <PlaceholderBox class="load-sections" :pulse="contentPulse" height="40%" width="var(--load-sidebar-width)"/>
    </div>
</div>
</template>

<style scoped>
.page {
    --load-sidebar-width: 20%;
    --load-crumbs-width: 40%;
    --load-crumbs-margin: 3%;
    --load-sidebar-offset: 6%;
    --navbar-height: 60px;
}

.footer {
    width: max-content; /* shrink to footer text */
    text-align: center;
}

/* ------------------------------ navbar styles ------------------------------ */

.navbar {
    position: absolute;
    top: 0;
    width: 100vw;
    height: var(--navbar-height);
    justify-content: space-between;
}

.doc-icon {
    margin: 10px 0 10px 80px;
}

.title {
    margin-left: 15px;
    font-size: 20pt;
    color: white;
    font-weight: bold;
}

.github-icon {
    margin-right: 12px;
}

/* ------------------------------ loading screen styles ------------------------------ */

.load {
    margin-top: var(--navbar-height);
    align-items: flex-start;
    height: calc(100vh - var(--navbar-height));
    justify-content: center;
}

.load-dir {
    margin-top: var(--load-sidebar-offset);
}

.load-body {
    height: calc(100vh - var(--navbar-height));
    width: 50%;
    justify-content: space-around;
}

.load-crumbs {
    margin: var(--load-crumbs-margin) var(--load-crumbs-width) var(--load-crumbs-margin) 0;
}

.load-sections {
    margin-top: var(--load-sidebar-offset);
}

.load-text {
    flex: 0.95;
}

/* ------------------------------ document styles ------------------------------ */

.document {
    margin-top: 3%;
    height: 96%;
    scroll-padding-top: 50px;
    overflow: scroll;
}

.content-body {
    width: 50%;
    height: 900px;
}

.content-directory {

}

.content-text {
    font-size: 12pt;
}

:deep(.content-text ul) {
    list-style-type: circle !important;

}

:deep(.content-text a) {
    color: dodgerblue;

}

:deep(.content-text h1) {
    font-size: 24pt;
}

:deep(.content-text a:hover) {
    text-decoration: dodgerblue underline; /* todo animate right */
}

:deep(.content-text li::marker) {
    content: "- " !important;
}

:deep(.content-text a:active) {
    color: dodgerblue;
}

.content .sections {

}
</style>
