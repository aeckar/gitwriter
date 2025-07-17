import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useError = defineStore('error', () => {
    const status = ref(404)
    const statusText = ref("Not found")

    return { status, statusText }
})
