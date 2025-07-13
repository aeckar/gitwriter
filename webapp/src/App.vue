<script setup lang="ts">
import { RouterView } from 'vue-router'
import { resetError } from '@/stores/stores.ts'

resetError()
</script>

<template>
    <RouterView />
</template>
