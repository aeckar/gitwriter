<script setup lang="ts">
import NavigationBar from '@/components/NavigationBar.vue'
import GithubButton from '@/components/GithubButton.vue'
import AppIcon from '@/components/AppIcon.vue'


</script>

<template>
    <div id="layout">
        <NavigationBar>
            <AppIcon/>
            <GithubButton :href="githubLink"/>
        </NavigationBar>
    </div>
</template>

<style scoped>
#layout {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}
</style>
