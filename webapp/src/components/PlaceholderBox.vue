<script setup lang="ts">
import type { PropType } from 'vue'
import type { Pulse } from '@/lib/types.ts'

const { width, height, pulse } = defineProps({
    width: {
        type: String,
        required: true
    },
    height: {
        type: String,
        required: true
    },
    pulse: {
        type: Object as PropType<Pulse>,
        required: true
    }
})

const { mid: toColor, faint: fromColor } = pulse
</script>

<template>
    <div class="placeholder-box pulsate shimmer"></div>
</template>

<!--suppress RequiredAttributes -->
<style>
.placeholder-box {
    border-radius: 10px;
    width: v-bind(width);
    height: v-bind(height);

}

.pulsate {
    animation-name: pulsate;
    animation-duration: 0.3s;
    animation-iteration-count: infinite;
    animation-direction: alternate-reverse;
    animation-timing-function: ease;
}

.shimmer {
    position: relative;
    overflow: hidden;
}

.shimmer::before {
    content: "";
    position: absolute;
    top: 0;
    left: -150%; /* Start far to the left */
    height: 100%;
    width: 150%;
    background: linear-gradient(120deg, transparent 20%, rgba(255, 255, 255, 0.4) 50%, transparent 80%);
    background-size: 120%;
    animation: shimmer 2s infinite;
    animation-delay: 0s;
}

@keyframes pulsate {
    to {
        background-color: v-bind(toColor);
        box-shadow: var(--shadow-blur) var(--shadow-blur) var(--shadow-blur) var(--black-faint);
    }
    from {
        background-color: v-bind(fromColor);
        box-shadow: var(--shadow-blur) var(--shadow-blur) var(--shadow-blur) var(--black-semi);
    }
}

@keyframes shimmer {
    to {
        left: 300%;
    }
    from {
        left: -300%;
    }
}
</style>
