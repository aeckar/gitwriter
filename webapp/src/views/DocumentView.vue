<script setup lang="ts">
import NavigationBar from '@/components/NavigationBar.vue'
import AppIcon from '@/components/AppIcon.vue'
import { useError } from '@/stores/stores.ts'
import GithubIcon from '@/components/GithubIcon.vue'
import { useRoute, useRouter } from 'vue-router'
import { onMounted } from 'vue'

type NavigableTree = {
    name: string
    children: Array<NavigableTree | string>
};

const router = useRouter()
const route = useRoute()

const { user, repo } = route.params

let document: {
    name: string,
    icon: string,
    favicon: string,
    contentRoot: string,
    contentDirectory: NavigableTree,
    folderAliases: Map<String, String>
}

let contentHtml: string

onMounted(async () => {
    const response = await fetch(`http://localhost:8080/${user}/${repo}/${route.params.page}`)
    if (!response.ok) {
        const error = useError()
        error.status = response.status
        error.description = response.statusText
        await router.push({ name: "/error" })
    }
    ({ document, contentHtml } = await response.json())
})
</script>

<template>
    <div id="layout">
        <NavigationBar>
            <a href="/document/{{ user }}/{{ repo }}/" target="_blank">
                <AppIcon />
            </a>
            <div id="name">
                {{ document.name }}
            </div>
            <a href="{{ content(user, repo) }}">
                <GithubIcon/>
            </a>
        </NavigationBar>
        <div id="content">
            <nav id="content-directory">

            </nav>
            <div id="content-body">
                <template v-html="contentHtml"></template>
                <footer class="faded-text footer-text">
                    Generated using GitWriter &copy; Angel Eckardt 2025
                </footer>
            </div>
            <nav id="content-navigation"></nav>
        </div>
    </div>
</template>

<style scoped>
#layout {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}

#content-directory {

}

#content-navigation {

}
</style>
