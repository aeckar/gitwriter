<script setup lang="ts">
const { width, height } = defineProps({
    width: {
        type: String,
        required: true
    },
    height: {
        type: String,
        required: true
    }
})
// todo fix
</script>

<template>
    <div class="placeholder-box pulsate shimmer" :style="{ width, height }"></div>
</template>

<!--suppress RequiredAttributes -->
<style>
.placeholder-box {
    border-radius: 10px;
}

.pulsate {
    animation-name: color;
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
    animation: shimmer-swipe 2s infinite;
    animation-delay: 0s;
}

@keyframes shimmer-swipe {
    0% {
        left: -300%;
    }
    100% {
        left: 300%;
    }
}

@keyframes color {
    to {
        background-color: #636468;
    }
    from {
        background-color: #4f5054;
    }
}
</style>
