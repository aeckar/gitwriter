import { ref } from 'vue'
import { defineStore } from 'pinia'

export function resetError() {
    const error = useError()
    error.status = 404
    error.description = "Not Found"
}

export const useError = defineStore('error', () => {
    const status = ref(0)
    const description = ref('')

    return { status, description }
})
