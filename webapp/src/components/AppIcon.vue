<script setup lang="ts">
import { PencilRuler } from 'lucide-vue-next'
import { shapeProps } from '@/lib/props.ts'

const { width, height, color } = defineProps(shapeProps)
</script>

<template>
    <PencilRuler :width="width" :height="height" :color="color"/>
</template>
