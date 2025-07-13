<script setup lang="ts">
defineProps(['color'])
</script>

<template>
    <a href="/" class="material-symbols-outlined home-icon" :style="{ color }">
        contract_edit
    </a>
</template> // todo later: make dynamic, user-definable

<style scoped>
.home-icon {
    color: white;
    text-decoration: none;
    user-select: none;
    padding-right: 10px;
    font-size: 30pt;
}
</style>
