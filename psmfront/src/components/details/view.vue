<template>
    <div class="overview">
        <div class="basic_info"></div>
        <div class="percent"></div>
    </div>
</template>

<script setup lang="ts">

</script>

<style scoped lang="scss">
.overview{
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    background-color: #fafafa;
    width: 100%;
    height: 100%;
    overflow: auto;
}
.basic_info{
    position: relative;
    border-radius: 2px;
    outline: 1px solid #eee;
    z-index: 1;
    display: block;
    margin-left: 8px;
    margin-top: 8px;
    width: 25%;
    height: 60%;
    background-color: white;
}
.percent{
    position: relative;
    border-radius: 2px;
    outline: 1px solid #eee;
    z-index: 1;
    display: block;
    margin-left: 8px;
    margin-top: 8px;
    width: 73%;
    height: 60%;
    background-color: white;
}
</style>