<script setup lang="ts">
import { PencilRuler } from 'lucide-vue-next'

defineProps(['color'])
</script>

<template>
    <PencilRuler class="app-icon" :style="{ color }"/>
</template>

<style scoped>
.app-icon {
    width: 60px;
    height: 60px;
}
</style>
