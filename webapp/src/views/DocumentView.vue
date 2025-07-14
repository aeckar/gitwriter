<script setup lang="ts">
import NavigationBar from '@/components/NavigationBar.vue'
import AppIcon from '@/components/AppIcon.vue'
import { useError } from '@/stores/stores.ts'
import GithubIcon from '@/components/GithubIcon.vue'
import { useRoute, useRouter } from 'vue-router'
import { onMounted } from 'vue'
import type { Page } from '@/lib/content.ts'

const router = useRouter()
const route = useRoute()

const { user, repo } = route.params

let page: string//todo

onMounted(async () => { // todo change from localhost on launch
    console.log("Hello!")
    const response = await fetch(`http://localhost:8080/pages/${user}/${repo}/${route.params.page}`)
    if (!response.ok) {
        const error = useError()
        error.status = response.status
        error.description = response.statusText
        await router.push({ name: "/error" })
    }
    page=await response.text()
})
</script>

<template>
    <div id="layout">
        <NavigationBar>
            <a href="/pages/{{ user }}/{{ repo }}/" target="_blank">
                <AppIcon />
            </a>
            <div id="title">
                {{ page }} page.site.title
            </div>
            <a href="{{ content(user, repo) }}">
                <GithubIcon/>
            </a>
        </NavigationBar>
        <div id="content">
            <nav id="content-directory">

            </nav>
            <div id="content-body" style="color:black; font-size: 40pt;">
                <div id="content-text" ></div>
                {{ page }}
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


#content-text {

}
</style>
