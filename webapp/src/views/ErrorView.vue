<script setup lang="ts">
import { useError } from '@/stores/stores.ts'
import { onUnmounted } from 'vue'

const { status, statusText } = useError()

onUnmounted(() => {
    const error = useError()
    error.status = 404
    error.statusText = "Not found"
})
</script>

<template>
<div class="display-col layout">
    {{ status }}: {{ statusText }}
    <a href="/" class="text-fade text-mono home-link">
        <span class="text-glow-white">return to homepage</span>
    </a>
</div>
</template>

<style scoped>
.layout {
    font-size: 4em;
    font-weight: bold;
}

.home-link {
    font-size: 0.3em;
}
</style>
