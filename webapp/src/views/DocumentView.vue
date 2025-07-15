<script setup lang="ts">
import NavigationBar from '@/components/NavigationBar.vue'
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
    try {
        response = await fetch(`http://localhost:8080/pages/${user}/${repo}/${route.params.page}`)
    } catch (err) {
        console.error('Fetch failed: ', err)
        if (err instanceof TypeError) {
            alert("Network error: Could not connect to server. Please try again later.")
        }
        throw err
    }

    if (!response.ok) {
        const error = useError()
        error.status = response.status
        error.description = response.statusText
        await router.push({ name: "error" })
    }

    page.value = await response.json()
})
</script>

<template>
    <!-- Guard components requiring page data with 'v-if="page"' until server responds. -->
    <div id="layout">
        <NavigationBar>
            <div id="title-container" class="row-format">
                <a href="/pages/{{ user }}/{{ repo }}/" target="_blank">
                    <AppIcon id="icon" width="40px" height="40px" color="white"/><!-- todo replace with icon later -->
                </a>
                <div id="title">
                    <div v-if="page">
                        {{ page.document.title }}
                    </div>
                    <div v-else>
                        <PlaceholderBox width="200px" height="40px"/>
                    </div>
                </div>
            </div>
            <a v-if="page" href="https://github.com/{{ user }}/{{ repo }}/">
                <GithubIcon width="40px" height="40px" color="white"/>
            </a>
            <div v-else>

            </div>
        </NavigationBar>
        <div id="content">
            <nav id="content-directory">

            </nav>
            <div id="content-body" style="color:black; font-size: 40pt;">
                <div id="content-text" >
                    <div v-if="page" v-html="page.content">
                    </div>
                    <div v-else>

                    </div>
                </div>
                <footer class="faded-text footer-text">
                    Generated using GitWriter &copy; Angel Eckardt 2025
                </footer>
            </div>
            <nav id="content-navigation"></nav>
        </div>
    </div>
</template>

<style scoped>
#title {
    margin-left: 5px;
}

#icon {
    margin: 10px 0 10px 100px;
}

#layout {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}

#content-directory {

}

#content-navigation {

}

#content-text {
    font-size: 12pt;
}
</style>
