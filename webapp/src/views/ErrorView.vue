<script setup lang="ts">
import { resetError, useError } from '@/stores/stores.ts'
import { onUnmounted } from 'vue'
import CodeBlock from '@/components/text/CodeBlock.vue'
import HoverText from '@/components/text/HoverText.vue'

const { cause, message } = useError()

onUnmounted(() => {
    resetError()
})
</script>

<template>
    <div id="layout" class="flex-column">
        {{ cause }}
        <div id="message" v-if="message">
            <CodeBlock>{{ message }}</CodeBlock>
        </div>
        <HoverText color="white">
            <a href="/" id="home-link" class="faded-text"> return to homepage </a>
        </HoverText>
    </div>
</template>

<style scoped>
#layout {
    #home-link {
        font-size: 20pt;
    }
    font-weight: bold;
    font-size: 40pt;
    color: white;
    background-color: #17171a;
    position: absolute;
    height: 100vh;
    width: 100vw;
    gap: 50px;
}
</style>
