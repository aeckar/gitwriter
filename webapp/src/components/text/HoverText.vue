<script setup lang="ts">
const { color } = defineProps({
    color: {
        type: String,
        required: true
    }
})
</script>

<template>
    <div class="glow-on-hover">
        <slot/>
    </div>
</template>

<style scoped>
.glow-on-hover:hover {  /* fixme not working ??? */
    /*noinspection CssUnresolvedCustomProperty*/
    color: white;
    transition: color 0.3s;
}
</style>
