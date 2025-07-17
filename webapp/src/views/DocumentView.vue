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
const err = useError()
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
    const serverUrl = `http://localhost:8080/pages/${user}/${repo}/${route.params.page}`    // todo remove localhost
    try {
        response = await fetch(serverUrl)
    } catch (err) {
        alert("Network error: Failed to connect to server. Please try again later.")
        throw err
    }
    if (!response.ok) {
        err.status = response.status
        err.statusText = response.statusText
        await router.push({ name: "error" })
    }
    page.value = await response.json()
})

const navbarPulse = ["#636468", "#4f5054"]
const contentPulse = ["#eff0f4", "#dbdce0"]
</script>

<template>
    <!-- Guard components requiring page data with 'v-if="page"' until server responds. -->
    <div class="page-layout">
        <nav class="appender flex-row navigation">
            <div class="flex-row">
                <div class="icon">
                    <a v-if="page" :href="`/pages/${user}/${repo}/`">
                        <AppIcon width="40px" height="40px" color="white"/><!-- todo replace with icon later -->
                    </a>
                    <PlaceholderBox v-else width="40px" height="40px" :pulse="navbarPulse"/>
                </div>
                <div class="title">
                    <h1 v-if="page">{{ page.document.title }}</h1>
                    <PlaceholderBox v-else width="250px" height="40px" :pulse="navbarPulse"/>
                </div>
            </div>
            <div class="github-icon">
                <a v-if="page" :href="`https://github.com/${user}/${repo}/`">
                    <GithubIcon width="40px" height="40px" color="white"/>
                </a>
                <div v-else>
                    <PlaceholderBox width="40px" height="40px" :pulse="navbarPulse"/>
                </div>
            </div>
        </nav>
        <div class="document">
            <div v-if="page">
                <div class="flex-row doc-actual">
                    <div class="directory">

                    </div>
                    <div class="flex-column body" style="color:black; font-size: 40pt;">

                        <footer class="footer faded footer-text">
                            Generated using GitWriter &copy; Angel Eckardt 2025
                        </footer>
                    </div>
                    <div class="sections">

                    </div>
                </div>
            </div>
            <div v-else>
                <div class="flex-row doc-placeholder">
                    <PlaceholderBox class="directory" :pulse="contentPulse" height="750px" width="500px"/>
                    <div class="body flex-column">
                        <PlaceholderBox class="breadcrumbs" :pulse="contentPulse" height="40px" width="400px"/>
                        <PlaceholderBox class="text" height="600px" width="700px" :pulse="contentPulse"/>
                        <footer class="footer faded footer-text">
                            Generated using GitWriter &copy; Angel Eckardt 2025
                        </footer>
                    </div>
                    <PlaceholderBox class="sections" :pulse="contentPulse" height="400px" width="500px"/>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.page-layout {
    display: flex;
    flex-direction: column;
}

/* ------------------------------ navbar items ------------------------------ */

.navigation {
    justify-content: space-between;
}

.github-icon {
    margin-right: 0.5rem;
}

.title {
    margin-left: 0.5rem;
    font-size: 20pt;
    color: white;
    font-weight: bold;
}

.icon {
    margin: 0.5rem 0 0.5rem 5rem;
}

/* ------------------------------ document styles ------------------------------ */

.document {
    flex: 1;    /* send navbar to top */
}

.doc-placeholder {
    margin-top: 2.4%;
    align-items: flex-start;
    justify-content: space-evenly;
}

.doc-placeholder.directory {

}

.doc-placeholder.body {
    height: 800px;
    justify-content: space-between;
}

.doc-placeholder.body.breadcrumbs {
    margin-right: 43%;
    margin-bottom: 20px;
}

.doc-placeholder.body.text {
    flex: 0.95;    /* send footer to bottom, breadcrumbs to top */
    font-size: 12pt;
}

.doc-placeholder.body.footer {
    width: max-content;
    text-align: center;
}

.doc-placeholder.sections {

}

.doc-actual {

}

.doc-actual.directory {

}

.doc-actual.body {

}

.doc-actual.sections {

}
</style>
