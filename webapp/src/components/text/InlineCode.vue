<script setup lang="ts">
import { useDark } from '@vueuse/core'

const isDark = useDark()
</script>

<template>
    <code v-if="isDark" class="dark-theme">
        <slot/>
    </code>
    <code v-else class="light-theme">
        <slot/>
    </code>
</template>

<style scoped>
code {
    .dark-theme {
        background-color: #2d2d2d;
        color: #d4d4d4;
    }
    .light-theme {
        background-color: #f0f0f0;
        color: #333333
    }
    padding: 0.2em 0.4em;
    border-radius: 4px;
    font-family: monospace;
    font-size: 0.95em;
    white-space: pre-wrap;
    box-decoration-break: clone;
}
</style>
