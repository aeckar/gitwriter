<script setup lang="ts">
import { useDark } from '@vueuse/core'

const isDark = useDark()
</script>

<template>
    <pre v-if="isDark" class="dark-theme"><code><slot/></code></pre>
    <pre v-else class="light-theme"><code><slot/></code></pre>
</template>

<style scoped>
pre {
    .dark-theme { /* todo syntax highlighting */
        background-color: #f5f5f5;
        color: #333333;
    }
    .light-theme {
        background-color: #1e1e1e;
        color: #d4d4d4;
    }
    padding: 1rem;
    border-radius: 6px;
    overflow-x: auto;
    font-family: monospace;
    font-size: 0.95rem;
    line-height: 1.5;
    border: 1px solid #dddddd;
}

code {
    display: block;
    white-space: pre;
}
</style>
