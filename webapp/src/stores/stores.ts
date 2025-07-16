import { ref } from 'vue'
import { defineStore } from 'pinia'

export function resetError() {
    const error = useError()
    error.cause = "404: Not Found"
    error.message = null
}

export const useError = defineStore('error', () => {
    const cause = ref('')
    const message = ref<string | null>(null)

    return { cause, message }
})
