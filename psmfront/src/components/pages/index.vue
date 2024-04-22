<template>
    <div class="myHeader">
        <span>
            <svg t="1712019523172" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
                p-id="4288" width="30" height="30">
                <path
                    d="M657.92 307.2V215.04c0-18.944 4.096-23.552 20.992-23.552h78.336c13.312 0 17.92 5.632 17.92 21.504v194.048a28.16 28.16 0 0 0 9.216 23.04c28.672 26.112 56.832 51.2 85.504 80.384 13.824 13.312 14.336 19.456 0 35.328l-21.504 28.672c-8.704 11.776-16.384 12.288-26.624 0l-299.008-285.184-10.752-10.24-67.584 64.512-244.736 232.96c-11.264 10.752-17.92 9.728-27.136 0l-23.04-31.232A19.456 19.456 0 0 1 153.6 512l134.656-129.536 182.272-173.056a51.2 51.2 0 0 1 81.92 0l98.304 93.696z m-89.088 525.824v-186.368H455.168v186.88H285.184a29.696 29.696 0 0 1-30.72-32.256v-229.888a20.48 20.48 0 0 1 5.632-11.776l145.92-132.096 97.28-87.552 8.704-7.168 57.856 51.2 190.464 172.032a26.112 26.112 0 0 1 9.728 22.528v218.624a35.84 35.84 0 0 1-13.312 31.744 24.064 24.064 0 0 1-13.312 4.608h-171.008z"
                    fill="#707070" p-id="4289">
                </path>
            </svg>
            工作台
        </span>
    </div>
    <div class="myBody">
        <div class="aside">

            <div class="aside-header">
                <el-avatar> {{ userInfo.nickname }} </el-avatar>
                <div class="aside-content">
                    <span>{{ userInfo.nickname }}，{{ period }}好</span>
                    <span class="date">今天是{{ date }}</span>
                </div>
            </div>
            <div class="aside_menu">
                <div class="item">
                    <router-link to="/home/index/indexOverview">
                        <el-icon size="30"><HomeFilled /></el-icon>
                        概览
                    </router-link>
                </div>
                <div class="item">
                    <span>待办</span>
                    <router-link to="/home/index/indexAgent">
                        <el-icon size="30"><Memo /></el-icon>
                        待处理的事项
                    </router-link>
                </div>
            </div>
        </div>
        <div class="main">
            <router-view></router-view>
        </div>
    </div>
</template>

<script setup lang="ts">
import { useUserStore } from '@/store/user';
import { getCurrentDateDetails, getCurrentTimePeriod } from '@/utils/dateUtils';
const userStore = useUserStore()
const userInfo = userStore.userInfo
const period = getCurrentTimePeriod()
const date = getCurrentDateDetails()

</script>

<style scoped lang="scss">
@import url('../../css/index.css');

.aside-header {
    display: flex;
    flex-direction: row;
    align-items: center;
    margin-top: 15px;
    margin-bottom: 20px;
    .el-avatar {
        background-color: #5dcfff;
        color: #fff;
        text-align: center;
        margin: 0 10px;
        
    }

    .aside-content {
        display: flex;
        flex-direction: column;

        span {
            color: black;
            font-size: 14px;
            margin: none;
            margin-left: 10px;
            line-height: 15px;
        }

        .date {
            margin-top: 7px;
            font-size: 12px;
            color: #999;
        }
    }

}
.item{
    border-top: 1px solid #ebeef5;
    padding: 5px;
}
</style>