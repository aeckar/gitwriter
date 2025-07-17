<script setup lang="ts">
import { PencilRuler } from 'lucide-vue-next'

const { width, height, color } = defineProps({
        width: {
            type: String,
            required: true
        },
        height: {
            type: String,
            required: true
        },
        color: {
            type: String,
            required: true
        }
    }
)
</script>

<template>
<PencilRuler :width="width" :height="height" :color="color"/>
</template>
