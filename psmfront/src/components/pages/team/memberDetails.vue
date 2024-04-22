<template>
    <div class="user">
        <div class="user_header">
            <div class="header_left">
                <span>账号：{{ userInfo?.userName }}</span>
                <span>昵称：{{ userInfo?.nickname }}</span>
            </div>
            <div class="header_right">
                <span>负责的任务数：{{ total }}</span>
            </div>
        </div>
        <div class="user_content">
            <div class="content_body" ref="chart">
                <!--图表-->
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, watchEffect, onMounted } from 'vue';
import { Members, user } from '@/api/interface';
import { queryUserByUserId } from '@/api/user';
import echarts from '@/utils/echarts';
import { queryStatusByUserId } from '@/api/task'

const props = defineProps({
    member: {
        type: Object as () => Members,
        required: true
    }
})
interface taskData {
    name: string,
    value: number
};
const data = ref<taskData[]>([])
const chart = ref()
const userInfo = ref<user>()
const tasks = ref<Map<String, any>>()
const total = ref<number>()
const loadData = async () => {
    userInfo.value = await queryUserByUserId(props.member.memberId)
    tasks.value = await queryStatusByUserId(props.member.memberId)
    console.log('tasks.value: ', tasks.value);
    if (tasks.value) {
        total.value = 0
        data.value = []
        for (let [key, value] of Object.entries(tasks.value!)) {
            data.value.push({ name: key, value: value });
            total.value += value
        }
        console.log(data.value);
        initEcharts()
    }


}
onMounted(async () => {
    loadData()
})

const initEcharts = () => {
    const pieChart = echarts.init(chart.value)
    const option = {
        title: {
            text: '任务完成情况',
            left: 'center',
            top: '5%'
        },
        tooltip: {
            trigger: 'item'
        },
        color: ['#909399', '#409EFF', '#E6A23C', '#67C23A', '#F56C6C', '#c45656'],
        series: [
            {
                type: 'pie',
                radius: '50%',
                label: {
                    formatter:  (params: { data: { value: number; }; name: string; })=> {
                        const rate = parseFloat((params.data.value * 1.0 / total.value! * 1.0 * 100).toFixed(1))
                        return params.name + ': ' + rate+'%';
                    }
                },
                data: data.value.map(item => ({
                    ...item,
                    label: { show: item.value !== 0 }
                })),
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)',
                    }
                }
            }
        ]
    };
    pieChart.setOption(option)
}
watchEffect(async () => {
    loadData()
})

</script>

<style scoped lang="scss">
.user {
    display: flex;
    flex-direction: column;
    border: 1px solid #ddd;
    border-radius: 10px;
    font-size: 16px;
    padding: 20px;
    max-width: 500px;
    text-align: left;
    margin: auto;
}

.user_header,
.user_content {
    display: flex;
    justify-content: space-between;
    margin-bottom: 20px;
}

.user_content {
    display: flex;
    flex-direction: column;
}

.header_left,
.header_right {
    display: flex;
    flex-direction: column;
}

.header_left span,
.header_right span {
    margin-bottom: 10px;
}

.content_header {
    align-self: flex-start;
}

.content_body {
    width: 500px;
    height: 350px;
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>