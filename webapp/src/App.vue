<script setup lang="ts">
import { RouterView } from 'vue-router'
</script>

<template>
<RouterView/>
</template>

<style>
@import url('https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100..900;1,100..900&display=swap');

html, body {
    height: 100%;
    margin: 0;
    overflow-y: auto;   /* https://stackoverflow.com/q/77665793 */

    --backdrop-color: #17171a
}

a {
    color: inherit;
    text-decoration: none;
}

/* ------------------------------ layout ------------------------------ */

.invisible {
    display: none;
}

.page {
    height: 100vh;
    width: 100vw;
    font-family: "Roboto", sans-serif;
    overflow: hidden;
}

.flex-col {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

.flex-row {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
}

.v-bar {
    background:
        linear-gradient(to bottom, #3b3c40, #27282c) top,
        linear-gradient(to top, #27282c, #131418) bottom,
        #27282c;
    box-shadow: 0 0 1rem black;
    width: 100vw;
}

/* ------------------------------ text formatting ------------------------------ */

.faded {
    color: #aaaaaa;
    font-family: monospace;
}

.fine {
    font-size: 10pt;
    padding-bottom: 0.8rem;
}

.glow-white:hover {
    color: white;
    transition: color 0.3s;
}
</style>
