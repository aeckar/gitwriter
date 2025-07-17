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
<div class="flex-col page">
    {{ status }}: {{ statusText }}
    <a href="/" class="faded home-link">
        <span class="glow-white">return to homepage</span>
    </a>
</div>
</template>

<style scoped>
.page {
    font-weight: bold;
    font-size: 40pt;
    color: white;
    background-color: #17171a;
    position: absolute;
    height: 100vh;
    width: 100vw;
    overflow: hidden;
}

.home-link {
    font-size: 20pt;
}
</style>
