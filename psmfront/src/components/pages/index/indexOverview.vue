<template>
    <div class="viewHeader">
        <span>最近使用</span>
        <div class="viewHeader-content">
            <div class="items">
                <div v-for="(item, index) in records" :key="index" class="item" @click="goProjectDetail(item)">

                    <div class="item_header">
                    </div>
                    <div class="item_body">
                        <svg t="1712042350475" class="icon" viewBox="0 0 1024 1024" version="1.1"
                            xmlns="http://www.w3.org/ 2000/svg" p-id="4320" width="30" height="30">
                            <path
                                d="M927.747553 0H96.289965a96.289964 96.289964 0 0 0-96.289964 96.289964v831.420072a96.289964 96.289964 0 0 0 96.289964 96.289964h831.457588a96.289964 96.289964 0 0 0 96.289964-96.289964V96.252449a96.289964 96.289964 0 0 0-96.289964-96.252449zM221.529445 275.739443h450.762084v76.806877H221.529445z m580.991134 472.471094h-580.991134v-76.794373h580.991134z m0-197.832108h-580.991134v-76.794373h580.991134z"
                                p-id="4321" fill="#1296db">
                            </path>
                        </svg>
                        <div class="item_footer">
                            {{ item.projectName }}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="viewBody"></div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { getRecords } from '@/api/project'
import { goProjectDetail } from '@/utils/tools'
const records = ref<any>()
onMounted(async () => {
    records.value = await getRecords()
    records.value.reverse()
})
</script>

<style scoped lang="scss">
.viewHeader {
    width: 100%;

    span {
        display: block;
        height: 100px;
        padding: 0 40px;
        line-height: 100px;
        font-size: 20px;
        text-align: left;
    }

    .items {
        width: 100%;
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        box-sizing: border-box; 
        .item {
            width: 18%;
            height: 130px;
            position: relative;
            display: inline-block;
            margin-left: 16px;
            margin-top: 16px;
            border: 1px solid #eee;
            border-radius: 4px;
            cursor: pointer;
            flex-shrink: 0; /* 防止子项缩小 */
            &:hover {
                background-color: #d0d0d0c9;
            }

            .item_header {
                height: 40px;
                background-color: #f5f5f5;
            }

            .item_body {
                height: calc(100% - 40px);

                svg {
                    position: relative;
                    top: -15px;
                    left: -35%;
                }

                .item_footer {
                    text-align: left;
                    font-size: 14px;
                    padding: 10px;
                    padding-top: 0px;
                }
            }
        }

    }
}

.viewHeader-content {
    width: 100%;
    overflow: auto;
}
</style>
