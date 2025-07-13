<script setup lang="ts">
import { resetError, useError } from '@/stores/stores.ts'
import { onUnmounted } from 'vue'

const { status, description } = useError()

onUnmounted(() => {
    resetError()
})
</script>

<template>
    <div id="layout" class="col-format">
        {{ status }}: {{ description }}
        <a href="/" id="return" class="faded-text"> return to homepage </a>
    </div>
</template>

<style scoped>
#return {
    font-size: 20pt;
}

#return:hover {
    color: white;
    transition: color 0.3s;
}

#layout {
    font-weight: bold;
    font-size: 40pt;

    /* color scheme */
    color: white;
    background-color: #27282c;

    /* position at center of page */
    position: absolute;
    height: 100vh;
    width: 100vw;
}
</style>
