<template>
    <nav id="navbar">
        <slot/>
    </nav>
</template>

<style scoped>
#navbar {
    display: flex;
    flex-direction: row;
    background-color: #27282c;
    align-items: center;
    justify-content: space-between;
    margin: 0;
    padding: 1px;
    box-shadow: 0 5px 10px rgba(0, 0, 0, 0.5);
}
</style>
<script setup lang="ts">
</script>
