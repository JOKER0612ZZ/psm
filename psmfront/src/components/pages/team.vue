<template>
    <div class="myHeader">
        <span>
            <svg t="1710425993079" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
                p-id="5760" width="30" height="30">
                <path
                    d="M593.9 473.4c110.2 23.8 238.4 101.3 238.4 324.8 0 59.6-74.5 59.6-315.8 59.6-244.3 0-315.8 0-315.8-59.6 0-220.5 128.1-300.9 238.4-324.8-86.4-47.7-116.2-157.9-74.5-247.3s146-122.2 226.4-74.5 113.2 157.9 68.5 244.3c-15 35.8-38.8 59.6-65.6 77.5z"
                    p-id="5761" fill="#f4ea2a"></path>
                <path
                    d="M344.6 469.8c9.4-2.8 11.8-15 4-21-69.2-53-90.4-151.6-52.5-232.9 5.6-12 12.3-23 20-32.9 6.4-8.2-0.2-20.1-10.6-19.2-44.8 3.8-87.1 31.4-108.7 77.7-30.2 64.7-14.2 142.8 39.4 186.4 7.4 6.1 5.2 17.9-4 20.8-82.7 25.8-168.3 94.5-168.3 256.8 0 24.9 15.3 36.9 57.2 42.8 6.7 0.9 12.9-3.9 13.6-10.7 16.5-167.2 115.5-239.9 209.9-267.8zM679.5 469.8c-9.4-2.8-11.8-15-4-21 69.2-53 90.4-151.6 52.5-232.9-5.6-12-12.3-23-20-32.9-6.4-8.2 0.2-20.1 10.6-19.2 44.8 3.8 87.1 31.4 108.7 77.7 30.2 64.7 14.2 142.8-39.4 186.4-7.4 6.1-5.2 17.9 4 20.8 82.7 25.8 168.3 94.5 168.3 256.8 0 24.9-15.3 36.9-57.2 42.8-6.7 0.9-12.9-3.9-13.6-10.7-16.5-167.2-115.5-239.9-209.9-267.8z"
                    p-id="5762" fill="#f4ea2a"></path>
            </svg>
            团队管理
        </span>
    </div>
    <div class="myBody">
        <div class="aside">
            <span>团队</span>
            <div class="aside_menu">
                <a @click="allTeamData">
                    <el-icon size="30" color="rgb(102, 152, 255)">
                        <UserFilled />
                    </el-icon>
                    全部团队
                </a>
                <a @click="creatorTeamData">
                    <el-icon color="rgb(115, 216, 151)" size="30">
                        <UserFilled />
                    </el-icon>
                    我创建的团队
                </a>
                <a @click="JoinTeamData">
                    <el-icon color="rgb(93, 207, 255)" size="30">
                        <UserFilled />
                    </el-icon>
                    我加入的团队
                </a>
            </div>
        </div>
        <div class="main">
            <team-view :title="title"></team-view>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import eventBus from '@/utils/event';
import teamView from './team/teamView.vue';
const allTeamData = () =>{
    eventBus.emit('AllteamData')
    title.value = '全部团队'
}
const creatorTeamData = () =>{
    eventBus.emit('creatorTeamData')
    title.value = '我创建的团队'
}
const JoinTeamData = () =>{
    eventBus.emit('JoinTeamData')
    title.value = '我加入的团队'
}
const title = ref('全部团队')

</script>

<style scoped lang="scss">
@import url('../../css/index.css');
</style>