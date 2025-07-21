<script setup lang="ts">
import { RouterView } from 'vue-router'
</script>

<template>
<RouterView/>
</template>

<style>
@import url('https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100..900;1,100..900&display=swap');

/* - Use 'em' for elements relative to font size, otherwise use % */

/* ============================== setup ============================== */

html, body {
    height: 100%;
    margin: 0;

    /* https://stackoverflow.com/q/77665793 */
    overflow-y: auto !important;
    overflow-x: hidden;
}

a {
    color: inherit;
    text-decoration: none;
}

/* ============================== variables ============================== */

:root {
    --bg-med-r: 39;
    --bg-med-g: 40;
    --bg-med-b: 44;
    --bg-med: rgb(var(--bg-med-r), var(--bg-med-g), var(--bg-med-b));
    --bg-dark: #17171a;

    --black-semi: rgba(0, 0, 0, 0.2);
    --black-faint: rgba(0, 0, 0, 0.1);

    --shadow-blur: 2px;
}

/* ============================== page layout ============================== */

.layout {
    position: absolute;
    height: 100vh;
    width: 100vw;
    background-color: var(--bg-dark);
    color: white;
    font-family: "Roboto", sans-serif;
    overflow: hidden;
}

.display-col {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

.display-row {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
}

.display-grid {
    display: grid;
    gap: 0.5rem;
    justify-items: center;
    align-items: center;
}

.bar {
    background:
        linear-gradient(
            to bottom,
            rgb(calc(var(--bg-med-r) + 20), calc(var(--bg-med-g) + 20), calc(var(--bg-med-b) + 20)),
            var(--bg-med)
        ) top / 100% 50% no-repeat,
        linear-gradient(
            to bottom,
            var(--bg-med),
            rgb(calc(var(--bg-med-r) - 20), calc(var(--bg-med-g) - 20), calc(var(--bg-med-b) - 20))
        ) bottom / 100% 50% no-repeat,
        var(--bg-med);
    box-shadow: 0 0 1rem black;
    width: 100vw;
}

/* ============================== text formatting ============================== */

.text-mono {
    font-family: monospace;
}

.text-fade {
    color: #aaaaaa;
}

.text-fine {
    font-size: 10pt;
}

.text-glow-white:hover {
    color: white;
    transition: color 0.3s;
}
</style>
