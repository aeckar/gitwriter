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
// todo update favicon for dark, light mode browsers keep lucide icon size at 48px
// https://lucide.dev/icons/pencil-ruler
// https://stackoverflow.com/questions/55170708/detect-if-the-browser-is-using-dark-mode-and-use-a-different-favicon

// todo autocomplete w/ known ('site.toml' or 'index.md')-containing repo's matching search query
</script>

<template>
<PencilRuler :width="width" :height="height" :color="color"/>
</template>
