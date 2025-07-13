import { ref } from 'vue'
import { defineStore } from 'pinia'

export const document = defineStore('document', () => {
    const homeIcon = ref("")
    const favicon = ref("")
    const pageTitle = ref("")
    const githubLink = ref("")

    return { homeIcon, favicon, pageTitle, githubLink }
})
