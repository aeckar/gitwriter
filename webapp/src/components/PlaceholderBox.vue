<script setup lang="ts">
import { shapeProps } from '@/lib/props.ts'

const { width, height, color } = defineProps(shapeProps)
// todo fix
</script>

<template>
    <div
        class="placeholder-box"
        :style="{ width, height, background: color }"
    ></div>
</template>

<!--suppress RequiredAttributes -->
<style>
.placeholder-box {
    border-radius: 10px;
}

.placeholder-box {
    background: linear-gradient(-45deg, lightgray 30%, #27282c 50%, lightgray 70%);
    background-size: 50%;
    background-position-x: -100%;
    animation: shimmer 1s infinite linear;
}

@keyframes shimmer {
    to {
        background-position-x: 0;
    }
}
</style>
